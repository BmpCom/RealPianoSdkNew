package com.example.realpianoadsmodule.Interstitial;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.realpianoadsmodule.Native.StaticNativeAds;
import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.example.realpianoadsmodule.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Adaptive_Banner {
    public Context context;
    NewApp_Preference preference;

    public Adaptive_Banner(Context context) {
        this.context = context;
        preference = new NewApp_Preference(this.context);
    }

    public void Adaptive_Banner(final ViewGroup viewGroup) {
        if (preference.getAdStatus().equalsIgnoreCase("on")) {
            if (preference.getBannerFlag().equalsIgnoreCase("on")) {
                new StaticNativeAds(context).AdaptiveBanner(viewGroup);
                if (preference.getAdstyleBanner().equalsIgnoreCase("Normal")) {
                    Admob_Adaptive_Banner(viewGroup);
                } else if (preference.getAdstyleBanner().equalsIgnoreCase("ALT")) {
                    if (Glob.Alt_Banner_Cntr == 2) {
                        Admob_Adaptive_Banner(viewGroup);
                        Glob.Alt_Banner_Cntr = 1;
                    } else {
                        Facebook_Adaptive_Banner(viewGroup);
                        Glob.Alt_Banner_Cntr++;
                    }
                } else if (preference.getAdstyleBanner().equalsIgnoreCase("multiple")) {
                    Admob_FB_Multiple_Adaptive_Banner(viewGroup);
                } else if (preference.getAdstyleBanner().equalsIgnoreCase("fb")) {
                    Facebook_Adaptive_Banner(viewGroup);
                }
            }
        }
    }

    private void Admob_FB_Multiple_Adaptive_Banner(final ViewGroup viewGroup) {
        final List<String> adUnitIds = Arrays.asList(new NewApp_Preference(context).getAdmobBannerId1(),
                new NewApp_Preference(context).getAdmobBannerId2(),
                new NewApp_Preference(context).getAdmobBannerId3());
        try {
            if (Glob.Banner_AD_Counter > 2) {
                Glob.Banner_AD_Counter = 0;
            }
            Log.e("Banner_AD_Counter out", String.valueOf(Glob.Banner_AD_Counter));
            final AdView adView = new AdView(context);
            adView.setAdSize(getAdSize(context));
            adView.setAdUnitId(adUnitIds.get(Glob.Banner_AD_Counter));
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Log.e("TAG", "Admob  Fail -> onAdFailedToLoad: Banner" + loadAdError.getMessage());
                    super.onAdFailedToLoad(loadAdError);
                    try {
                        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, preference.getFacebookBanner(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                        viewGroup.removeAllViews();
                        viewGroup.addView(adView);
                        adView.loadAd(adView.buildLoadAdConfig().withAdListener(new com.facebook.ads.AdListener() {
                            @Override
                            public void onError(Ad ad, AdError adError) {
                                Log.e("TAG", " Facebook Adaptive_Banner  onAdFailedToLoad  " + adError.getErrorCode());
                                Qureka_Predchamp_Adaptive(viewGroup);
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {

                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        }).build());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.e("TAG", "Admob Load -> onAdLoaded: Banner");
                    Glob.Banner_AD_Counter++;
                    Log.e("Banner_AD_Counter", String.valueOf(Glob.Banner_AD_Counter));
                    try {
                        viewGroup.removeAllViews();
                        viewGroup.addView(adView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Admob_Adaptive_Banner(final ViewGroup viewGroup) {
        try {
            final AdView adView = new AdView(context);
            adView.setAdSize(getAdSize(context));
            adView.setAdUnitId(preference.getAdmobBannerId1());
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Log.e("TAG", "Admob  Fail -> onAdFailedToLoad: Banner" + loadAdError.getMessage());
                    super.onAdFailedToLoad(loadAdError);
                    com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, preference.getFacebookBanner(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                    viewGroup.removeAllViews();
                    viewGroup.addView(adView);
                    adView.loadAd(adView.buildLoadAdConfig().withAdListener(new com.facebook.ads.AdListener() {
                        @Override
                        public void onError(Ad ad, AdError adError) {
                            Log.e("TAG", " Facebook Adaptive_Banner  onAdFailedToLoad  " + adError.getErrorCode());
                            Qureka_Predchamp_Adaptive(viewGroup);
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {

                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }
                    }).build());
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.e("TAG", "Admob Load -> onAdLoaded: Banner");

                    try {
                        viewGroup.removeAllViews();
                        viewGroup.addView(adView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Facebook_Adaptive_Banner(final ViewGroup viewGroup) {
        try {
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, preference.getFacebookBanner(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            viewGroup.removeAllViews();
            viewGroup.addView(adView);
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    Log.e("TAG", " Facebook Adaptive_Banner  onAdFailedToLoad  " + adError.getErrorCode());
                    final AdView adView = new AdView(context);
                    adView.setAdSize(getAdSize(context));
                    adView.setAdUnitId(preference.getAdmobBannerId1());
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                    adView.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.e("TAG", "Admob  Fail -> onAdFailedToLoad: Banner" + loadAdError.getMessage());
                            super.onAdFailedToLoad(loadAdError);
                            Qureka_Predchamp_Adaptive(viewGroup);
                        }

                        @Override
                        public void onAdOpened() {
                            super.onAdOpened();
                        }

                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            Log.e("TAG", "Admob Load -> onAdLoaded: Banner");

                            try {
                                viewGroup.removeAllViews();
                                viewGroup.addView(adView);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAdClicked() {
                            super.onAdClicked();
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    });
                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            }).build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Qureka_Predchamp_Adaptive(final ViewGroup BannerContainer) {
        if (preference.getAdStatus().equalsIgnoreCase("on")) {
            if (preference.getQurekaFlag().equalsIgnoreCase("qureka")) {
                View view = LayoutInflater.from(context).inflate(R.layout.qureka_adaptivead, null, false);
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                ((ImageView) view.findViewById(R.id.qureka_banner)).setImageResource(Glob.qurekaBanner[i1]);
                view.setOnClickListener(v -> Glob.OpenQurekaAd(context));
                BannerContainer.removeAllViews();
                BannerContainer.addView(view);
            } else if (preference.getQurekaFlag().equalsIgnoreCase("predchamp")) {
                View view = LayoutInflater.from(context).inflate(R.layout.qureka_adaptivead, null, false);
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                ((ImageView) view.findViewById(R.id.qureka_banner)).setImageResource(Glob.predchampBanner[i1]);
                view.setOnClickListener(v -> Glob.OpenQurekaAd(context));
                BannerContainer.removeAllViews();
                BannerContainer.addView(view);
            } else {
                new StaticNativeAds(context).AdaptiveBanner(BannerContainer);
            }
        }
    }

    private static AdSize getAdSize(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

}
