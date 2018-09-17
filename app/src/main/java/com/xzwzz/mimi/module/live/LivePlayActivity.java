package com.xzwzz.mimi.module.live;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.xzwzz.mimi.AppConfig;
import com.xzwzz.mimi.AppContext;
import com.xzwzz.mimi.R;
import com.xzwzz.mimi.api.http.RxUtils;
import com.xzwzz.mimi.base.BaseActivity;
import com.xzwzz.mimi.bean.ChannelDataBean;
import com.xzwzz.mimi.bean.CollectBean;
import com.xzwzz.mimi.bean.CollectBeanDao;
import com.xzwzz.mimi.glide.GlideApp;
import com.xzwzz.mimi.glide.GlideCircleTransform;
import com.xzwzz.mimi.ui.VipActivity;
import com.xzwzz.mimi.utils.CountDownUtils;
import com.xzwzz.mimi.utils.DialogHelp;
import com.xzwzz.mimi.utils.MemberUtil;
import com.xzwzz.mimi.utils.SpannableUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.custom.IRenderView;
import tv.danmaku.ijk.media.player.custom.IjkVideoView;

public class LivePlayActivity extends BaseActivity implements View.OnClickListener {
    private IjkVideoView ijkPlayer;
    boolean mBackPressed = false;
    private android.widget.TextView mTvLiveName;
    private android.widget.TextView mTvLiveTime;
    private android.widget.TextView mTvLiveNum;
    private android.widget.ImageView mIjkGuanbi;
    private android.widget.ImageView mIjkPingbi;
    private android.widget.ImageView mIvAvatar;
    private AnimationDrawable animationDrawable;
    private android.widget.ImageView loadingView;
    private android.widget.TextView tvTips;
    private LinearLayout layout;
    private ImageView imageView;
    private String textAd = AppContext.textAdBean.getZb_ad(), gifAd = AppContext.textAdBean.getZb_gif();
    private String[] textSplit;
    private String[] gifSplit;
    private ChannelDataBean.DataBean bean;
    private CollectBeanDao collectBeanDao;
    private ImageView imgCollect;
    private boolean isCollect = false;


    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected Object getIdOrView() {
        return R.layout.activity_live_play;
    }

    @Override
    protected void initView() {
        Bundle extras = getIntent().getExtras();
        bean = (ChannelDataBean.DataBean) extras.getSerializable("data");

        mTvLiveName = findViewById(R.id.tv_live_name);
        mTvLiveNum = findViewById(R.id.tv_live_num);
        mIjkGuanbi = findViewById(R.id.ijk_guanbi);
        mIjkPingbi = findViewById(R.id.ijk_pingbi);
        mIvAvatar = findViewById(R.id.iv_avatar);
        loadingView = findViewById(R.id.loading_View);
        layout = findViewById(R.id.layout);
        tvTips = findViewById(R.id.tv_tips);
        mTvLiveTime = findViewById(R.id.tv_time);
        imageView = findViewById(R.id.imageView);
        tvTips.setOnClickListener(this);

        textSplit = textAd.replaceAll("，", ",").split(",");
        tvTips.setText(textSplit[0]);

        gifSplit = gifAd.replaceAll("，", ",").split(",");
        Glide.with(this).load(gifSplit[0]).into(imageView);
        imageView.setOnClickListener(this);

        imgCollect = findViewById(R.id.img_collect);
        imgCollect.setOnClickListener(this);
        isCollect();


        Log.e("gy", "免费时间：" + AppConfig.free_time);
        if (!TextUtils.isEmpty(AppConfig.free_time) && !AppConfig.IS_MEMBER) {
            mTvLiveTime.setVisibility(View.VISIBLE);
            CountDownUtils count = new CountDownUtils(mTvLiveTime,"剩余观看时间：%s",Integer.valueOf(AppConfig.free_time));
            count.start();
            count.setCountdownListener(new CountDownUtils.CountdownListener() {
                @Override
                public void onStartCount() {

                }

                @Override
                public void onFinishCount() {
                    mTvLiveTime.setVisibility(View.GONE);
                }

                @Override
                public void onUpdateCount(int currentRemainingSeconds) {
                    mTvLiveTime.setText("剩余观看时间："+currentRemainingSeconds+"s");
                }
            });
            startDownTime();
        }


    }

    @SuppressLint("CheckResult")
    private void startDownTime() {
        Observable.timer(Integer.valueOf(AppConfig.free_time), TimeUnit.SECONDS)
                .compose(RxUtils.io_main())
                .subscribe(aLong -> isMember());
    }

    private void isMember() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void initData() {
        ijkPlayer = findViewById(R.id.ijk_player);

        if (!(bean != null && bean.getUrl() != null && !bean.getUrl().isEmpty())) {
            ToastUtils.showShort("暂无法获取主播信息，请稍后重试");
            finish();
            return;
        }
        mTvLiveName.setText(bean.getName());
        mTvLiveNum.setText(bean.getNum());
        GlideApp.with(this)
                .load(bean.getBigpic())
                .transform(new GlideCircleTransform(mContext))
                .into(mIvAvatar);

        ijkPlayer.setVideoPath(bean.getUrl());
        ijkPlayer.setAspectRatio(IRenderView.AR_MATCH_PARENT);
        animationDrawable = (AnimationDrawable) loadingView.getBackground();
        animationDrawable.setOneShot(false);
        animationDrawable.start();
        ijkPlayer.setListener(mp -> {
        }, mp -> {
            animationDrawable.stop();
            loadingView.setVisibility(View.GONE);
        }, (mp, what, extra) -> false, (mp, what, extra) -> false);
        ijkPlayer.start();
    }

    @Override
    protected void setListener() {
        mIjkGuanbi.setOnClickListener(this);
        mIjkPingbi.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        ijkPlayer.stopPlayback();
        ijkPlayer.release(true);
        ijkPlayer.destroyDrawingCache();
        ijkPlayer.stopBackgroundPlay();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ijkPlayer.pause();
        if (mBackPressed || !ijkPlayer.isBackgroundPlayEnabled()) {
            ijkPlayer.stopPlayback();
            ijkPlayer.release(true);
            ijkPlayer.destroyDrawingCache();
            ijkPlayer.stopBackgroundPlay();
        } else {
            ijkPlayer.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ijkPlayer != null)
            ijkPlayer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ijk_guanbi:
                onBackPressed();
                break;
            case R.id.imageView:
                toBrower(gifSplit[1]);
                break;
            case R.id.tv_tips:
                toBrower(textSplit[1]);
                break;
            case R.id.ijk_pingbi:

                break;

            case R.id.img_collect://搜藏
                if (!isCollect) {
                    CollectBean collectBean = new CollectBean();
                    collectBean.setImg(bean.getBigpic());
                    collectBean.setName(bean.getName());
                    collectBean.setNum(bean.getNum());
                    collectBean.setUrl(bean.getUrl());
                    collectBeanDao.insertOrReplace(collectBean);
                    ToastUtils.showShort("收藏成功");
                    imgCollect.setImageResource(R.mipmap.collect);
                    isCollect = true;
                } else {
                    collectBeanDao.deleteByKey(bean.getUid());
                    ToastUtils.showShort("取消收藏");
                    imgCollect.setImageResource(R.mipmap.collect_false);
                    isCollect = false;
                }

                break;
            default:
                break;
        }
    }

    private void toBrower(String url) {
        if (!url.contains("http")) return;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    private void isCollect() {
        collectBeanDao = AppContext.getDaoInstant().getCollectBeanDao();
        List<CollectBean> list = collectBeanDao.queryBuilder().list();
        if (list == null || list.size() == 0) return;
        for (int i = 0; i < list.size(); i++) {
            if (bean.getUid() == list.get(i).getId()) {
                imgCollect.setImageResource(R.mipmap.collect);
                isCollect = true;
            }
        }
    }
}
