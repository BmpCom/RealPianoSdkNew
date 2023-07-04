package com.example.realpianoadsmodule.Interstitial;

import android.app.Activity;

import com.example.realpianoadsmodule.Main.CheckOrganic_CommanClass;
import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.NewApp_Preference;

public class InterstitialAd_Full {

    public void ShowAds_Full(Activity source_class, Close_AdListener close_adListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getFullFlag().equalsIgnoreCase("on")) {
            if (!newApp_preference.getClickFlag().equalsIgnoreCase("on")) {
                if (Glob.isTimeInterval) {
                    Glob.Full_AD_Counter++;
                    StylewiseAds(newApp_preference, source_class, close_adListener, "noorganic");
                } else {
                    if (close_adListener != null) {
                        close_adListener.onAdClosed();
                    }
                }
            } else {
                if (CheckOrganic_CommanClass.checkForOrganicOrNot(source_class)) {
                    if (Glob.Full_AD_Counter % Integer.parseInt(newApp_preference.getOrganicClickCount()) == 0) {
                        Glob.Full_AD_Counter++;
                        StylewiseAds(newApp_preference, source_class, close_adListener, "organic");
                    } else {
                        Glob.Full_AD_Counter++;
                        if (close_adListener != null) {
                            close_adListener.onAdClosed();
                        }
                    }
                } else {
                    if (Glob.Full_AD_Counter % Integer.parseInt(newApp_preference.getClickCount()) == 0) {
                        Glob.Full_AD_Counter++;
                        StylewiseAds(newApp_preference, source_class, close_adListener, "noorganic");
                    } else {
                        Glob.Full_AD_Counter++;
                        if (close_adListener != null) {
                            close_adListener.onAdClosed();
                        }
                    }
                }
            }
        } else {
            if (close_adListener != null) {
                close_adListener.onAdClosed();
            }
        }
    }

    public void StylewiseAds(NewApp_Preference newApp_preference, Activity source_class, Close_AdListener close_adListener, String oraganictype) {
        if (newApp_preference.getAdStyle().equalsIgnoreCase("Normal")) {
            InterAd_Full_AdmobFailFb.ShowAd_AdmobORAdx(source_class, close_adListener, oraganictype);
        } else if (newApp_preference.getAdStyle().equalsIgnoreCase("ALT")) {
            if (Glob.Inter_Counter == 2) {
                Glob.Inter_Counter = 1;
                InterAd_Full_AdmobFailFb.ShowAd_AdmobORAdx(source_class, close_adListener, oraganictype);
            } else {
                Glob.Inter_Counter++;
                InterAd_Full_FbFailAdmob.Show_AdFb(source_class, close_adListener, oraganictype);
            }
        } else if (newApp_preference.getAdStyle().equalsIgnoreCase("fb")) {
            InterAd_Full_FbFailAdmob.Show_AdFb(source_class, close_adListener, oraganictype);
        } else if (newApp_preference.getAdStyle().equalsIgnoreCase("multiple")) {
            InterAd_Full_AdmobFailFbFailQureka_MultipleAd.Show_AdMultiple(source_class, close_adListener, oraganictype);
        }
    }

    public interface Close_AdListener {
        void onAdClosed();
    }
}
