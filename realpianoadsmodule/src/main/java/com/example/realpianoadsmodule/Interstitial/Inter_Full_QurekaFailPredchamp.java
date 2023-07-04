package com.example.realpianoadsmodule.Interstitial;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.example.realpianoadsmodule.R;

import java.util.Random;

public class Inter_Full_QurekaFailPredchamp {

    public static void Shows_QurekaPredchampAd(Activity activity, InterstitialAd_Full.Close_AdListener close_adListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            if (newApp_preference.getQurekaFlag().equalsIgnoreCase("qureka")) {
                NewApp_Preference.isfullscreenshow = true;
                final Dialog dialog = new Dialog(activity, R.style.dialogtransparent_);
                dialog.setContentView(R.layout.reward_qurekapredchamp);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                Glide.with(activity).load(Glob.qurekaIcon[i1]).into(((ImageView) dialog.findViewById(R.id.img_coin)));
                Glide.with(activity).load(Glob.qurekaNative[i1]).into(((ImageView) dialog.findViewById(R.id.qureka_banner)));
                ((TextView) dialog.findViewById(R.id.txt_main_title)).setText("Qureka Lite/Play Game");
                ((TextView) dialog.findViewById(R.id.qureka_title)).setText(Glob.qurekaHeader[i1]);
                ((TextView) dialog.findViewById(R.id.qureka_description)).setText(Glob.qurekaDescription[i1]);
                dialog.setOnDismissListener(dialog1 -> {
                    NewApp_Preference.isfullscreenshow = false;
                    if (close_adListener != null) {
                        close_adListener.onAdClosed();
                    }
                });
                dialog.findViewById(R.id.qureka_close).setOnClickListener(v -> dialog.dismiss());
                dialog.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(activity));
                dialog.show();
            } else if (newApp_preference.getQurekaFlag().equalsIgnoreCase("predchamp")) {
                NewApp_Preference.isfullscreenshow = true;
                final Dialog dialog = new Dialog(activity, R.style.dialogtransparent_);
                dialog.setContentView(R.layout.reward_qurekapredchamp);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                Glide.with(activity).load(Glob.predchampIcon[i1]).into(((ImageView) dialog.findViewById(R.id.img_coin)));
                Glide.with(activity).load(Glob.predchampNative[i1]).into(((ImageView) dialog.findViewById(R.id.qureka_banner)));
                ((TextView) dialog.findViewById(R.id.txt_main_title)).setText("PredChamp/Play Game");
                ((TextView) dialog.findViewById(R.id.qureka_title)).setText(Glob.predchampHeader[i1]);
                ((TextView) dialog.findViewById(R.id.qureka_description)).setText(Glob.predchampDescription[i1]);
                dialog.setOnDismissListener(dialog12 -> {
                    NewApp_Preference.isfullscreenshow = false;
                    if (close_adListener != null) {
                        close_adListener.onAdClosed();
                    }
                });
                dialog.findViewById(R.id.qureka_close).setOnClickListener(v -> dialog.dismiss());
                dialog.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(activity));
                dialog.show();
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

    public static void Shows_QurekaPredchampAd(Activity activity) {
        NewApp_Preference newApp_preference = new NewApp_Preference(activity);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            if (newApp_preference.getQurekaFlag().equalsIgnoreCase("qureka")) {
                NewApp_Preference.isfullscreenshow = true;
                Dialog dialog = new Dialog(activity, R.style.dialogtransparent_);
                dialog.setContentView(R.layout.reward_qurekapredchamp);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                Glide.with(activity).load(Glob.qurekaIcon[i1]).into(((ImageView) dialog.findViewById(R.id.img_coin)));
                Glide.with(activity).load(Glob.qurekaNative[i1]).into(((ImageView) dialog.findViewById(R.id.qureka_banner)));
                ((TextView) dialog.findViewById(R.id.txt_main_title)).setText("Qureka Lite/Play Game");
                ((TextView) dialog.findViewById(R.id.qureka_title)).setText(Glob.qurekaHeader[i1]);
                ((TextView) dialog.findViewById(R.id.qureka_description)).setText(Glob.qurekaDescription[i1]);
                dialog.setOnDismissListener(dialog12 -> {
                    NewApp_Preference.isfullscreenshow = false;
                });
                dialog.findViewById(R.id.qureka_close).setOnClickListener(v -> dialog.dismiss());
                dialog.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(activity));
                dialog.show();
            } else if (newApp_preference.getQurekaFlag().equalsIgnoreCase("predchamp")) {
                NewApp_Preference.isfullscreenshow = true;
                Dialog dialog = new Dialog(activity, R.style.dialogtransparent_);
                dialog.setContentView(R.layout.reward_qurekapredchamp);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                Glide.with(activity).load(Glob.predchampIcon[i1]).into(((ImageView) dialog.findViewById(R.id.img_coin)));
                Glide.with(activity).load(Glob.predchampNative[i1]).into(((ImageView) dialog.findViewById(R.id.qureka_banner)));
                ((TextView) dialog.findViewById(R.id.txt_main_title)).setText("PredChamp/Play Game");
                ((TextView) dialog.findViewById(R.id.qureka_title)).setText(Glob.predchampHeader[i1]);
                ((TextView) dialog.findViewById(R.id.qureka_description)).setText(Glob.predchampDescription[i1]);
                dialog.setOnDismissListener(dialog1 -> {
                    NewApp_Preference.isfullscreenshow = false;
                });
                dialog.findViewById(R.id.qureka_close).setOnClickListener(v -> dialog.dismiss());
                dialog.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(activity));
                dialog.show();
            }
        }
    }
}
