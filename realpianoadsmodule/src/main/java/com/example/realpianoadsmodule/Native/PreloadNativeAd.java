package com.example.realpianoadsmodule.Native;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.example.realpianoadsmodule.R;
import com.facebook.ads.NativeAdView;
import com.facebook.ads.NativeAdViewAttributes;
import com.google.android.gms.ads.nativead.NativeAd;

import java.util.Random;


public class PreloadNativeAd {
    public static Activity activitys;
    public static NewApp_Preference newApp_preference;
    public static PreloadNativeAd mInstance;

    public PreloadNativeAd(Activity activity) {
        activitys = activity;
        newApp_preference = new NewApp_Preference(activity);
    }

    public static PreloadNativeAd getInstance(Activity mContext) {
        activitys = mContext;
        newApp_preference = new NewApp_Preference(mContext);
        if (mInstance == null) {
            mInstance = new PreloadNativeAd(mContext);
        }
        return mInstance;
    }

    public void Add_Native_Inlayout(FrameLayout frameLayout, boolean isList) {
        String type = isList ? new NewApp_Preference(activitys).getNativeTypeList() : new NewApp_Preference(activitys).getNativeTypeOther();
        switch (type) {
            case "banner":
                if (newApp_preference.getNativeFlag().equalsIgnoreCase("on")) {
                    Native_BannerAd(activitys, frameLayout);
                } else {
                    frameLayout.setVisibility(View.GONE);
                }
                break;
            case "small":
                if (newApp_preference.getNativeFlag().equalsIgnoreCase("on")) {
                    Native_SmallAd(activitys, frameLayout);
                } else {
                    frameLayout.setVisibility(View.GONE);
                }
                break;
            case "medium":
                if (newApp_preference.getNativeFlag().equalsIgnoreCase("on")) {
                    Native_MediumAd(activitys, frameLayout);
                } else {
                    frameLayout.setVisibility(View.GONE);
                }
                break;
            case "large":
                if (newApp_preference.getNativeFlag().equalsIgnoreCase("on")) {
                    Native_LargeAd(activitys, frameLayout);
                } else {
                    frameLayout.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void inflateAd(Activity activitys, com.facebook.ads.NativeAd nativeAd, FrameLayout frameLayout, int height) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activitys);
        int textColor = Color.parseColor("#" + newApp_preference.getTextColor());
        int btnColor = Color.parseColor("#" + newApp_preference.getAdbtColor());
        int backColor = Color.parseColor("#" + newApp_preference.getBackColor());

        NativeAdViewAttributes viewAttributes = new NativeAdViewAttributes(activitys)
                .setBackgroundColor(backColor)
                .setTitleTextColor(textColor)
                .setDescriptionTextColor(textColor)
                .setButtonColor(btnColor)
                .setButtonTextColor(Color.WHITE);

        View adView = NativeAdView.render(activitys, nativeAd, viewAttributes);
        frameLayout.removeAllViews();
        frameLayout.addView(adView, new ViewGroup.LayoutParams(MATCH_PARENT, height));
    }

    private void Native_Qureka(final ViewGroup BannerContainer) {
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            if (newApp_preference.getQurekaFlag().equalsIgnoreCase("qureka")) {
                View view = LayoutInflater.from(activitys).inflate(R.layout.itemqureka_nativead, null, false);
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                ((ImageView) view.findViewById(R.id.qureka_banner)).setImageResource(Glob.qurekaNative[i1]);
                ((TextView) view.findViewById(R.id.tv_appname)).setText(Glob.qurekaHeader[i1]);
                ((TextView) view.findViewById(R.id.tv_desc)).setText(Glob.qurekaDescription[i1]);
                Glide.with(activitys).load(Glob.qurekaIcon[i1]).into((ImageView) view.findViewById(R.id.img_logo));
                view.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(activitys));
                BannerContainer.removeAllViews();
                BannerContainer.addView(view);
            } else if (newApp_preference.getQurekaFlag().equalsIgnoreCase("predchamp")) {
                View view = LayoutInflater.from(activitys).inflate(R.layout.itemqureka_nativead, null, false);
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                Glide.with(activitys).load(Glob.predchampIcon[i1]).into((ImageView) view.findViewById(R.id.img_logo));
                ((TextView) view.findViewById(R.id.tv_appname)).setText(Glob.predchampHeader[i1]);
                ((TextView) view.findViewById(R.id.tv_desc)).setText(Glob.predchampDescription[i1]);
                ((ImageView) view.findViewById(R.id.qureka_banner)).setImageResource(Glob.predchampNative[i1]);
                view.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(activitys));
                BannerContainer.removeAllViews();
                BannerContainer.addView(view);
            } else {
                BannerContainer.setVisibility(View.GONE);
            }
        }
    }

    public void Native_BannerAd(Activity activity, final FrameLayout frameLayout) {
        new StaticNativeAds(activitys).Native_BannerAd(frameLayout);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Object nativeAd = NativeAdLoad_All.getNextNativeAd(activity);
            if (nativeAd != null) {
                if (nativeAd instanceof NativeAd) {
                    View inflate = LayoutInflater.from(activitys).inflate(R.layout.native_ads_template, frameLayout, false);
                    final Template_V templateView = inflate.findViewById(R.id.my_template_small);
                    inflate.findViewById(R.id.my_template_small).setVisibility(View.GONE);
                    templateView.setVisibility(View.GONE);
                    TemplateNativeStyle build = new TemplateNativeStyle.Builder().build();
                    templateView.setVisibility(View.VISIBLE);
                    templateView.setStyles(build);
                    templateView.Set_NativeAd((NativeAd) nativeAd);
                    frameLayout.setVisibility(View.VISIBLE);
                    frameLayout.removeAllViews();
                    frameLayout.addView(inflate);
                } else if (nativeAd instanceof com.facebook.ads.NativeAd) {
                    inflateAd(activitys, (com.facebook.ads.NativeAd) nativeAd, frameLayout, 300);
                }
            } else {
                Native_Qureka_Banner(frameLayout);
            }
        }
    }

    private void Native_Qureka_Banner(final ViewGroup BannerContainer) {
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {

            if (newApp_preference.getQurekaFlag().equalsIgnoreCase("qureka")) {
                View view = LayoutInflater.from(activitys).inflate(R.layout.qurekanative_bannerad, null, false);
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                ((TextView) view.findViewById(R.id.tv_appname)).setText(Glob.qurekaHeader[i1]);
                ((TextView) view.findViewById(R.id.tv_desc)).setText(Glob.qurekaDescription[i1]);
                Glide.with(activitys).load(Glob.qurekaIcon[i1]).into((ImageView) view.findViewById(R.id.img_logo));
                view.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(activitys));
                BannerContainer.setVisibility(View.VISIBLE);
                BannerContainer.removeAllViews();
                BannerContainer.addView(view);
            } else if (newApp_preference.getQurekaFlag().equalsIgnoreCase("predchamp")) {
                View view = LayoutInflater.from(activitys).inflate(R.layout.qurekanative_bannerad, null, false);
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                ((TextView) view.findViewById(R.id.tv_appname)).setText(Glob.predchampHeader[i1]);
                ((TextView) view.findViewById(R.id.tv_desc)).setText(Glob.predchampDescription[i1]);
                Glide.with(activitys).load(Glob.predchampIcon[i1]).into((ImageView) view.findViewById(R.id.img_logo));
                view.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(activitys));
                BannerContainer.setVisibility(View.VISIBLE);

                BannerContainer.removeAllViews();
                BannerContainer.addView(view);
            } else {
                BannerContainer.setVisibility(View.GONE);
            }
        }
    }

    public void Native_LargeAd(Activity activity, final FrameLayout frameLayout) {
        new StaticNativeAds(activitys).NativeAd_(frameLayout);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Object nativeAd = NativeAdLoad_All.getNextNativeAd(activity);
            if (nativeAd != null) {
                if (nativeAd instanceof NativeAd) {
                    View inflate = LayoutInflater.from(activitys).inflate(R.layout.native_ads_template, frameLayout, false);
                    final Template_V templateView = inflate.findViewById(R.id.my_template_large);
                    inflate.findViewById(R.id.my_template_small).setVisibility(View.GONE);
                    templateView.setVisibility(View.GONE);
                    TemplateNativeStyle build = new TemplateNativeStyle.Builder().build();
                    templateView.setVisibility(View.VISIBLE);
                    templateView.setStyles(build);
                    templateView.Set_NativeAd((NativeAd) nativeAd);
                    frameLayout.removeAllViews();
                    frameLayout.addView(inflate);
                } else if (nativeAd instanceof com.facebook.ads.NativeAd) {
                    inflateAd(activitys, (com.facebook.ads.NativeAd) nativeAd, frameLayout, 500);
                }
            } else {
                Native_Qureka(frameLayout);
            }
        }
    }

    public void Native_MediumAd(Activity activity, final FrameLayout frameLayout) {
        new StaticNativeAds(activitys).Native_BannerAd(frameLayout);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Object nativeAd = NativeAdLoad_All.getNextNativeAd(activity);
            if (nativeAd != null) {
                if (nativeAd instanceof NativeAd) {
                    View inflate = LayoutInflater.from(activitys).inflate(R.layout.native_ads_template, frameLayout, false);
                    final Template_V templateView = inflate.findViewById(R.id.my_template_large);
                    inflate.findViewById(R.id.my_template_small).setVisibility(View.GONE);
                    templateView.setVisibility(View.GONE);
                    TemplateNativeStyle build = new TemplateNativeStyle.Builder().build();
                    templateView.setVisibility(View.VISIBLE);
                    templateView.setStyles(build);
                    templateView.Set_NativeAd((NativeAd) nativeAd);
                    frameLayout.removeAllViews();
                    frameLayout.addView(inflate);
                } else if (nativeAd instanceof com.facebook.ads.NativeAd) {
                    inflateAd(activitys, (com.facebook.ads.NativeAd) nativeAd, frameLayout, 400);
                }
            } else {
                Native_Qureka(frameLayout);
            }
        }
    }

    public void Native_SmallAd(Activity activity, final FrameLayout frameLayout) {
        new StaticNativeAds(activitys).AdaptiveBanner(frameLayout);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            Object nativeAd = NativeAdLoad_All.getNextNativeAd(activity);
            if (nativeAd != null) {
                if (nativeAd instanceof NativeAd) {
                    View inflate = LayoutInflater.from(activitys).inflate(R.layout.native_ads_template, frameLayout, false);
                    final Template_V templateView = inflate.findViewById(R.id.my_template_small);
                    inflate.findViewById(R.id.my_template_small).setVisibility(View.GONE);
                    templateView.setVisibility(View.GONE);
                    TemplateNativeStyle build = new TemplateNativeStyle.Builder().build();
                    templateView.setVisibility(View.VISIBLE);
                    templateView.setStyles(build);
                    templateView.Set_NativeAd((NativeAd) nativeAd);
                    frameLayout.setVisibility(View.VISIBLE);
                    frameLayout.removeAllViews();
                    frameLayout.addView(inflate);
                } else if (nativeAd instanceof com.facebook.ads.NativeAd) {
                    inflateAd(activitys, (com.facebook.ads.NativeAd) nativeAd, frameLayout, 300);
                }
            } else {
                Native_Qureka_Banner(frameLayout);
            }
        }
    }

    private void Adaptive_Qureka(final ViewGroup BannerContainer) {
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            if (newApp_preference.getQurekaFlag().equalsIgnoreCase("qureka")) {
                View view = LayoutInflater.from(activitys).inflate(R.layout.qureka_adaptivead, null, false);
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                ((ImageView) view.findViewById(R.id.qureka_banner)).setImageResource(Glob.qurekaBanner[i1]);
                view.setOnClickListener(v -> Glob.OpenQurekaAd(activitys));
                BannerContainer.removeAllViews();
                BannerContainer.addView(view);
            } else if (newApp_preference.getQurekaFlag().equalsIgnoreCase("predchamp")) {
                View view = LayoutInflater.from(activitys).inflate(R.layout.qureka_adaptivead, null, false);
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                ((ImageView) view.findViewById(R.id.qureka_banner)).setImageResource(Glob.predchampBanner[i1]);
                view.setOnClickListener(v -> Glob.OpenQurekaAd(activitys));
                BannerContainer.removeAllViews();
                BannerContainer.addView(view);
            } else {
                BannerContainer.setVisibility(View.GONE);
            }
        }
    }
}
