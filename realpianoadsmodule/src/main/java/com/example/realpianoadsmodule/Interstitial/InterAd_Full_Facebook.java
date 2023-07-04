package com.example.realpianoadsmodule.Interstitial;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import com.example.realpianoadsmodule.Other.Adloading_Custom_Dialog;
import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;

public class InterAd_Full_Facebook {

    public static void Show_AdFacebook(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Glob.Full_AD_Counter++;
            final Adloading_Custom_Dialog adloadingCustomDialog = new Adloading_Custom_Dialog(source_class, "Loading Ads");
            adloadingCustomDialog.setCancelable(false);
            adloadingCustomDialog.show();
            com.facebook.ads.InterstitialAd fb_interstitial = new com.facebook.ads.InterstitialAd(source_class, newApp_preference.getFacebookInterstitial());
            fb_interstitial.loadAd(
                    fb_interstitial.buildLoadAdConfig()
                            .withAdListener(new InterstitialAdListener() {
                                @Override
                                public void onInterstitialDisplayed(Ad ad) {
                                    NewApp_Preference.isfullscreenshow = true;
                                }

                                @Override
                                public void onInterstitialDismissed(Ad ad) {
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
                                public void onError(Ad ad, AdError adError) {
                                    NewApp_Preference.isfullscreenshow = false;
                                    Log.e("TAG", "onError: " + adError.getErrorCode());
                                    if (adloadingCustomDialog.isShowing()) {
                                        adloadingCustomDialog.dismiss();
                                    }
                                    if (close_adListener != null) {
                                        close_adListener.onAdClosed();
                                    }
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
                                }

                                @Override
                                public void onAdClicked(Ad ad) {

                                }

                                @Override
                                public void onLoggingImpression(Ad ad) {

                                }
                            })
                            .build());
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }
}
