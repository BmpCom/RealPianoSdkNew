package com.example.realpianoadsmodule.Other;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.example.realpianoadsmodule.Native.NativeAdLoad_All;
import com.example.realpianoadsmodule.Open.ApplicationOpenMngr;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainApplicationClass extends MultiDexApplication {
    private static MainApplicationClass instance;

    public static void Load_Ads_Init(OnInitializationCompleteListener listener) {
        MobileAds.initialize(
                instance,
                initializationStatus -> {
                    listener.onInitializationComplete(initializationStatus);
                    if (new NewApp_Preference(instance).getOpenFlag().equalsIgnoreCase("on")) {
                        new ApplicationOpenMngr(instance);
                    }
                    if (new NewApp_Preference(instance).getAdStatus().equalsIgnoreCase("on")) {
                        if (new NewApp_Preference(instance).getNativeFlag().equalsIgnoreCase("on")) {
                            new NativeAdLoad_All(instance);
                        }
                    }
                });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AudienceNetworkAds.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
