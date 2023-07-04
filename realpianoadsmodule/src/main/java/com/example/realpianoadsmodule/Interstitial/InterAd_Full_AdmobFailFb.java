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
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterAd_Full_AdmobFailFb {
    public static InterstitialAd interstitialAd_ads;
    public static AdManagerInterstitialAd managerInterstitialAd;

    public static void ShowAd_AdmobORAdx(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, String oraganictype) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdFlag().equals("admob")) {
            Show_AdAdmob(source_class, close_adListener, oraganictype);
        } else {
            Show_AdAdx(source_class, close_adListener, oraganictype);
        }
    }

    public static void Show_AdAdmob(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, String oraganictype) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Glob.Full_AD_Counter++;
            final Adloading_Custom_Dialog adloadingCustomDialog = new Adloading_Custom_Dialog(source_class, "Loading Ads");
            adloadingCustomDialog.setCancelable(false);
            adloadingCustomDialog.show();
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(source_class, new NewApp_Preference(source_class).getAdmobInterId1(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    interstitialAd_ads = interstitialAd;
                    interstitialAd_ads.show(source_class);
                    interstitialAd_ads.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            interstitialAd_ads = null;
                            NewApp_Preference.isfullscreenshow = false;
                            if (adloadingCustomDialog.isShowing()) {
                                adloadingCustomDialog.dismiss();
                            }
                            Glob.isTimeInterval = false;
                            new Handler().postDelayed(() -> Glob.isTimeInterval = true, Long.parseLong(String.valueOf(newApp_preference.getAdTimeInterval())) * 1000);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            interstitialAd_ads = null;
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
                    interstitialAd_ads = null;
                    NewApp_Preference.isfullscreenshow = false;
                    Load_FbAD(source_class, close_adListener, adloadingCustomDialog, oraganictype);
                }
            });
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }

    public static void Show_AdAdx(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, String oraganictype) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Glob.Full_AD_Counter++;
            final Adloading_Custom_Dialog adloadingCustomDialog = new Adloading_Custom_Dialog(source_class, "Loading Ads");
            adloadingCustomDialog.setCancelable(false);
            adloadingCustomDialog.show();
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            AdManagerInterstitialAd.load(source_class, new NewApp_Preference(source_class).getAdmobInterId1(), adRequest, new AdManagerInterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                    managerInterstitialAd = interstitialAd;
                    managerInterstitialAd.show(source_class);
                    managerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            managerInterstitialAd = null;
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
                            managerInterstitialAd = null;
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
                    managerInterstitialAd = null;
                    NewApp_Preference.isfullscreenshow = false;
                    Load_FbAD(source_class, close_adListener, adloadingCustomDialog, oraganictype);
                }
            });
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }

    private static void Load_FbAD(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener, Adloading_Custom_Dialog adloadingCustomDialog, String oraganictype) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        com.facebook.ads.InterstitialAd interstitialAd_fb = new com.facebook.ads.InterstitialAd(source_class, newApp_preference.getFacebookInterstitial());
        interstitialAd_fb.loadAd(
                interstitialAd_fb.buildLoadAdConfig()
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
                            public void onError(Ad ad, AdError adError) {
                                Log.e("TAG", "onError: " + adError.getErrorCode());
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

                            @Override
                            public void onAdLoaded(Ad ad) {
                                if (!interstitialAd_fb.isAdLoaded()) {
                                    return;
                                }
                                if (interstitialAd_fb.isAdInvalidated()) {
                                    return;
                                }
                                interstitialAd_fb.show();
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

    public static void Show_AdWithTime(Activity source_class) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(source_class, newApp_preference.getAdmobInterId1(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                    interstitialAd_ads = interstitialAd;
                    interstitialAd_ads.show(source_class);
                    interstitialAd_ads.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            NewApp_Preference.isfullscreenshow = false;

                            interstitialAd_ads = null;
                            com.facebook.ads.InterstitialAd interstitialAd_fb = new com.facebook.ads.InterstitialAd(source_class, newApp_preference.getFacebookInterstitial());
                            interstitialAd_fb.loadAd(
                                    interstitialAd_fb.buildLoadAdConfig()
                                            .withAdListener(new InterstitialAdListener() {
                                                @Override
                                                public void onInterstitialDisplayed(Ad ad) {
                                                    NewApp_Preference.isfullscreenshow = true;
                                                }

                                                @Override
                                                public void onInterstitialDismissed(Ad ad) {
                                                    NewApp_Preference.isfullscreenshow = false;
                                                }

                                                @Override
                                                public void onError(Ad ad, AdError adError) {
                                                    NewApp_Preference.isfullscreenshow = false;

                                                    AdRequest adRequest = new AdRequest.Builder().build();
                                                    InterstitialAd.load(source_class, newApp_preference.getAdmobInterId1(), adRequest, new InterstitialAdLoadCallback() {
                                                        @Override
                                                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                                            interstitialAd_ads = interstitialAd;
                                                            interstitialAd_ads.show(source_class);
                                                            interstitialAd_ads.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                                @Override
                                                                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                                                    super.onAdFailedToShowFullScreenContent(adError);
                                                                    NewApp_Preference.isfullscreenshow = false;
                                                                    interstitialAd_ads = null;
                                                                }

                                                                @Override
                                                                public void onAdShowedFullScreenContent() {
                                                                    super.onAdShowedFullScreenContent();
                                                                    interstitialAd_ads = null;
                                                                    NewApp_Preference.isfullscreenshow = true;
                                                                }

                                                                @Override
                                                                public void onAdDismissedFullScreenContent() {
                                                                    super.onAdDismissedFullScreenContent();
                                                                    NewApp_Preference.isfullscreenshow = false;
                                                                }

                                                                @Override
                                                                public void onAdImpression() {
                                                                    super.onAdImpression();
                                                                }
                                                            });
                                                        }

                                                        @Override
                                                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                                            NewApp_Preference.isfullscreenshow = false;

                                                            interstitialAd_ads = null;
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onAdLoaded(Ad ad) {
                                                    interstitialAd_fb.show();
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

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            interstitialAd_ads = null;
                            NewApp_Preference.isfullscreenshow = true;
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            NewApp_Preference.isfullscreenshow = false;
                            super.onAdDismissedFullScreenContent();
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    NewApp_Preference.isfullscreenshow = false;
                    interstitialAd_ads = null;
                }
            });
        }
    }
}
