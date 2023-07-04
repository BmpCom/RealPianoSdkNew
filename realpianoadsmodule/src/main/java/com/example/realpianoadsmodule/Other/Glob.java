package com.example.realpianoadsmodule.Other;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;

import com.example.realpianoadsmodule.R;

public class Glob {
    public static int Inter_Counter = 1, Alt_Banner_Cntr = 0, Reward_Counter = 1, Banner_AD_Counter = 0, Back_Inter_Counter = 0, Back_Click_Counter = 0, NativeCountIncr = 0, OpenAdCounter = 0, Full_AD_Counter = 0, MultipleAdCounter = 0;
    public static boolean isTimeInterval = true;

    public static int[] qurekaIcon = {R.drawable.qureka_icon1, R.drawable.qureka_icon2, R.drawable.qureka_icon3, R.drawable.qureka_icon4, R.drawable.qureka_icon5};
    public static int[] predchampIcon = {R.drawable.predchampicon1, R.drawable.predchampicon2, R.drawable.predchampicon3, R.drawable.predchampicon4, R.drawable.predchampicon5};

    public static int[] qurekaNative = {R.drawable.qureka_ban1, R.drawable.qureka_ban2, R.drawable.qureka_ban3, R.drawable.qureka_ban4, R.drawable.qureka_ban5};
    public static int[] predchampNative = {R.drawable.predchampbanner1, R.drawable.predchampbanner2, R.drawable.predchampbanner3, R.drawable.predchampbanner4, R.drawable.predchampbanner5};

    public static int[] qurekaBanner = {R.drawable.qureka_banner1, R.drawable.qureka_banner2, R.drawable.qureka_banner3, R.drawable.qureka_banner4, R.drawable.qureka_banner5};
    public static int[] predchampBanner = {R.drawable.predchamp_banner1, R.drawable.predchamp_banner2, R.drawable.predchamp_banner3, R.drawable.predchamp_banner4, R.drawable.predchamp_banner5};

    public static String[] qurekaHeader = {"Play & Win Coins Daily.", "Pool prize is 50,000 coins", "Aaj Math Quiz khela kya?", "Time to Win Now!", "Prize Pool: 50,000 Coins| No install required"};
    public static String[] qurekaDescription = {"Come play Cricket contests running for 50,000 Coins daily.", "Sharpen your Cricket knowledge & win now", "Play and win coins now", "Play Bollywood Quizzes  & win coins daily", "Play IPL contest! Khelo aur jeeto ."};

    public static String[] predchampHeader = {"Play Quiz & Win Now", "SSC Exam Quiz for 50,000 Coins is Live", "Jeeto 10,000 Coins Abhi!", "Mega quiz for 5,00,000 coins open", "Tech quiz for 50,000 coins open"};
    public static String[] predchampDescription = {"Play SSC Exam Quiz & Win Upto 50,000 Coins", "Play Quiz & Win Now", "Play GK, Math & other quizzes & Win Now", "Your chance of winning is high here! Play Now", "Test your tech skills & win now"};

    public static void Share_App_Function(Context context, String appname, String shortlink) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, appname);
            String shareMessage = "\nPlease Download this amazing application\n\n";
            shareMessage = shareMessage + shortlink + "\n\n";
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(intent, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Rate_App_Function(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Unable to Find Market App !", Toast.LENGTH_LONG).show();
        }
    }

    public static void More_App_Function(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=" + new NewApp_Preference(context).getAccount()));
        context.startActivity(intent);
    }

    public static void OpenQurekaAd(Context context) {
        String URL = new NewApp_Preference(context).getQurekaLink();
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(URL));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Open_Url_To_View(Context context) {
        if (new NewApp_Preference(context).getDirectLlink().equalsIgnoreCase("on")) {
            String URL = new NewApp_Preference(context).getUrlFor_ads();
            try {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(URL));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
