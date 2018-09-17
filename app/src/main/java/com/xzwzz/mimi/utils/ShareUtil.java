package com.xzwzz.mimi.utils;

import android.app.Activity;
import android.content.Context;

import com.xzwzz.mimi.AppConfig;
import com.xzwzz.mimi.AppContext;
import com.xzwzz.mimi.R;

import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class ShareUtil {

    public static void share(Activity activity, PlatformActionListener listener) {
        String url = AppConfig.APP_ANDROID_SHARE + "?code=" + AppContext.getInstance().getLoginUid();
        share(activity, AppConfig.SHARE_TITLE, AppConfig.SHARE_DES, url, listener);
    }

    private static void share(final Context context, String des, String title, String shareUrl, PlatformActionListener listener) {
        final OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setText(des);
        oks.setImageUrl("http://vi4.6rooms.com/live/2016/08/15/12/1010v1471234426177866019_s.jpg");
        oks.setUrl(shareUrl);
        oks.setSiteUrl(shareUrl);
        oks.setTitleUrl(shareUrl);
        oks.setSite(context.getString(R.string.app_name));
        if (listener!=null){
            oks.setCallback(listener);
        }
        oks.show(context);
    }
}
