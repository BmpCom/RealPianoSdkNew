package com.example.realpianoadsmodule.Open;

import static androidx.lifecycle.Lifecycle.Event.ON_START;
import static com.example.realpianoadsmodule.Other.NewApp_Preference.isfullscreenshow;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.MainApplicationClass;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ApplicationOpenMngr implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private static final String LOG_TAG = "ApplicationOpenMngr";
    private AppOpenAd appOpenAd = null;
    private static boolean isShowingAd = false;
    public static boolean isSplash = false;
    private long loadTime = 0;
    private Activity currentActivity;
    private final MainApplicationClass myApplication;

    public ApplicationOpenMngr(MainApplicationClass myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

    }

    public void RequestAd() {
        final List<String> adUnitIds = Arrays.asList(new NewApp_Preference(myApplication).getAdmobOpenAppId1(),
                new NewApp_Preference(myApplication).getAdmobOpenAppId2(),
                new NewApp_Preference(myApplication).getAdmobOpenAppId3());
        if (isAdAvailable()) {
            return;
        }

        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                Log.e("TAG", "onAdLoaded: ");
                super.onAdLoaded(appOpenAd);
                ApplicationOpenMngr.this.appOpenAd = appOpenAd;
                ApplicationOpenMngr.this.loadTime = (new Date()).getTime();
                Glob.OpenAdCounter++;
                Log.e("OpenAdCounter", String.valueOf(Glob.OpenAdCounter));
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("TAG", "onAdFailedToLoad: " + loadAdError.getMessage());
            }
        };
        if (new NewApp_Preference(myApplication).getAdStatus().equalsIgnoreCase("on")) {
            if (Glob.OpenAdCounter > 2) {
                Glob.OpenAdCounter = 0;
            }
            Log.e("OpenAdCounter out", String.valueOf(Glob.OpenAdCounter));
            AdRequest request = getAdRequest();
            AppOpenAd.load(
                    myApplication, adUnitIds.get(Glob.OpenAdCounter), request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
        }
        // We will implement this below.
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public void DisplayAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (isfullscreenshow) {
            return;
        }
        if (!isShowingAd && isAdAvailable()) {
            Log.e(LOG_TAG, "Will show ad.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            ApplicationOpenMngr.this.appOpenAd = null;
                            isShowingAd = false;
                            RequestAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);

        } else {
            Log.e(LOG_TAG, "Can not show ad.");
            RequestAd();
        }
    }

    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo();
    }

    private boolean wasLoadTimeLessThanNHoursAgo() {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * (long) 4));
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        if (isSplash) {
            Log.d(LOG_TAG, "no open ad for splash");
        } else {
            DisplayAdIfAvailable();
        }
    }
}