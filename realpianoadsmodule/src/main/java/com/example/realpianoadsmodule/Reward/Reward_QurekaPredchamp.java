package com.example.realpianoadsmodule.Reward;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.R;

import java.util.Random;

public class Reward_QurekaPredchamp {

    public static void Show_QurekaFailPredchampAd(Activity source_class, Intertial_RewardAd.UserEarnRewardListener userEarnRewardListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        if (newApp_preference.getAdStatus().equalsIgnoreCase("on")) {
            if (newApp_preference.getQurekaFlag().equalsIgnoreCase("qureka")) {
                NewApp_Preference.isfullscreenshow = true;
                final Dialog dialog = new Dialog(source_class, R.style.dialogtransparent_);
                dialog.setContentView(R.layout.reward_qurekapredchamp);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                Glide.with(source_class).load(Glob.qurekaIcon[i1]).into(((ImageView) dialog.findViewById(R.id.img_coin)));
                Glide.with(source_class).load(Glob.qurekaNative[i1]).into(((ImageView) dialog.findViewById(R.id.qureka_banner)));
                ((TextView) dialog.findViewById(R.id.txt_main_title)).setText("Qureka Lite/Play Game");
                ((TextView) dialog.findViewById(R.id.qureka_title)).setText(Glob.qurekaHeader[i1]);
                ((TextView) dialog.findViewById(R.id.qureka_description)).setText(Glob.qurekaDescription[i1]);
                dialog.setOnDismissListener(dialog1 -> {
                    NewApp_Preference.isfullscreenshow = false;
                    if (userEarnRewardListener != null) {
                        userEarnRewardListener.onEarnReward();
                    }
                });
                dialog.findViewById(R.id.qureka_close).setOnClickListener(v -> dialog.dismiss());
                dialog.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(source_class));
                dialog.show();
            } else if (newApp_preference.getQurekaFlag().equalsIgnoreCase("predchamp")) {
                NewApp_Preference.isfullscreenshow = true;
                final Dialog dialog = new Dialog(source_class, R.style.dialogtransparent_);
                dialog.setContentView(R.layout.reward_qurekapredchamp);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Random r = new Random();
                int i1 = r.nextInt(4 + 1);
                Glide.with(source_class).load(Glob.predchampIcon[i1]).into(((ImageView) dialog.findViewById(R.id.img_coin)));
                Glide.with(source_class).load(Glob.predchampNative[i1]).into(((ImageView) dialog.findViewById(R.id.qureka_banner)));
                ((TextView) dialog.findViewById(R.id.txt_main_title)).setText("Predchamp/Play Game");
                ((TextView) dialog.findViewById(R.id.qureka_title)).setText(Glob.predchampHeader[i1]);
                ((TextView) dialog.findViewById(R.id.qureka_description)).setText(Glob.predchampDescription[i1]);
                dialog.setOnDismissListener(dialog12 -> {
                    NewApp_Preference.isfullscreenshow = false;
                    if (userEarnRewardListener != null) {
                        userEarnRewardListener.onEarnReward();
                    }
                });
                dialog.findViewById(R.id.qureka_close).setOnClickListener(v -> dialog.dismiss());
                dialog.findViewById(R.id.qureka_btn_install).setOnClickListener(v -> Glob.OpenQurekaAd(source_class));
                dialog.show();
            }
        }
    }
}
