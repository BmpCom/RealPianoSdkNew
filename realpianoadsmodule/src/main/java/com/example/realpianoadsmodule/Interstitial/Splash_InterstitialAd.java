package com.example.realpianoadsmodule.Interstitial;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Splash_InterstitialAd {
    private InterstitialAd mInterstitialAd_admob;
    private AdManagerInterstitialAd adManagerInterstitialAd;

    public void ShowAds_FullSplash(Activity source_class, Intent intent, final boolean flag) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdFlag().equals("admob")) {
            Show_AdmobSplash(source_class, intent, flag);
        } else
            Show_AdxSplash(source_class, intent, flag);
    }

    public void Show_AdmobSplash(Activity source_class, Intent intent, final boolean flag) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on") && newApp_preference.isConnected()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(source_class, newApp_preference.getSplashInterId(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd_admob = interstitialAd;
                    mInterstitialAd_admob.show(source_class);
                    mInterstitialAd_admob.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            mInterstitialAd_admob = null;
                            NewApp_Preference.isfullscreenshow = false;
                            if (flag) {
                                source_class.startActivity(intent);
                                source_class.finish();
                            } else {
                                source_class.startActivity(intent);
                            }
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            mInterstitialAd_admob = null;
                            NewApp_Preference.isfullscreenshow = true;
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            NewApp_Preference.isfullscreenshow = false;

                            if (flag) {
                                source_class.startActivity(intent);
                                source_class.finish();
                            } else {
                                source_class.startActivity(intent);
                            }
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
                    NewApp_Preference.isfullscreenshow = false;

                    if (flag) {
                        source_class.startActivity(intent);
                        source_class.finish();
                    } else {
                        source_class.startActivity(intent);
                    }
                }
            });
        } else {
            if (flag) {
                source_class.startActivity(intent);
                source_class.finish();
            } else {
                source_class.startActivity(intent);
            }
        }
    }

    public void Show_AdxSplash(Activity source_class, Intent intent, final boolean flag) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on") && newApp_preference.isConnected()) {
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            AdManagerInterstitialAd.load(source_class, newApp_preference.getSplashInterId(), adRequest, new AdManagerInterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                    adManagerInterstitialAd = interstitialAd;
                    adManagerInterstitialAd.show(source_class);
                    adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            adManagerInterstitialAd = null;
                            NewApp_Preference.isfullscreenshow = false;
                            if (flag) {
                                source_class.startActivity(intent);
                                source_class.finish();
                            } else {
                                source_class.startActivity(intent);
                            }
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            adManagerInterstitialAd = null;
                            NewApp_Preference.isfullscreenshow = true;
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            NewApp_Preference.isfullscreenshow = false;
                            if (flag) {
                                source_class.startActivity(intent);
                                source_class.finish();
                            } else {
                                source_class.startActivity(intent);
                            }
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
                    if (flag) {
                        source_class.startActivity(intent);
                        source_class.finish();
                    } else {
                        source_class.startActivity(intent);
                    }
                }
            });
        } else {
            if (flag) {
                source_class.startActivity(intent);
                source_class.finish();
            } else {
                source_class.startActivity(intent);
            }
        }
    }

}
