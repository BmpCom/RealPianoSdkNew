package com.example.realpianoadsmodule.Interstitial;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.realpianoadsmodule.Other.Adloading_Custom_Dialog;
import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterAd_Full_FbFailAdmob {

    public static InterstitialAd mInterstitialAd_admob;
    public static AdManagerInterstitialAd adManagerInterstitialAd;

    public static void Show_AdFb(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, String oraganictype) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
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
                                    if (!oraganictype.equalsIgnoreCase("organic")) {
                                        Glob.Open_Url_To_View(source_class);
                                    }
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
                                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                                    NewApp_Preference.isfullscreenshow = false;
                                    Log.e("TAG", "onError: " + adError.getErrorCode());
                                    ShowAd_Full(source_class, close_adListener, adloadingCustomDialog, oraganictype);
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

    public static void ShowAd_Full(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, Adloading_Custom_Dialog adloadingCustomDialog, String oraganictype) {
        NewApp_Preference appPreference = new NewApp_Preference(source_class);
        if (appPreference.getAdFlag().equals("admob")) {
            Show_AdAdmob(source_class, close_adListener, adloadingCustomDialog, oraganictype);
        } else {
            Show_AdAdx(source_class, close_adListener, adloadingCustomDialog, oraganictype);
        }
    }

    public static void Show_AdAdmob(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, Adloading_Custom_Dialog adloadingCustomDialog, String oraganictype) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(source_class, new NewApp_Preference(source_class).getAdmobInterId1(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd_admob = interstitialAd;
                    mInterstitialAd_admob.show(source_class);
                    mInterstitialAd_admob.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            mInterstitialAd_admob = null;
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
                            mInterstitialAd_admob = null;
                            NewApp_Preference.isfullscreenshow = true;
                            if (!oraganictype.equalsIgnoreCase("organic")) {
                                Glob.Open_Url_To_View(source_class);
                            }
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            if (adloadingCustomDialog.isShowing()) {
                                adloadingCustomDialog.dismiss();
                            }
                            if (close_adListener != null) {
                                close_adListener.onAdClosed();
                            }
                            NewApp_Preference.isfullscreenshow = false;

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
                    mInterstitialAd_admob = null;
                    Show_QurekaAds(source_class, close_adListener, adloadingCustomDialog, newApp_preference, oraganictype);
                }
            });
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }

    private static void Show_QurekaAds(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, Adloading_Custom_Dialog adloadingCustomDialog, NewApp_Preference newApp_preference, String oraganictype) {
        NewApp_Preference.isfullscreenshow = false;
        if (adloadingCustomDialog.isShowing()) {
            adloadingCustomDialog.dismiss();
        }
        NewApp_Preference.isfullscreenshow = false;
        Inter_Full_QurekaFailPredchamp.Shows_QurekaPredchampAd(source_class, close_adListener);
        if (!oraganictype.equalsIgnoreCase("organic")) {
            Glob.Open_Url_To_View(source_class);
        }
        Glob.isTimeInterval = false;
        new Handler().postDelayed(() -> Glob.isTimeInterval = true, Long.parseLong(String.valueOf(newApp_preference.getAdTimeInterval())) * 1000);
    }

    public static void Show_AdAdx(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, Adloading_Custom_Dialog adloadingCustomDialog, String oraganictype) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            AdManagerInterstitialAd.load(source_class, new NewApp_Preference(source_class).getAdmobInterId1(), adRequest, new AdManagerInterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                    adManagerInterstitialAd = interstitialAd;
                    adManagerInterstitialAd.show(source_class);
                    adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            adManagerInterstitialAd = null;
                            if (adloadingCustomDialog.isShowing()) {
                                adloadingCustomDialog.dismiss();
                            }
                            if (close_adListener != null) {
                                close_adListener.onAdClosed();
                            }
                            NewApp_Preference.isfullscreenshow = false;
                            Glob.isTimeInterval = false;
                            new Handler().postDelayed(() -> Glob.isTimeInterval = true, Long.parseLong(String.valueOf(newApp_preference.getAdTimeInterval())) * 1000);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            adManagerInterstitialAd = null;
                            NewApp_Preference.isfullscreenshow = true;
                            if (!oraganictype.equalsIgnoreCase("organic")) {
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
                    adManagerInterstitialAd = null;
                    NewApp_Preference.isfullscreenshow = false;
                    Show_QurekaAds(source_class, close_adListener, adloadingCustomDialog, newApp_preference, oraganictype);
                }
            });
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }

}
