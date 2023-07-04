package com.example.realpianoadsmodule.Native;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.nativead.NativeAd;

import java.util.ArrayList;


public class NativeAdLoad_All {
    public static Context context;
    private static final String TAG = "NativeAdload";
    int native_AdCount;
    static int Ad_counter = -1;

    public static ArrayList<NativeAd> nativeAds;
    public static ArrayList<String> list;
    private static com.facebook.ads.NativeAd fbNativeAd;

    public static Object getNextNativeAd(Activity activity) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        if (newApp_preference.getAdStyleNative().equalsIgnoreCase("Normal")) {
            if (nativeAds != null && nativeAds.size() > 0) {
                return nativeAds.get(GetTotalCounter());
            } else if (fbNativeAd != null) {
                return fbNativeAd;
            } else
                return null;
        } else if (newApp_preference.getAdStyleNative().equalsIgnoreCase("fb")) {
            if (fbNativeAd != null) {
                return fbNativeAd;
            } else if (nativeAds != null && nativeAds.size() > 0) {
                return nativeAds.get(GetTotalCounter());
            } else
                return null;
        } else return null;
    }

    public static int GetTotalCounter() {
        CounterUpdateAfterIncrement();
        return Ad_counter;
    }

    public static void CounterUpdateAfterIncrement() {
        Ad_counter++;
        if (Ad_counter >= nativeAds.size()) {
            Ad_counter = 0;
        }
    }

    public NativeAdLoad_All(Context activity) {
        context = activity;
        list = new ArrayList<>();
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        if (!newApp_preference.getAdmobNativeId1().isEmpty()) {
            list.add(newApp_preference.getAdmobNativeId1());
        }
        if (!newApp_preference.getAdmobNativeId2().isEmpty()) {
            list.add(newApp_preference.getAdmobNativeId2());
        }
        if (!newApp_preference.getAdmobNativeId3().isEmpty()) {
            list.add(newApp_preference.getAdmobNativeId3());
        }

        native_AdCount = list.size();
        Load_NativeAd(activity);
    }

    public void Load_NativeAd(final Context activity) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        if (newApp_preference.getAdStyleNative().equalsIgnoreCase("Normal")) {
            Load_Native_Intermediate(activity, 0, true);
        } else if (newApp_preference.getAdStyleNative().equalsIgnoreCase("fb")) {
            Load_FbAd(activity, true);
        }
    }

    public void Load_FbAd(Context activity, boolean fromMainFunction) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        Log.d(TAG, newApp_preference.getFacebookNative());
        final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(activity, newApp_preference.getFacebookNative());

        NativeAdListener nativeAdListener = new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                if (fromMainFunction)
                    Load_Native_Intermediate(activity, 0, false);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
                fbNativeAd = nativeAd;
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                        .build());
    }

    public void Load_Native_Intermediate(Context activity, int adCount, boolean fromMainFunction) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        if (adCount == 0) {
            nativeAds = new ArrayList<>();
        }
        AdLoader.Builder builder;

        String adUnitId = list.get(adCount);
        Log.e("Ads ", "NativeAd adUnitId:  " + adUnitId);
        Log.e("NativeAd", "adUnitId:" + adUnitId);
        if (adUnitId == null) {
            return;
        }
        if (TextUtils.isEmpty(adUnitId)) {
            return;
        }
        builder = new AdLoader.Builder(context, adUnitId);

        builder.forNativeAd(nativeAd -> {
            nativeAds.add(nativeAd);
            int nextConunt = adCount + 1;
            if (nextConunt < native_AdCount) {
                Log.e("NativeAd ", " nextConunt: " + nextConunt);
                Load_Native_Intermediate(activity, nextConunt, fromMainFunction);
            }

            if (nextConunt == native_AdCount) {
                Log.e("Ads ", "NativeAd " + nextConunt + ":Last");
            }
        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        com.google.android.gms.ads.nativead.NativeAdOptions adOptions = new com.google.android.gms.ads.nativead.NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                Log.e("NativeAd ", " onAdFailedToLoad: " + adError.getMessage());
                if (nativeAds.size() == 0 && fromMainFunction) {
                    Load_FbAd(activity, false);
                }
            }
        }).build();

        if (newApp_preference.getAdFlag().equals("admob")) {
            AdRequest.Builder builerRe = new AdRequest.Builder();
            adLoader.loadAd(builerRe.build());
        } else {
            AdManagerAdRequest.Builder builerRe = new AdManagerAdRequest.Builder();
            adLoader.loadAd(builerRe.build());
        }
    }

}
