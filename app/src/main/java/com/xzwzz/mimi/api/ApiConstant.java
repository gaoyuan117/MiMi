package com.xzwzz.mimi.api;

import com.xzwzz.mimi.api.http.HttpArray;
import com.xzwzz.mimi.api.http.HttpResult;
import com.xzwzz.mimi.bean.AdBean;
import com.xzwzz.mimi.bean.AdListBean;
import com.xzwzz.mimi.bean.AvVideoListBean;
import com.xzwzz.mimi.bean.BalanceBean;
import com.xzwzz.mimi.bean.BaseBean;
import com.xzwzz.mimi.bean.BookBean;
import com.xzwzz.mimi.bean.BookDetailBean;
import com.xzwzz.mimi.bean.BuyVipBean;
import com.xzwzz.mimi.bean.ChannelDataBean;
import com.xzwzz.mimi.bean.ChannelDataBean1;
import com.xzwzz.mimi.bean.ConfigBean;
import com.xzwzz.mimi.bean.DiamondAdBean;
import com.xzwzz.mimi.bean.HotBean;
import com.xzwzz.mimi.bean.HttpPaybean;
import com.xzwzz.mimi.bean.InvitationBean;
import com.xzwzz.mimi.bean.MobileBean;
import com.xzwzz.mimi.bean.MoviesLinkListBean;
import com.xzwzz.mimi.bean.NovelTermBean;
import com.xzwzz.mimi.bean.NovieceBean;
import com.xzwzz.mimi.bean.Pay2Bean;
import com.xzwzz.mimi.bean.PayBean;
import com.xzwzz.mimi.bean.PayDialogBean;
import com.xzwzz.mimi.bean.PlatformBean;
import com.xzwzz.mimi.bean.PlatformBean1;
import com.xzwzz.mimi.bean.PlatformBean2;
import com.xzwzz.mimi.bean.ProfitBean;
import com.xzwzz.mimi.bean.PromotionBean;
import com.xzwzz.mimi.bean.QqBean;
import com.xzwzz.mimi.bean.QrCodeBean;
import com.xzwzz.mimi.bean.RtmpAddressBean;
import com.xzwzz.mimi.bean.TextAdBean;
import com.xzwzz.mimi.bean.TvTermBean;
import com.xzwzz.mimi.bean.UpdatePhotoBean;
import com.xzwzz.mimi.bean.UserBean;
import com.xzwzz.mimi.bean.UserInfoBean;
import com.xzwzz.mimi.bean.VideoBean;
import com.xzwzz.mimi.bean.VideoDetailBean;
import com.xzwzz.mimi.bean.VideoListBean;
import com.xzwzz.mimi.bean.VipBean;
import com.xzwzz.mimi.bean.WallperBean;
import com.xzwzz.mimi.bean.WallperListBean;
import com.xzwzz.mimi.bean.YunVideoBean;
import com.xzwzz.mimi.bean.ZhuBoBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiConstant {


    @POST("/index.php/appapi/Collector/channel")
    Observable<PlatformBean> getChannelList();

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpResult> share(@Field("service") String service, @Field("uid") String id);

    //    Home.getConfig获取动态设置
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<ConfigBean>> getConfig(@Field("service") String service);

    //    service=Home.DailiList
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<PromotionBean>> getPromotion(@Field("service") String service);

    @FormUrlEncoded
    @POST("/index.php/appapi/Collector/liveList")
    Observable<ChannelDataBean> getChannelData(@Field("id") String id);

    @FormUrlEncoded
    @POST("/index.php/appapi/Collector/getroom")
    Observable<RtmpAddressBean> getRtmpAddress(@Field("appid") String appid, @Field("user_id") String user_id, @Field("room_id") String room_id, @Field("cookie") String cookie, @Field("id") String id);

    @POST("/api/public/?service=Home.NovelList")
    Observable<HttpArray<BookBean>> getBookList();

    @FormUrlEncoded
    @POST("/api/public/?service=Home.NovelList")
    Observable<HttpArray<BookBean>> getBookList(@Field("id") String id);

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<BookDetailBean>> getBookList(@Field("service") String service, String id);

    @POST("/api/public/?service=Home.TermList")
    Observable<HttpArray<VideoBean>> getVideoList();

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<VideoListBean>> getVideoListData(@Field("service") String service, @Field("id") String id);


    @POST("/api/public/?service=Home.PictureList")
    Observable<HttpArray<WallperBean>> getWallperList();

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpResult<WallperListBean>> getWallperList(@Field("service") String service, @Field("id") String id);

    //获取广告
    @POST("/api/public/?service=User.ad")
    Observable<HttpArray<AdBean>> getAd();

    //轮播图
    @POST("/api/public/?service=Home.getHot")
    Observable<HttpArray<HotBean>> getBanner();

    //小说详情
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpResult<BookBean>> getNoveldetails(@Field("service") String service, @Field("id") String id);


    //-----------------------------------------------------------------支付宝支付和微信支付-----------------------------------------------------------------
//    Charge.getWxOrder
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<BaseBean> wxPay(@Field("service") String service, @Field("uid") String uid, @Field("changeid") String changeid, @Field("coin") String price, @Field("money") String num);

    //    Charge.getAliOrder
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<HttpPaybean>> aliPay(@Field("service") String service, @Field("uid") String uid, @Field("money") String money, @Field("type") String type, @Field("changeid") String changeid, @Field("coin") String coin);

    //--------------------------------------------------------------------用户验证相关-------------------------------------------------------------------------
    //登录
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<UserBean>> login(@Field("service") String service, @Field("user_login") String user, @Field("user_pass") String pwd);

    //登录
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<UserBean>> thirdLogin(@Field("service") String service, @Field("openid") String openid, @Field("nicename") String nicename, @Field("type") String type, @Field("avatar") String avatar);

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<UserBean>> thirdLogin(@Field("service") String service, @Field("openid") String openid, @Field("nicename") String nicename, @Field("type") String type, @Field("avatar") String avatar, @Field("invitation_code") String invitation_code);

    //User.iftoken检测TOKEN
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<BaseBean>> checkToken(@Field("service") String service, @Field("uid") String uid, @Field("token") String token);

    //注册
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<UserBean>> register(@Field("service") String service, @Field("user_login") String user, @Field("user_pass") String pwd, @Field("code") String code, @Field("invitation_code") String invitationCode);


    //获取验证码service=Login.getCode
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<BaseBean> getCode(@Field("service") String service, @Field("mobile") String moblie);

    //获取验证码service=Login.getForgetCode
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<BaseBean> getForgetCode(@Field("service") String service, @Field("mobile") String moblie);

    //找回密码service=Login.userFindPass
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<BaseBean>> FindPassWord(@Field("service") String service, @Field("user_login") String user_login, @Field("user_pass") String user_pass, @Field("user_pass2") String user_pass2, @Field("code") String code);


    //找回密码service=User.updatePass
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<BaseBean>> ModifyPassWord(@Field("service") String service, @Field("token") String token, @Field("uid") String uid, @Field("oldpass") String oldpass, @Field("pass") String pass, @Field("pass2") String pass2);


    //                .addParams("service", "User.getBaseInfo")
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<UserInfoBean>> getBaseUserInfo(@Field("service") String service, @Field("token") String token, @Field("uid") String uid);


    //获取邀请信息
    //                .addParams("service", "User.getBaseInfo")
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<InvitationBean>> getInvitationInfo(@Field("service") String service, @Field("uid") String uid);

    //    User.getBalance
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<BalanceBean>> getBalance(@Field("service") String service, @Field("token") String token, @Field("uid") String uid);

    //   User.getProfit
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<ProfitBean>> getWithdraw(@Field("service") String service, @Field("token") String token, @Field("uid") String uid);

    //   User.setCash
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<BaseBean> requestCash(@Field("service") String service, @Field("token") String token, @Field("uid") String uid, @Field("account") String account, @Field("name") String name, @Field("money") String money);

    //   Charge.monthCardList
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<BuyVipBean>> getVipList(@Field("service") String service, @Field("uid") String uid);

    //   Charge.getcoinCard
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<BaseBean> BuyVip(@Field("service") String service, @Field("uid") String uid, @Field("changeid") String changeid);

    //   User.updateFields
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<BaseBean> ModifyUserInfo(@Field("service") String service, @Field("fields") String fields, @Field("token") String token, @Field("uid") String uid);

    //   User.updateAvatar
    @Multipart
    @POST("/api/public/")
    Observable<HttpArray<UpdatePhotoBean>> UpdateAvatar(@Part() List<MultipartBody.Part> files);


    //   Charge.getcoinCard
    @FormUrlEncoded
    @POST("/index.php/appapi/Contribute/qrcode")
    Observable<QrCodeBean> getMyQrcode(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<VipBean> kami(@Field("service") String service, @Field("uid") String uid, @Field("code") String code);


    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<VideoListBean>> tiyan(@Field("service") String service);

    @GET()
    Observable<PlatformBean1> getChannelList1(@Url String url);

    @GET()
    Observable<ChannelDataBean1> getChannelData1(@Url String url, @Query("pt") String pt);

    @GET()
    Observable<PlatformBean2> getChannelList2(@Url String url);

    @GET()
    Observable<ZhuBoBean> getChannelData2(@Url String url);

    //    Home.getConfig获取动态设置
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<QqBean> getQq(@Field("service") String service, @Field("uid") String id);

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<VideoListBean>> getVideo(@Field("service") String service);

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<NovelTermBean>> novelTerm(@Field("service") String service);

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<MoviesLinkListBean>> moviesLinkList(@Field("service") String service, @Field("uid") String uid);

    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<AvVideoListBean>> videoList(@Field("service") String service, @Field("id") String id);

    //service=home.TvTerm
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<TvTermBean>> tvTerm(@Field("service") String service);

    //service=home.TvList
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<TvTermBean.tvListbean>> tvList(@Field("service") String service, @Field("id") String id);

    //service=home.TvList
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<MobileBean> getIsVip(@Field("service") String service, @Field("code") String code, @Field("invitation_code") String invitation_code);

    //service=home.TvList
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<PayBean> getOrder(@Field("service") String service,
                                 @Field("uid") String code,
                                 @Field("change_type") String change_type,
                                 @Field("change_id") String change_id,
                                 @Field("type") String type,
                                 @Field("ip") String ip);

    @GET
    Observable<Pay2Bean> pay(@Url String url);

    //直播区会员充值规则
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<PayDialogBean>> getChargeRule(@Field("service") String service);

    //service=Home.noviece
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpResult<NovieceBean>> noviece(@Field("service") String service);

    //service=Home.adsList
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<AdListBean>> adsList(@Field("service") String service);

    //service=Home.avList
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpArray<TextAdBean>> textAd(@Field("service") String service);

    //service=Home.avList
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpResult> addViewNum(@Field("service") String service, @Field("id") String id, @Field("type") String type);


    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpResult> buyVideo(@Field("service") String service, @Field("uid") String uid, @Field("id") String id);

    //getfreenum
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpResult> getfreenum(@Field("service") String service, @Field("uid") String uid, @Field("type") String type);


    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpResult<VideoDetailBean>> videoDetail(@Field("service") String service, @Field("uid") String uid, @Field("id") String id);


    //service=Home.avList
    @FormUrlEncoded
    @POST("/api/public/")
    Observable<HttpResult<DiamondAdBean>> diamondAv(@Field("service") String service);
}
