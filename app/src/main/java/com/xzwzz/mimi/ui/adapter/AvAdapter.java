package com.xzwzz.mimi.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xzwzz.mimi.R;
import com.xzwzz.mimi.bean.AvVideoListBean;
import com.xzwzz.mimi.bean.VideoListBean;
import com.xzwzz.mimi.utils.GlideUtils;

import java.util.List;

/**
 * Created by aaaa on 2018/8/5.
 */

public class AvAdapter extends BaseQuickAdapter<AvVideoListBean,BaseViewHolder>{

    public AvAdapter(int layoutResId, @Nullable List<AvVideoListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AvVideoListBean item) {
        ImageView imageView = helper.getView(R.id.img_av);
        GlideUtils.glide(mContext,item.getVideo_img(),imageView);
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_time,item.getUptime());
    }
}
