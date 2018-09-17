package com.xzwzz.mimi.module.video;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xzwzz.mimi.AppContext;
import com.xzwzz.mimi.R;
import com.xzwzz.mimi.api.http.HttpArray;
import com.xzwzz.mimi.api.http.HttpResult;
import com.xzwzz.mimi.api.http.RetrofitClient;
import com.xzwzz.mimi.api.http.RxUtils;
import com.xzwzz.mimi.base.BaseActivity;
import com.xzwzz.mimi.bean.VideoListBean;
import com.xzwzz.mimi.widget.XzwzzPlayer;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.functions.Consumer;

public class VideoPlayActivity extends BaseActivity {

    private String type, title, url;
    private String[] textSplit;
    private String[] gifSplit;
    private TextView tips;
    private ImageView gif;
    private String id;

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected Object getIdOrView() {
        return R.layout.activity_video_play;
    }


    @Override
    protected void initView() {
        super.initView();
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        title = bundle.getString("title");
        url = bundle.getString("url");
        id = bundle.getString("id");
        VideoListBean bean = (VideoListBean) bundle.getSerializable("data");
        jzVideoPlayerStandard = findViewById(R.id.videoplayer);
        gif = findViewById(R.id.gif);
        tips = findViewById(R.id.tips);
        jzVideoPlayerStandard.fullscreenButton.setVisibility(View.INVISIBLE);
        jzVideoPlayerStandard.setUp(url, JZVideoPlayerStandard.NORMAL_ORIENTATION, title);
        jzVideoPlayerStandard.thumbImageView.setVisibility(View.GONE);
        jzVideoPlayerStandard.startVideo();

        initAd();
        jzVideoPlayerStandard.backButton.setOnClickListener(v -> finish());
        jzVideoPlayerStandard.titleTextView.setOnClickListener(v -> finish());
        tips.setOnClickListener(v -> toBrower(textSplit[1]));
        gif.setOnClickListener(v -> toBrower(gifSplit[1]));

    }

    private void initAd() {
        try {
            if (type.equals("av")) {
                textSplit = AppContext.textAdBean.getAv_ad().replaceAll("，", ",").split(",");
                tips.setText(textSplit[0]);

                gifSplit = AppContext.textAdBean.getAv_gif().replaceAll("，", ",").split(",");
                Glide.with(this).load(gifSplit[0]).into(gif);
            } else {
                textSplit = AppContext.textAdBean.getCoin_ad().replaceAll("，", ",").split(",");
                tips.setText(textSplit[0]);

                gifSplit = AppContext.textAdBean.getCoin_gif().replaceAll("，", ",").split(",");
                Glide.with(this).load(gifSplit[0]).into(gif);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        addViewNum();
    }

    @SuppressLint("CheckResult")
    private void addViewNum() {
        RetrofitClient.getInstance().createApi().addViewNum("Home.addvideonum", id,type)
                .compose(RxUtils.io_main())
                .subscribe(httpResult -> {
                });
    }

    private XzwzzPlayer jzVideoPlayerStandard;

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        if (jzVideoPlayerStandard.currentScreen == JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL) {
            super.onBackPressed();
            return;
        }
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    private void toBrower(String url) {
        if (!url.contains("http")) return;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }
}
