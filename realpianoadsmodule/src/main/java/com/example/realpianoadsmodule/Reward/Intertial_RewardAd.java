package com.example.realpianoadsmodule.Reward;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.realpianoadsmodule.Other.Glob;
import com.example.realpianoadsmodule.Other.NewApp_Preference;
import com.example.realpianoadsmodule.R;
import com.facebook.ads.Ad;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Intertial_RewardAd {

    public RewardedAd ADrewardedad;

    public void Dialogfor_Reward_Ad(Activity activity, UserEarnRewardListener userEarnRewardListener) {
        Dialog dialog = new Dialog(activity, R.style.dialogtransparent_);
        dialog.setContentView(R.layout.rewardad_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.findViewById(R.id.cv_no).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.cv_yes).setOnClickListener(v -> {
            (dialog.findViewById(R.id.pb_loading)).setVisibility(View.VISIBLE);
            Show_RewardAd(activity, dialog, userEarnRewardListener);
        });
        dialog.show();

    }

    private void Show_RewardAd(Activity source_class, Dialog dialog, UserEarnRewardListener userEarnRewardListener) {
        if (new NewApp_Preference(source_class).getAdStatus().equalsIgnoreCase("on")) {
            if (new NewApp_Preference(source_class).getAdFlag().equalsIgnoreCase("Normal")) {
                ShowReward_AdmobFailFb(source_class, dialog, userEarnRewardListener);
            } else if (new NewApp_Preference(source_class).getAdFlag().equalsIgnoreCase("fb")) {
                ShowReward_FbFailAdmob(source_class, dialog, userEarnRewardListener);
            } else if (new NewApp_Preference(source_class).getAdFlag().equalsIgnoreCase("multiple")) {
                if (Glob.Reward_Counter == 2) {
                    Glob.Reward_Counter = 1;
                    ShowReward_AdmobFailFb(source_class, dialog, userEarnRewardListener);
                } else {
                    Glob.Reward_Counter++;
                    ShowReward_FbFailAdmob(source_class, dialog, userEarnRewardListener);
                }
            } else {
                if (Glob.Reward_Counter == 2) {
                    Glob.Reward_Counter = 1;
                    ShowReward_AdmobFailFb(source_class, dialog, userEarnRewardListener);
                } else {
                    Glob.Reward_Counter++;
                    ShowReward_FbFailAdmob(source_class, dialog, userEarnRewardListener);
                }
            }
        }
    }

    public void ShowReward_AdmobFailFb(Activity source_class, Dialog dialog, UserEarnRewardListener userEarnRewardListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(source_class, newApp_preference.getAdmobRewardedId(), adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d("TAG", loadAdError.getMessage());

                final RewardedVideoAd rewardedVideoAd = new RewardedVideoAd(source_class, newApp_preference.getFbRewardId());
                RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

                    @Override
                    public void onError(Ad ad, com.facebook.ads.AdError adError) {
                        dialog.dismiss();
                        Reward_QurekaPredchamp.Show_QurekaFailPredchampAd(source_class, userEarnRewardListener);
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        rewardedVideoAd.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                    }

                    @Override
                    public void onRewardedVideoCompleted() {
                        Toast.makeText(source_class, "Ad completed, now give reward to user", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRewardedVideoClosed() {
                    }
                };
                rewardedVideoAd.loadAd(
                        rewardedVideoAd.buildLoadAdConfig()
                                .withAdListener(rewardedVideoAdListener)
                                .build());
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                ADrewardedad = rewardedAd;
                ADrewardedad.show(source_class, rewardItem -> {
                    //Your Code Goes Here
                    userEarnRewardListener.onEarnReward();
                });
                ADrewardedad.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        dialog.dismiss();

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        Log.d("TAG", "Ad failed to show.");
                        ADrewardedad = null;
                        dialog.dismiss();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d("TAG", "Ad was dismissed.");
                        ADrewardedad = null;

                    }
                });
            }
        });
    }

    public void ShowReward_FbFailAdmob(Activity source_class, Dialog dialog, UserEarnRewardListener userEarnRewardListener) {
        NewApp_Preference newApp_preference = new NewApp_Preference(source_class);
        AdRequest adRequest = new AdRequest.Builder().build();
        final RewardedVideoAd rewardedVideoAd = new RewardedVideoAd(source_class, newApp_preference.getFbRewardId());
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                RewardedAd.load(source_class, newApp_preference.getAdmobRewardedId(), adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d("TAG", loadAdError.getMessage());
                        dialog.dismiss();
                        Reward_QurekaPredchamp.Show_QurekaFailPredchampAd(source_class, userEarnRewardListener);
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        ADrewardedad = rewardedAd;
                        ADrewardedad.show(source_class, rewardItem -> {
                            //Your Code Goes Here
                            userEarnRewardListener.onEarnReward();
                        });
                        ADrewardedad.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                Log.d("TAG", "Ad failed to show.");
                                ADrewardedad = null;
                                dialog.dismiss();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d("TAG", "Ad was dismissed.");
                                ADrewardedad = null;

                            }
                        });
                    }
                });
            }

            @Override
            public void onAdLoaded(Ad ad) {
                rewardedVideoAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }

            @Override
            public void onRewardedVideoCompleted() {
                Toast.makeText(source_class, "Ad completed, now give reward to user", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoClosed() {
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());

    }

    public interface UserEarnRewardListener {
        void onEarnReward();
    }
}
