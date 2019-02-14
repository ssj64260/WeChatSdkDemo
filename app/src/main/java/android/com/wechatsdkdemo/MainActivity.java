package android.com.wechatsdkdemo;

import android.com.wechatsdkdemo.wxapi.PayInfo;
import android.com.wechatsdkdemo.wxapi.WXApiHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void doWeChatPay() {
        final PayInfo payInfo = new PayInfo();
        //TODO 实际的PayInfo需要调自己的服务器获取相关信息
        WXApiHelper.get().doWxPay(null);
    }

    private void doWeChatShare() {
        WXApiHelper.get().doShareUrlToWeChat("活动", "这是一个公益活动", "公益活动的网页链接");
    }

    private void doWeChatLogin() {
        WXApiHelper.get().doWxLogin();
    }
}
