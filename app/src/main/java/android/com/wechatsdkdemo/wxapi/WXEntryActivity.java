package android.com.wechatsdkdemo.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WXApiHelper.get().handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        final int type = baseResp.getType();
        if (ConstantsAPI.COMMAND_SENDAUTH == type) {//登录
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:

                    final String code = ((SendAuth.Resp) baseResp).code;
                    getWxLoginInfo(code);

                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    Toast.makeText(this, "拒绝授权微信登录", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    Toast.makeText(this, "已取消", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                default:
                    Toast.makeText(this, "错误码：" + baseResp.errCode, Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        } else if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == type) {//分享
            finish();
        }
    }

    private void getWxLoginInfo(String code) {
        //TODO 通过微信SDK返回的code，请求自己的服务器获取用户信息。
    }
}
