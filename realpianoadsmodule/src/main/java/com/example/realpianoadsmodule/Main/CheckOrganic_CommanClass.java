package com.example.realpianoadsmodule.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.example.realpianoadsmodule.Interstitial.Splash_InterstitialAd;
import com.example.realpianoadsmodule.Native.PreloadNativeAd;
import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.MainApplicationClass;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Locale;

public class CheckOrganic_CommanClass {
    static InstallReferrerClient installReferrerClient;
    public static String Referrer_Url = "NA";

    public static boolean CheckInstallRefereerwithMedium(String url, String medium) {
        String[] split = medium.split(",");
        if (TextUtils.isEmpty(url)) {
            return true;
        }
        for (String abc : split) {
            if (url.toLowerCase(Locale.getDefault()).contains(abc.toLowerCase(Locale.getDefault())))
                return true;
        }
        return false;
    }

    public static boolean checkScreenNdOrganic(Activity activity) {
        boolean isOrganic = checkForOrganicOrNot(activity);
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        boolean isScreenOn = newApp_preference.getScreen().equals("on");
        return isScreenOn && !isOrganic;
    }

    public static boolean checkForOrganicOrNot(Activity activity) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        String[] split = newApp_preference.getMedium().split(",");
        if (TextUtils.isEmpty(newApp_preference.getReferrerUrl())) {
            return true;
        }
        for (String abc : split) {
            if (newApp_preference.getReferrerUrl().toLowerCase(Locale.getDefault()).contains(abc.toLowerCase(Locale.getDefault())))
                return true;
        }
        if (newApp_preference.getGclid().equalsIgnoreCase("on")) {
            return !newApp_preference.getReferrerUrl().toLowerCase(Locale.getDefault()).contains(newApp_preference.getGclidValue());
        }
        return false;
    }

    public static void CheckInstallRefererfromDevice(Activity activity, Install_ReferrerListener referrerListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        installReferrerClient = InstallReferrerClient.newBuilder(activity).build();
        installReferrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        try {
                            ReferrerDetails referrerDetails = installReferrerClient.getInstallReferrer();
                            Referrer_Url = referrerDetails.getInstallReferrer();
                            newApp_preference.setReferrerUrl(Referrer_Url);
                            if (newApp_preference.getShowInstall().equalsIgnoreCase("on")) {
                                Toast.makeText(activity, "referrerUrl :" + Referrer_Url, Toast.LENGTH_SHORT).show();
                            }
                            referrerListener.installrefererfound();
                        } catch (RemoteException e) {
                            referrerListener.installreferrerCancel();
                            Log.e("InstallReferrer", "" + e.getMessage());
                        }
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        referrerListener.installreferrerCancel();
                        Log.w("InstallReferrer", " Response.FEATURE_NOT_SUPPORTED");
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        referrerListener.installreferrerCancel();
                        Log.w("InstallReferrer", " Response.SERVICE_UNAVAILABLE");
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED:
                        referrerListener.installreferrerCancel();
                        Log.w("InstallReferrer", " Response.SERVICE_DISCONNECTED");
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR:
                        referrerListener.installreferrerCancel();
                        Log.w("InstallReferrer", " Response.DEVELOPER_ERROR");
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                Log.w("InstallReferrer", "onInstallReferrerServiceDisconnected()");
            }
        });
    }

    public static void LoadOpenAds(NewApp_Preference newApp_preference, Activity activity, Intent intent) {
        String string = newApp_preference.getSplashOpenAppId();
        if (NewApp_Preference.isfullscreenshow) {
            return;
        }
        try {
            AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdLoaded(@NonNull AppOpenAd openAd) {
                    super.onAdLoaded(openAd);
                    FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                        @Override
                        public void onAdShowedFullScreenContent() {
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            activity.startActivity(intent);
                            activity.finish();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    };
                    openAd.show(activity);
                    openAd.setFullScreenContentCallback(fullScreenContentCallback);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    activity.startActivity(intent);
                    activity.finish();
                }
            };
            AppOpenAd.load(activity, string, new AdRequest.Builder().build(), 1, loadCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void StartActivityWithAds(Activity activity, Intent intent) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        boolean check = CheckInstallRefereerwithMedium(Referrer_Url, newApp_preference.getMedium());
        if (check) {
            newApp_preference.setCheckinstallbool(false);
            startAdLoading(activity, newApp_preference, intent);
        } else {
            newApp_preference.setCheckinstallbool(true);
            startAdLoading(activity, newApp_preference, intent);
        }
    }

    private static void startAdLoading(Activity activity, NewApp_Preference newApp_preference, Intent intent) {
        MainApplicationClass.Load_Ads_Init(initializationStatus -> {
            if (newApp_preference.get_splash_flag().equalsIgnoreCase("open")) {
                if (newApp_preference.getOpenFlag().equalsIgnoreCase("on")) {
                    LoadOpenAds(newApp_preference, activity, intent);
                } else {
                    activity.startActivity(intent);
                }
            } else {
                if (newApp_preference.getFullFlag().equalsIgnoreCase("on")) {
                    new Handler().postDelayed(() -> new Splash_InterstitialAd().ShowAds_FullSplash(activity, intent, true), 1000);
                } else {
                    new Handler().postDelayed(() -> activity.startActivity(intent), 1000);
                }
            }
        });
    }

    public static void Native_BannerAdWithCount(Activity activity, FrameLayout frameLayout) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        int count = Integer.parseInt(newApp_preference.getNativecount());
        if (Glob.NativeCountIncr == count) {
            Glob.NativeCountIncr = 0;
        }
        if (Glob.NativeCountIncr % count == 0) {
            Glob.NativeCountIncr++;
            PreloadNativeAd.getInstance(activity).Native_BannerAd(activity, frameLayout);
        } else {
            Glob.NativeCountIncr++;
        }
    }

    public static void Native_BigAdWithCount(Activity activity, FrameLayout frameLayout, boolean isList) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        int count = Integer.parseInt(newApp_preference.getNativecount());
        if (Glob.NativeCountIncr == count) {
            Glob.NativeCountIncr = 0;
        }
        if (Glob.NativeCountIncr % count == 0) {
            Glob.NativeCountIncr++;
            PreloadNativeAd.getInstance(activity).Add_Native_Inlayout(frameLayout, isList);
        } else {
            Glob.NativeCountIncr++;
        }
    }

}
