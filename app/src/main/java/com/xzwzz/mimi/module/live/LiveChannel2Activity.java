package com.xzwzz.mimi.module.live;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xzwzz.mimi.AppConfig;
import com.xzwzz.mimi.AppContext;
import com.xzwzz.mimi.R;
import com.xzwzz.mimi.api.http.BaseListObserver;
import com.xzwzz.mimi.api.http.HttpResult;
import com.xzwzz.mimi.api.http.RetrofitClient;
import com.xzwzz.mimi.api.http.RxUtils;
import com.xzwzz.mimi.bean.ChannelDataBean;
import com.xzwzz.mimi.bean.MobileBean;
import com.xzwzz.mimi.bean.UserInfoBean;
import com.xzwzz.mimi.bean.ZhuBoBean;
import com.xzwzz.mimi.module.AbsModuleActivity;
import com.xzwzz.mimi.module.live.adapter.LiveChannelAdapter;
import com.xzwzz.mimi.ui.PayActivity;
import com.xzwzz.mimi.ui.VipActivity;
import com.xzwzz.mimi.ui.login.LoginActivity;
import com.xzwzz.mimi.utils.DialogHelp;
import com.xzwzz.mimi.utils.LoginUtils;
import com.xzwzz.mimi.utils.MemberUtil;
import com.xzwzz.mimi.utils.PayUtils;
import com.xzwzz.mimi.utils.SharePrefUtil;
import com.xzwzz.mimi.utils.ShareUtil;
import com.xzwzz.mimi.utils.StatusBarUtil;
import com.xzwzz.mimi.widget.ViewStatusManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import io.reactivex.functions.Consumer;

public class LiveChannel2Activity extends AbsModuleActivity implements PlatformActionListener {
    private String id, logo;
    private ProgressDialog mProgressDialog;
    private View headView;
    private ImageView imgLogo;
    private TextView tvNum, tvName;
    private String title1;
    private Random random;

    @Override
    protected void initView() {
        super.initView();
        Bundle extras = getIntent().getExtras();
        title1 = extras.getString("plamform");
        logo = extras.getString("logo");
        setToolbar(title1);
        ImageView imgBack = findViewById(R.id.img_back);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(v -> finish());
        id = extras.getString("id");
        mViewStatusManager.setStatus(ViewStatusManager.ViewStatus.loading);

        headView = View.inflate(this, R.layout.view_channel_head, null);
        imgLogo = headView.findViewById(R.id.img_channel_logo);
        tvNum = headView.findViewById(R.id.tv_num);
        tvName = headView.findViewById(R.id.tv_name);
        tvName.setText(title1);
        Glide.with(this).load(logo).into(imgLogo);

        mAdapter.addHeaderView(headView);

    }

    @Override
    protected void setRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildPosition(view);
                if (position == 0) return;
                int offest = SizeUtils.dp2px(5f);
                if (position % 2 == 0) {
                    outRect.set(offest, offest, offest / 2, 0);
                } else if (position % 2 == 1) {
                    outRect.set(offest / 2, offest, offest, 0);
                }
            }
        });
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new LiveChannelAdapter();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadData() {
        random = new Random();
        String url = SharePrefUtil.getString("dataurl", "");
        if (TextUtils.isEmpty(url)) return;
        RetrofitClient.getInstance().createApi().getChannelData2(url + id)
                .compose(RxUtils.io_main())
                .subscribe(zhuBoBean -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (zhuBoBean.getZhubo() != null && zhuBoBean.getZhubo().size() > 0) {

                        List<ChannelDataBean.DataBean> list = new ArrayList<>();

                        for (int i = 0; i < zhuBoBean.getZhubo().size(); i++) {
                            ChannelDataBean.DataBean bean = new ChannelDataBean.DataBean();
                            ZhuBoBean.ZhuboBean zhuboBean = zhuBoBean.getZhubo().get(i);
                            bean.setBigpic(zhuboBean.getImg());
                            bean.setName(zhuboBean.getTitle());
                            bean.setUrl(zhuboBean.getAddress());
                            bean.setNum(random.nextInt(1000) + 100 + "");

                            list.add(bean);
                        }
                        mAdapter.setNewData(list);
                        mViewStatusManager.setStatus(ViewStatusManager.ViewStatus.success);
                        tvNum.setText(list.size() + "");
                    } else {
                        mViewStatusManager.setStatus(ViewStatusManager.ViewStatus.empty);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        ChannelDataBean.DataBean item = (ChannelDataBean.DataBean) adapter.getItem(position);
        goRoomV1(item);
    }

    private void goRoomV1(ChannelDataBean.DataBean item) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("data", item);
//        ActivityUtils.startActivity(bundle, LivePlayActivity.class);
        Intent intent = new Intent(this,LivePlayActivity.class);
        intent.putExtra("data",item);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1){
//                PayUtils.payDialog(this, R.mipmap.zb_pay_bg, "直播区", "", 1, AppContext.zbChargeList);
                dialog();
            }
        }
    }

    private void dialog() {
        Dialog dialog = new Dialog(this, R.style.wx_dialog);
        View view = View.inflate(this, R.layout.dialog_commom, null);
        TextView tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setText("账号到期，分享可获得免费会员时间");
        TextView tvClose = view.findViewById(R.id.tv_close);
        tvClose.setText("立即分享");
        tvClose.setOnClickListener(v -> {
//            ActivityUtils.startActivity(VipActivity.class);
            ShareUtil.share(LiveChannel2Activity.this, this);
            dialog.dismiss();
        });
        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isMember();
    }

    private void isMember() {
        RetrofitClient.getInstance().createApi().getBaseUserInfo("User.getBaseInfo", AppContext.getInstance().getToken(), AppContext.getInstance().getLoginUid())
                .compose(RxUtils.io_main())
                .subscribe(new BaseListObserver<UserInfoBean>() {
                    @Override
                    protected void onHandleSuccess(List<UserInfoBean> list) {
                        if (list.size() > 0) {
                            UserInfoBean bean = list.get(0);
                            AppConfig.IS_MEMBER = (bean.is_member == 1);
                        }
                    }
                });
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ToastUtils.showShort("分享成功");
        shareComplete();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtils.showShort("分享失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtils.showShort("取消分享");
    }

    @SuppressLint("CheckResult")
    private void shareComplete(){
        RetrofitClient.getInstance().createApi().share("Home.sharenum", AppContext.getInstance().getLoginUid())
                .compose(RxUtils.io_main())
                .subscribe(new Consumer<HttpResult>() {
                    @Override
                    public void accept(HttpResult httpResult) throws Exception {
                        isMember();
                    }
                });
    }

}
