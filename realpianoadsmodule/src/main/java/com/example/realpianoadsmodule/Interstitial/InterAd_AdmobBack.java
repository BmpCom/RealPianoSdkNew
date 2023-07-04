package com.example.realpianoadsmodule.Interstitial;


import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.realpianoadsmodule.Main.CheckOrganic_CommanClass;
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

import java.util.Arrays;
import java.util.List;

public class InterAd_AdmobBack {
    public static InterstitialAd interstitialAd_ad;
    public static AdManagerInterstitialAd managerInterstitialAd;

    public static void ShowAd_Full(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getFullFlag().equalsIgnoreCase("on")) {
            if (newApp_preference.getBackFlag().equalsIgnoreCase("on")) {
                if (Glob.Back_Click_Counter % Integer.parseInt(newApp_preference.getBackClick()) == 0) {
                    if (newApp_preference.getBackClickAdStyle().equalsIgnoreCase("admob") || newApp_preference.getBackClickAdStyle().equalsIgnoreCase("normal")) {
                        Show_BackAdAdmob(source_class, close_adListener);
                    } else if (newApp_preference.getBackClickAdStyle().equalsIgnoreCase("adx")) {
                        Show_BackAdAdx(source_class, close_adListener);
                    } else if (newApp_preference.getBackClickAdStyle().equalsIgnoreCase("fb")) {
                        Show_BackAdFacebook(source_class, close_adListener);
                    } else if (newApp_preference.getBackClickAdStyle().equalsIgnoreCase("ALT")) {
                        if (Glob.Back_Inter_Counter == 2) {
                            Glob.Back_Inter_Counter = 1;
                            Show_BackAdAdmob(source_class, close_adListener);
                        } else {
                            Glob.Back_Inter_Counter++;
                            Show_BackAdFacebook(source_class, close_adListener);
                        }
                    } else if (newApp_preference.getBackClickAdStyle().equalsIgnoreCase("multiple")) {
                        Show_BackAdMultiple(source_class, close_adListener);
                    }
                } else {
                    if (close_adListener != null) {
                        close_adListener.onAdClosed();
                    }
                }
            } else {
                if (close_adListener != null) {
                    close_adListener.onAdClosed();
                }
            }
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }

    public static void Show_BackAdMultiple(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener) {
        final List<String> list_UnitIds = Arrays.asList(new NewApp_Preference(source_class).getAdmobInterId1(),
                new NewApp_Preference(source_class).getAdmobInterId2(),
                new NewApp_Preference(source_class).getAdmobInterId3());

        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Glob.Back_Click_Counter++;
            if (Glob.MultipleAdCounter > 2) {
                Glob.MultipleAdCounter = 0;
            }
            final Adloading_Custom_Dialog adloadingCustomDialog = new Adloading_Custom_Dialog(source_class, "Loading Ads");
            adloadingCustomDialog.setCancelable(false);
            adloadingCustomDialog.show();
            AdRequest adRequest = new AdRequest.Builder().build();
            Log.e("selected admob id", String.valueOf(Glob.MultipleAdCounter));
            InterstitialAd.load(source_class, list_UnitIds.get(Glob.MultipleAdCounter), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    interstitialAd_ad = interstitialAd;
                    interstitialAd_ad.show(source_class);
                    Glob.MultipleAdCounter++;
                    Log.e("multiple ad counter", String.valueOf(Glob.MultipleAdCounter));
                    interstitialAd_ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            interstitialAd_ad = null;
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
                            interstitialAd_ad = null;
                            NewApp_Preference.isfullscreenshow = true;
                            if (!CheckOrganic_CommanClass.checkForOrganicOrNot(source_class)) {
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
                    interstitialAd_ad = null;
                    if (adloadingCustomDialog.isShowing()) {
                        adloadingCustomDialog.dismiss();
                    }
                    NewApp_Preference.isfullscreenshow = false;
                    Inter_Full_QurekaFailPredchamp.Shows_QurekaPredchampAd(source_class, close_adListener);
                    if (!CheckOrganic_CommanClass.checkForOrganicOrNot(source_class)) {
                        Glob.Open_Url_To_View(source_class);
                    }
                }
            });
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }

    public static void Show_BackAdFacebook(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Glob.Back_Click_Counter++;
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
                                    if (!CheckOrganic_CommanClass.checkForOrganicOrNot(source_class)) {
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
                                public void onError(Ad ad, AdError adError) {
                                    NewApp_Preference.isfullscreenshow = false;
                                    Log.e("TAG", "onError: " + adError.getErrorCode());
                                    if (adloadingCustomDialog.isShowing()) {
                                        adloadingCustomDialog.dismiss();
                                    }
                                    NewApp_Preference.isfullscreenshow = false;
                                    Inter_Full_QurekaFailPredchamp.Shows_QurekaPredchampAd(source_class, close_adListener);
                                    if (!CheckOrganic_CommanClass.checkForOrganicOrNot(source_class)) {
                                        Glob.Open_Url_To_View(source_class);
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

    public static void Show_BackAdAdmob(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Glob.Back_Click_Counter++;
            final Adloading_Custom_Dialog adloadingCustomDialog = new Adloading_Custom_Dialog(source_class, "Loading Ads");
            adloadingCustomDialog.setCancelable(false);
            adloadingCustomDialog.show();
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(source_class, new NewApp_Preference(source_class).getAdmobInterId1(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    interstitialAd_ad = interstitialAd;
                    interstitialAd_ad.show(source_class);
                    interstitialAd_ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            interstitialAd_ad = null;
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
                            interstitialAd_ad = null;
                            NewApp_Preference.isfullscreenshow = true;
                            if (!CheckOrganic_CommanClass.checkForOrganicOrNot(source_class)) {
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
                    interstitialAd_ad = null;
                    NewApp_Preference.isfullscreenshow = false;
                    if (adloadingCustomDialog.isShowing()) {
                        adloadingCustomDialog.dismiss();
                    }
                    NewApp_Preference.isfullscreenshow = false;
                    Inter_Full_QurekaFailPredchamp.Shows_QurekaPredchampAd(source_class, close_adListener);
                    if (!CheckOrganic_CommanClass.checkForOrganicOrNot(source_class)) {
                        Glob.Open_Url_To_View(source_class);
                    }
                }
            });
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }

    public static void Show_BackAdAdx(Activity source_class, InterstitialAd_Full.Close_AdListener close_adListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Glob.Back_Click_Counter++;
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
                            if (!CheckOrganic_CommanClass.checkForOrganicOrNot(source_class)) {
                                Glob.Open_Url_To_View(source_class);
                            }
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();

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
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    managerInterstitialAd = null;
                    if (adloadingCustomDialog.isShowing()) {
                        adloadingCustomDialog.dismiss();
                    }
                    NewApp_Preference.isfullscreenshow = false;
                    Inter_Full_QurekaFailPredchamp.Shows_QurekaPredchampAd(source_class, close_adListener);
                    if (!CheckOrganic_CommanClass.checkForOrganicOrNot(source_class)) {
                        Glob.Open_Url_To_View(source_class);
                    }
                    Glob.isTimeInterval = false;
                    new Handler().postDelayed(() -> Glob.isTimeInterval = true, Long.parseLong(String.valueOf(newApp_preference.getAdTimeInterval())) * 1000);
                }
            });
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }
}
