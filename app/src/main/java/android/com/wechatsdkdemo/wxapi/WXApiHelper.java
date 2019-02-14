package android.com.wechatsdkdemo.wxapi;

import android.com.wechatsdkdemo.APP;
import android.content.Intent;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信SDK帮助类
 */
public class WXApiHelper {

    private static final String WX_APP_ID = "wx666666";//TODO 微信相关
    private static final String WX_APP_SECRET = "66666666666666666";
    private static final String WX_SCOPE = "snsapi_userinfo";
    private static final String WX_STATE = "sport_dict_wx_login";
    private static final String WX_GRANT_TYPE = "authorization_code";
    private static final String WX_PACKAGE_VALUE = "Sign=WXPay";

    private static WXApiHelper mHelper;
    private final IWXAPI mWxApi;

    /**
     * APP.getInstance() 为Application的context
     */
    private WXApiHelper() {
        mWxApi = WXAPIFactory.createWXAPI(APP.getInstance(), WX_APP_ID, false);
        mWxApi.registerApp(WX_APP_ID);
    }

    public static WXApiHelper get() {
        if (mHelper == null) {
            mHelper = new WXApiHelper();
        }
        return mHelper;
    }

    /**
     * 微信登录
     */
    public void doWxLogin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = WX_SCOPE;
        req.state = WX_STATE;

        mWxApi.sendReq(req);
    }

    /**
     * 微信支付
     *
     * @param payInfo 支付相关信息，需要从自己的服务器获取
     */
    public void doWxPay(PayInfo payInfo) {
        PayReq req = new PayReq();
        req.appId = WX_APP_ID;
        req.partnerId = payInfo.getPartnerid();
        req.prepayId = payInfo.getPrepayid();
        req.nonceStr = payInfo.getNoncestr();
        req.timeStamp = payInfo.getTimestamp();
        req.packageValue = WX_PACKAGE_VALUE;
        req.sign = payInfo.getSign();

        mWxApi.sendReq(req);
    }

    /**
     * 微信分享url给朋友
     *
     * @param title 标题
     * @param description 内容
     * @param url 分享的url
     */
    public void doShareUrlToWeChat(String title, String description, String url) {
        doShareUrl(title, description, url, SendMessageToWX.Req.WXSceneSession);
    }

    /**
     * 微信分享url到朋友圈
     * @param title 标题
     * @param description 内容
     * @param url 分享的url
     */
    public void doShareUrlToTimeline(String title, String description, String url) {
        doShareUrl(title, description, url, SendMessageToWX.Req.WXSceneTimeline);
    }

    private void doShareUrl(String title, String description, String url, int scene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
//        Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.drawable.send_music_thumb);
//        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage";
        req.message = msg;
        req.scene = scene;
//        req.userOpenId = getOpenId();

        mWxApi.sendReq(req);
    }

    //TODO 其他分享功能可以写到这里

    public void handleIntent(Intent intent, IWXAPIEventHandler handler) {
        mWxApi.handleIntent(intent, handler);
    }
}
