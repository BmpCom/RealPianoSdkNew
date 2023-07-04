package com.example.realpianoadsmodule.Interstitial;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.realpianoadsmodule.Other.Adloading_Custom_Dialog;
import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Arrays;
import java.util.List;

public class InterAd_Full_AdmobFailFbFailQureka_MultipleAd {
    public static InterstitialAd interstitialAd_ads;

    public static void Show_AdMultiple(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, String orgnc_type) {
        final List<String> listUnitIds = Arrays.asList(new NewApp_Preference(source_class).getAdmobInterId1(),
                new NewApp_Preference(source_class).getAdmobInterId2(),
                new NewApp_Preference(source_class).getAdmobInterId3());

        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Glob.Full_AD_Counter++;
            if (Glob.MultipleAdCounter > 2) {
                Glob.MultipleAdCounter = 0;
            }
            final Adloading_Custom_Dialog adloadingCustomDialog = new Adloading_Custom_Dialog(source_class, "Loading Ads");
            adloadingCustomDialog.setCancelable(false);
            adloadingCustomDialog.show();
            AdRequest adRequest = new AdRequest.Builder().build();
            Log.e("selected admob id", String.valueOf(Glob.MultipleAdCounter));
            InterstitialAd.load(source_class, listUnitIds.get(Glob.MultipleAdCounter), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    interstitialAd_ads = interstitialAd;
                    interstitialAd_ads.show(source_class);
                    Glob.MultipleAdCounter++;
                    Log.e("multiple load ad", String.valueOf(Glob.MultipleAdCounter));
                    interstitialAd_ads.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            interstitialAd_ads = null;
                            NewApp_Preference.isfullscreenshow = false;
                            if (adloadingCustomDialog.isShowing()) {
                                adloadingCustomDialog.dismiss();
                            }
                            if (close_adListener != null) {
                                close_adListener.onAdClosed();
                            }
                            Glob.isTimeInterval = false;
                            new Handler().postDelayed(() -> Glob.isTimeInterval = true, Long.parseLong(String.valueOf(newApp_preference.getAdTimeInterval())) * 1000);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            interstitialAd_ads = null;
                            NewApp_Preference.isfullscreenshow = true;
                            if (!orgnc_type.equalsIgnoreCase("organic")) {
                                Glob.Open_Url_To_View(source_class);
                            }
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            if (adloadingCustomDialog.isShowing()) {
                                adloadingCustomDialog.dismiss();
                            }
                            NewApp_Preference.isfullscreenshow = false;
                            if (close_adListener != null) {
                                close_adListener.onAdClosed();
                            }
                            Glob.isTimeInterval = false;
                            new Handler().postDelayed(() -> Glob.isTimeInterval = true, Long.parseLong(String.valueOf(newApp_preference.getAdTimeInterval())) * 1000);
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    interstitialAd_ads = null;
                    if (adloadingCustomDialog.isShowing()) {
                        adloadingCustomDialog.dismiss();
                    }
                    NewApp_Preference.isfullscreenshow = false;

                    com.facebook.ads.InterstitialAd fb_interstitial = new com.facebook.ads.InterstitialAd(source_class, newApp_preference.getFacebookInterstitial());
                    fb_interstitial.loadAd(
                            fb_interstitial.buildLoadAdConfig()
                                    .withAdListener(new InterstitialAdListener() {
                                        @Override
                                        public void onInterstitialDisplayed(Ad ad) {
                                            NewApp_Preference.isfullscreenshow = true;
                                            if (!orgnc_type.equalsIgnoreCase("organic")) {
                                                Glob.Open_Url_To_View(source_class);
                                            }
                                        }

                                        @Override
                                        public void onInterstitialDismissed(Ad ad) {
                                            if (adloadingCustomDialog.isShowing()) {
                                                adloadingCustomDialog.dismiss();
                                            }
                                            NewApp_Preference.isfullscreenshow = false;

                                            if (close_adListener != null) {
                                                close_adListener.onAdClosed();
                                            }
                                            Glob.isTimeInterval = false;
                                            new Handler().postDelayed(() -> Glob.isTimeInterval = true, Long.parseLong(String.valueOf(newApp_preference.getAdTimeInterval())) * 1000);
                                        }

                                        @Override
                                        public void onError(Ad ad, AdError adError) {
                                            Log.e("TAG", "onError: " + adError.getErrorCode());
                                            if (adloadingCustomDialog.isShowing()) {
                                                adloadingCustomDialog.dismiss();
                                            }
                                            NewApp_Preference.isfullscreenshow = false;
                                            Inter_Full_QurekaFailPredchamp.Shows_QurekaPredchampAd(source_class, close_adListener);
                                            if (!orgnc_type.equalsIgnoreCase("organic")) {
                                                Glob.Open_Url_To_View(source_class);
                                            }
                                            Glob.isTimeInterval = false;
                                            new Handler().postDelayed(() -> Glob.isTimeInterval = true, Long.parseLong(String.valueOf(newApp_preference.getAdTimeInterval())) * 1000);
                                        }

                                        @Override
                                        public void onAdLoaded(Ad ad) {
                                            if (!fb_interstitial.isAdLoaded()) {
                                                return;
                                            }
                                            if (fb_interstitial.isAdInvalidated()) {
                                                return;
                                            }
                                            fb_interstitial.show();
                                            Glob.Full_AD_Counter++;
                                        }

                                        @Override
                                        public void onAdClicked(Ad ad) {

                                        }

                                        @Override
                                        public void onLoggingImpression(Ad ad) {

                                        }
                                    })
                                    .build());
                }
            });
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }
}
