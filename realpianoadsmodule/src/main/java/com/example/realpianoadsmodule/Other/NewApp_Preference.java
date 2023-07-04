package com.example.realpianoadsmodule.Other;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewApp_Preference {

    Context contexts;
    public Editor editor;
    public String USER_PREFS = "PREFS";
    public SharedPreferences sharedPreferences;
    public static boolean isfullscreenshow = false;

    String AdStatus = "AdStatus";
    String AdStyle = "AdStyle";
    String AdStyleNative = "AdStyleNative";
    String AdStyleBanner = "AdStyleBanner";
    String AdFlag = "AdFlag";
    String AdTimeInterval = "AdTimeInterval";

    String RewardCounter = "RewardCounter";
    String QurekaFlag = "QurekaFlag";
    String QurekaLink = "QurekaLink";

    String ClickCount = "ClickCount";
    String OrganicClickCount = "OrganicClickCount";
    String ClickFlag = "ClickFlag";
    String NativeCount = "NativeCount";

    String BaseUrl = "BaseUrl";
    String Account = "Account";
    String PrivacyPolicy = "PrivacyPolicy";

    //Whatsapp Saver
    String WSaver = "WSaver";

    //Direct Link Url
    String UrlFor_ads = "UrlFor_ads";
    String DirectLlink = "DirectLlink";

    String BackFlag = "BackFlag";
    String BackClick = "BackClick";
    String BackClickAdStyle = "BackClickAdStyle";

    String NativeFlag = "NativeFlag";
    String BannerFlag = "BannerFlag";
    String FullFlag = "FullFlag";
    String OpenFlag = "OpenFlag";

    String NativeTypeList = "NativeTypeList";
    String NativeTypeOther = "NativeTypeOther";

    String Gclid = "Gclid";
    String GclidValue = "GclidValue";

    String SplashFlag = "SplashFlag";
    String SplashOpenAppId = "SplashOpenAppId";

    String AdbtColor = "AdbtColor";
    String TextColor = "TextColor";
    String BackColor = "BackColor";

    String Medium = "Medium";

    String Page = "Page";

    String ShowInstall = "ShowInstall";
    String ReferrerUrl = "ReferrerUrl";

    String Screen = "Screen";

    String FbRewardId = "FbRewardId";

    String SplashInterId = "SplashInterId";
    String AdmobInterId1 = "AdmobInterId1";
    String AdmobInterId2 = "AdmobInterId2";
    String AdmobInterId3 = "AdmobInterId3";

    String AdmobNativeId1 = "AdmobNativeId1";
    String AdmobNativeId2 = "AdmobNativeId2";
    String AdmobNativeId3 = "AdmobNativeId3";

    String AdmobBannerId1 = "AdmobBannerId1";
    String AdmobBannerId2 = "AdmobBannerId2";
    String AdmobBannerId3 = "AdmobBannerId3";

    String AdmobOpenAppId1 = "AdmobOpenAppId1";
    String AdmobOpenAppId2 = "AdmobOpenAppId2";
    String AdmobOpenAppId3 = "AdmobOpenAppId3";

    String AdmobRewardedId = "AdmobRewardedId";

    String FacebookInterstitial = "FacebookInterstitial";
    String FacebookNative = "FacebookNative";
    String FacebookBanner = "FacebookBanner";


    public NewApp_Preference(Context context) {
        this.sharedPreferences = context.getSharedPreferences(this.USER_PREFS, 0);
        this.editor = this.sharedPreferences.edit();
        contexts = context;
    }

    public boolean isConnected() {
        boolean connected;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) contexts.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return false;
    }

    public void AllDataFromJSON_StoreinPreference(String response) throws JSONException {
        JSONObject object = new JSONObject(response);
        JSONArray jsonArray = new JSONArray(new JSONObject(object.getString("json_data")).getString("Data"));
        setAdFlag(jsonArray.getJSONObject(0).optString("Adflag"));
        setAdTimeInterval(jsonArray.getJSONObject(0).optString("Adtime"));
        setAdStyle(jsonArray.getJSONObject(0).optString("Adstyle"));
        setAdStatus(jsonArray.getJSONObject(0).optString("Adstatus"));
        setAccount(jsonArray.getJSONObject(0).optString("account"));
        setPrivacyPolicy(jsonArray.getJSONObject(0).optString("pp"));
        setQurekaFlag(jsonArray.getJSONObject(0).optString("qureka"));
        setQurekaLink(jsonArray.getJSONObject(0).optString("qureka-link"));
        setSplashInterId(jsonArray.getJSONObject(0).optString("admob-splash"));
        setAdmobInterId1(jsonArray.getJSONObject(0).optString("admob-full1"));
        setAdmobInterId2(jsonArray.getJSONObject(0).optString("admob-full2"));
        setAdmobInterId3(jsonArray.getJSONObject(0).optString("admob-full3"));
        setAdmobNativeId1(jsonArray.getJSONObject(0).optString("admob-native1"));
        setAdmobNativeId2(jsonArray.getJSONObject(0).optString("admob-native2"));
        setAdmobNativeId3(jsonArray.getJSONObject(0).optString("admob-native3"));
        setAdmobBannerId1(jsonArray.getJSONObject(0).optString("admob-banner1"));
        setAdmobBannerId2(jsonArray.getJSONObject(0).optString("admob-banner2"));
        setAdmobBannerId3(jsonArray.getJSONObject(0).optString("admob-banner3"));
        setAdmobOpenAppId1(jsonArray.getJSONObject(0).optString("admob-open1"));
        setAdmobOpenAppId2(jsonArray.getJSONObject(0).optString("admob-open2"));
        setAdmobOpenAppId3(jsonArray.getJSONObject(0).optString("admob-open3"));
        setClickFlag(jsonArray.getJSONObject(0).optString("clickflag"));
        setScreen(jsonArray.getJSONObject(0).optString("screen"));
        setClickCount(jsonArray.getJSONObject(0).optString("click"));
        setOrganicClickCount(jsonArray.getJSONObject(0).optString("orgclick", "4"));
        setGclid(jsonArray.getJSONObject(0).optString("gclid", "off"));
        setGclidValue(jsonArray.getJSONObject(0).optString("gclidValue", "gclid"));
        setNativeFlag(jsonArray.getJSONObject(0).optString("native", "on"));
        setBannerFlag(jsonArray.getJSONObject(0).optString("banner", "on"));
        setOpenFlag(jsonArray.getJSONObject(0).optString("open", "on"));
        setFullFlag(jsonArray.getJSONObject(0).optString("full", "on"));
        setSplashOpenAppId(jsonArray.getJSONObject(0).optString("admob-splash-open"));
        set_splash_flag(jsonArray.getJSONObject(0).optString("splash"));
        setFacebookInterstitial(jsonArray.getJSONObject(0).optString("fb-full"));
        setFacebookNative(jsonArray.getJSONObject(0).optString("fb-native"));
        setFacebookBanner(jsonArray.getJSONObject(0).optString("fb-banner"));
        setAdbtcolor(jsonArray.getJSONObject(0).optString("adbtclr"));
        setPage(jsonArray.getJSONObject(0).optString("page"));
        setMedium(jsonArray.getJSONObject(0).optString("medium"));
        setBackFlag(jsonArray.getJSONObject(0).optString("backflag"));
        setBackClick(jsonArray.getJSONObject(0).optString("backclick"));
        setNativeTypeList(jsonArray.getJSONObject(0).optString("native_type_list"));
        setNativeTypeOther(jsonArray.getJSONObject(0).optString("native_type_other"));
        setBackcolor(jsonArray.getJSONObject(0).optString("backcolor", "ffffff"));
        setTextColor(jsonArray.getJSONObject(0).optString("textcolor", "000000"));
        setShowInstall(jsonArray.getJSONObject(0).optString("showinstall", "off"));
        setWSaver(jsonArray.getJSONObject(0).optString("wsaver", "on"));
        setNativeCount(jsonArray.getJSONObject(0).optString("nativecount", "2"));
        setBackClickAdStyle(jsonArray.getJSONObject(0).optString("backclickadstyle", "admob"));
        setAdStyleNative(jsonArray.getJSONObject(0).optString("AdstyleNative", "admob"));
        setAdStyleBanner(jsonArray.getJSONObject(0).optString("AdstyleBanner", "admob"));
        setFbRewardId(jsonArray.getJSONObject(0).optString("fb-reward"));
        setAdmobRewardedId(jsonArray.getJSONObject(0).optString("admob-reward"));
        setDirectLlink(jsonArray.getJSONObject(0).optString("direct_link", "off"));
        setUrlFor_ads(jsonArray.getJSONObject(0).optString("url_for_ads"));
    }

    public String getUrlFor_ads() {
        return this.sharedPreferences.getString(this.UrlFor_ads, "");
    }

    public void setUrlFor_ads(String url_for_ads) {
        this.editor.putString(this.UrlFor_ads, url_for_ads).commit();
    }

    public String getDirectLlink() {
        return this.sharedPreferences.getString(this.DirectLlink, "");
    }

    public void setDirectLlink(String direct_link) {
        this.editor.putString(this.DirectLlink, direct_link).commit();
    }

    public String getMedium() {
        return this.sharedPreferences.getString(this.Medium, "");
    }

    public void setMedium(String medium) {
        this.editor.putString(this.Medium, medium).commit();
    }

    public String getFbRewardId() {
        return this.sharedPreferences.getString(this.FbRewardId, "");
    }

    public void setFbRewardId(String fbrewardid) {
        this.editor.putString(this.FbRewardId, fbrewardid).commit();
    }

    public String getBackClickAdStyle() {
        return this.sharedPreferences.getString(this.BackClickAdStyle, "");
    }

    public void setBackClickAdStyle(String backclickadstyle) {
        this.editor.putString(this.BackClickAdStyle, backclickadstyle).commit();
    }

    public String getNativecount() {
        return this.sharedPreferences.getString(this.NativeCount, "");
    }

    public void setNativeCount(String nativecount) {
        this.editor.putString(this.NativeCount, nativecount).commit();
    }

    public String getPage() {
        return this.sharedPreferences.getString(this.Page, "");
    }

    public void setPage(String page) {
        this.editor.putString(this.Page, page).commit();
    }

    public boolean getCheckinstallbool() {
        return this.sharedPreferences.getBoolean("checkinstallbool", false);
    }

    public void setCheckinstallbool(boolean checkinstallbool) {
        this.editor.putBoolean("checkinstallbool", checkinstallbool).commit();
    }

    public String getGclid() {
        return this.sharedPreferences.getString(this.Gclid, "");
    }

    public void setGclid(String gclid) {
        this.editor.putString(this.Gclid, gclid).commit();
    }

    public String getGclidValue() {
        return this.sharedPreferences.getString(this.GclidValue, "");
    }

    public void setGclidValue(String gclid) {
        this.editor.putString(this.GclidValue, gclid).commit();
    }

    public String getShowInstall() {
        return this.sharedPreferences.getString(this.ShowInstall, "");
    }

    public void setShowInstall(String showinstall) {
        this.editor.putString(this.ShowInstall, showinstall).commit();
    }

    public String getReferrerUrl() {
        return this.sharedPreferences.getString(this.ReferrerUrl, "");
    }

    public void setReferrerUrl(String referrerUrl) {
        this.editor.putString(this.ReferrerUrl, referrerUrl).commit();
    }

    public String get_splash_flag() {
        return this.sharedPreferences.getString(this.SplashFlag, "");
    }

    public void set_splash_flag(String splash_flag) {
        this.editor.putString(this.SplashFlag, splash_flag).commit();
    }

    public String getSplashOpenAppId() {
        return this.sharedPreferences.getString(this.SplashOpenAppId, "");
    }

    public void setSplashOpenAppId(String str) {
        this.editor.putString(this.SplashOpenAppId, str).commit();
    }


    public String getWsaver() {
        return this.sharedPreferences.getString(this.WSaver, "");
    }

    public void setWSaver(String wsaver) {
        this.editor.putString(this.WSaver, wsaver).commit();
    }

    public String getFullFlag() {
        return this.sharedPreferences.getString(this.FullFlag, "");
    }

    public void setFullFlag(String fullFlag) {
        this.editor.putString(this.FullFlag, fullFlag).commit();
    }

    public String getOpenFlag() {
        return this.sharedPreferences.getString(this.OpenFlag, "");
    }

    public void setOpenFlag(String openFlag) {
        this.editor.putString(this.OpenFlag, openFlag).commit();
    }

    public String getNativeFlag() {
        return this.sharedPreferences.getString(this.NativeFlag, "");
    }

    public void setNativeFlag(String nativeFlag) {
        this.editor.putString(this.NativeFlag, nativeFlag).commit();
    }

    public String getBannerFlag() {
        return this.sharedPreferences.getString(this.BannerFlag, "");
    }

    public void setBannerFlag(String bannerFlag) {
        this.editor.putString(this.BannerFlag, bannerFlag).commit();
    }

    public String getOrganicClickCount() {
        return this.sharedPreferences.getString(this.OrganicClickCount, "");
    }

    public void setOrganicClickCount(String Organic_Click_Count) {
        this.editor.putString(this.OrganicClickCount, Organic_Click_Count).commit();
    }

    public String getBackFlag() {
        return this.sharedPreferences.getString(this.BackFlag, "");
    }

    public void setBackFlag(String backflag) {
        this.editor.putString(this.BackFlag, backflag).commit();
    }

    public String getBackClick() {
        return this.sharedPreferences.getString(this.BackClick, "");
    }

    public void setBackClick(String backclick) {
        this.editor.putString(this.BackClick, backclick).commit();
    }

    public String getScreen() {
        return this.sharedPreferences.getString(this.Screen, "");
    }

    public void setScreen(String screen) {
        this.editor.putString(this.Screen, screen).commit();
    }

    public void setNativeTypeOther(String type) {
        this.editor.putString(this.NativeTypeOther, type).commit();
    }

    public String getNativeTypeOther() {
        return this.sharedPreferences.getString(this.NativeTypeOther, "");
    }

    public String getNativeTypeList() {
        return this.sharedPreferences.getString(this.NativeTypeList, "");
    }

    public void setNativeTypeList(String nativeTypeList) {
        this.editor.putString(this.NativeTypeList, nativeTypeList).commit();
    }

    public String getBackColor() {
        return this.sharedPreferences.getString(this.BackColor, "");
    }

    public void setBackcolor(String str) {
        this.editor.putString(this.BackColor, str).commit();
    }

    public String getTextColor() {
        return this.sharedPreferences.getString(this.TextColor, "");
    }

    public void setTextColor(String str) {
        this.editor.putString(this.TextColor, str).commit();
    }

    public String getBaseUrl() {
        return this.sharedPreferences.getString(this.BaseUrl, "");
    }

    public void setBaseUrl(String str) {
        this.editor.putString(this.BaseUrl, str).commit();
    }

    public String getClickFlag() {
        return this.sharedPreferences.getString(this.ClickFlag, "");
    }

    public void setClickFlag(String clickFlag) {
        this.editor.putString(this.ClickFlag, clickFlag).commit();
    }

    public String getClickCount() {
        return this.sharedPreferences.getString(this.ClickCount, "");
    }

    public void setClickCount(String click_count) {
        this.editor.putString(this.ClickCount, click_count).commit();
    }

    public String getAdmobRewardedId() {
        return this.sharedPreferences.getString(this.AdmobRewardedId, "");
    }

    public void setAdmobRewardedId(String admob_rewarded_id) {
        this.editor.putString(this.AdmobRewardedId, admob_rewarded_id).commit();
    }

    public String getAdStatus() {
        return this.sharedPreferences.getString(this.AdStatus, "");
    }

    public void setAdStatus(String str) {
        this.editor.putString(this.AdStatus, str).commit();
    }

    public String getAdStyle() {
        return this.sharedPreferences.getString(this.AdStyle, "");
    }

    public void setAdStyle(String adstyle) {
        this.editor.putString(this.AdStyle, adstyle).commit();
    }

    public String getAdstyleBanner() {
        return this.sharedPreferences.getString(this.AdStyleBanner, "");
    }

    public void setAdStyleBanner(String AdstyleBanner) {
        this.editor.putString(this.AdStyleBanner, AdstyleBanner).commit();
    }

    public String getAdStyleNative() {
        return this.sharedPreferences.getString(this.AdStyleNative, "");
    }

    public void setAdStyleNative(String adStyleNative) {
        this.editor.putString(this.AdStyleNative, adStyleNative).commit();
    }

    public String getQurekaFlag() {
        return this.sharedPreferences.getString(this.QurekaFlag, "");
    }

    public void setQurekaFlag(String str) {
        this.editor.putString(this.QurekaFlag, str).commit();
    }

    public String getAdFlag() {
        return this.sharedPreferences.getString(this.AdFlag, "");
    }

    public void setAdFlag(String adFlag) {
        this.editor.putString(this.AdFlag, adFlag).commit();
    }

    public String getRewardCounter() {
        return this.sharedPreferences.getString(this.RewardCounter, "");
    }

    public void setRewardCounter(String reward_counter) {
        this.editor.putString(this.RewardCounter, reward_counter).commit();
    }

    public String getAdTimeInterval() {
        return this.sharedPreferences.getString(this.AdTimeInterval, "");
    }

    public void setAdTimeInterval(String ad_time_interval) {
        this.editor.putString(this.AdTimeInterval, ad_time_interval).commit();
    }

    public String getQurekaLink() {
        return this.sharedPreferences.getString(this.QurekaLink, "");
    }

    public void setQurekaLink(String qurekaLink) {
        this.editor.putString(this.QurekaLink, qurekaLink).commit();
    }

    public String getAccount() {
        return this.sharedPreferences.getString(this.Account, "");
    }

    public void setAccount(String account) {
        this.editor.putString(this.Account, account).commit();
    }

    public String getPrivacyPolicy() {
        return this.sharedPreferences.getString(this.PrivacyPolicy, "");
    }

    public void setPrivacyPolicy(String privacy_policy) {
        this.editor.putString(this.PrivacyPolicy, privacy_policy).commit();
    }

    public String getSplashInterId() {
        return this.sharedPreferences.getString(this.SplashInterId, "");
    }

    public void setSplashInterId(String splashInterId) {
        this.editor.putString(this.SplashInterId, splashInterId).commit();
    }

    public String getAdmobInterId1() {
        return this.sharedPreferences.getString(this.AdmobInterId1, "");
    }

    public void setAdmobInterId1(String admobInterId1) {
        this.editor.putString(this.AdmobInterId1, admobInterId1).commit();
    }

    public String getAdmobInterId2() {
        return this.sharedPreferences.getString(this.AdmobInterId2, "");
    }

    public void setAdmobInterId2(String admobInterId2) {
        this.editor.putString(this.AdmobInterId2, admobInterId2).commit();
    }

    public String getAdmobInterId3() {
        return this.sharedPreferences.getString(this.AdmobInterId3, "");
    }

    public void setAdmobInterId3(String admobInterId3) {
        this.editor.putString(this.AdmobInterId3, admobInterId3).commit();
    }

    public String getAdmobNativeId1() {
        return this.sharedPreferences.getString(this.AdmobNativeId1, "");
    }

    public void setAdmobNativeId1(String admob_native_id1) {
        this.editor.putString(this.AdmobNativeId1, admob_native_id1).commit();
    }

    public String getAdmobNativeId2() {
        return this.sharedPreferences.getString(this.AdmobNativeId2, "");
    }

    public void setAdmobNativeId2(String admobNativeId2) {
        this.editor.putString(this.AdmobNativeId2, admobNativeId2).commit();
    }

    public String getAdmobNativeId3() {
        return this.sharedPreferences.getString(this.AdmobNativeId3, "");
    }

    public void setAdmobNativeId3(String admobNativeId3) {
        this.editor.putString(this.AdmobNativeId3, admobNativeId3).commit();
    }

    public String getAdmobBannerId1() {
        return this.sharedPreferences.getString(this.AdmobBannerId1, "");
    }

    public void setAdmobBannerId1(String admobBannerId1) {
        this.editor.putString(this.AdmobBannerId1, admobBannerId1).commit();
    }

    public String getAdmobBannerId2() {
        return this.sharedPreferences.getString(this.AdmobBannerId2, "");
    }

    public void setAdmobBannerId2(String admobBannerId2) {
        this.editor.putString(this.AdmobBannerId2, admobBannerId2).commit();
    }

    public String getAdmobBannerId3() {
        return this.sharedPreferences.getString(this.AdmobBannerId3, "");
    }

    public void setAdmobBannerId3(String admobBannerId3) {
        this.editor.putString(this.AdmobBannerId3, admobBannerId3).commit();
    }

    public String getAdmobOpenAppId1() {
        return this.sharedPreferences.getString(this.AdmobOpenAppId1, "");
    }

    public void setAdmobOpenAppId1(String AdmobOpenAppId1) {
        this.editor.putString(this.AdmobOpenAppId1, AdmobOpenAppId1).commit();
    }

    public String getAdmobOpenAppId2() {
        return this.sharedPreferences.getString(this.AdmobOpenAppId2, "");
    }

    public void setAdmobOpenAppId2(String AdmobOpenAppId2) {
        this.editor.putString(this.AdmobOpenAppId2, AdmobOpenAppId2).commit();
    }

    public String getAdmobOpenAppId3() {
        return this.sharedPreferences.getString(this.AdmobOpenAppId3, "");
    }

    public void setAdmobOpenAppId3(String AdmobOpenAppId3) {
        this.editor.putString(this.AdmobOpenAppId3, AdmobOpenAppId3).commit();
    }


    public String getFacebookInterstitial() {
        return this.sharedPreferences.getString(this.FacebookInterstitial, "");
    }

    public void setFacebookInterstitial(String FacebookInterstitial) {
        this.editor.putString(this.FacebookInterstitial, FacebookInterstitial).commit();
    }

    public String getFacebookNative() {
        return this.sharedPreferences.getString(this.FacebookNative, "");
    }

    public void setFacebookNative(String FacebookNative) {
        this.editor.putString(this.FacebookNative, FacebookNative).commit();
    }

    public String getFacebookBanner() {
        return this.sharedPreferences.getString(this.FacebookBanner, "");
    }

    public void setFacebookBanner(String FacebookBanner) {
        this.editor.putString(this.FacebookBanner, FacebookBanner).commit();
    }

    public String getAdbtColor() {
        return this.sharedPreferences.getString(this.AdbtColor, "");
    }

    public void setAdbtcolor(String AdbtColor) {
        this.editor.putString(this.AdbtColor, AdbtColor).commit();
    }
}