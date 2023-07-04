package com.example.realpianoadsmodule.Native;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realpianoadsmodule.R;
import com.facebook.shimmer.ShimmerFrameLayout;

public class StaticNativeAds {
    Context context;

    public StaticNativeAds(Context context) {
        this.context = context;
    }

    public void NativeAd_(ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_staticnativead, null);
        viewGroup.removeAllViews();
        ShimmerFrameLayout shimmerContainer = view.findViewById(R.id.shimmer_container);
        shimmerContainer.startShimmer();
        viewGroup.addView(view);
    }

    public void Native_BannerAd(ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.staticnative_bannerad, null);
        viewGroup.removeAllViews();
        ShimmerFrameLayout shimmerContainer = view.findViewById(R.id.shimmer_container);
        shimmerContainer.startShimmer();
        viewGroup.addView(view);
    }

    public void AdaptiveBanner(ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.static_adaptivead, null);
        viewGroup.removeAllViews();
        ShimmerFrameLayout shimmerContainer = view.findViewById(R.id.shimmer_container);
        shimmerContainer.startShimmer();
        viewGroup.addView(view);
    }
}
