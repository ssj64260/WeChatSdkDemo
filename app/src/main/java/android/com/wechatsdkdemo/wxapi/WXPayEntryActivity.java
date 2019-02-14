package android.com.wechatsdkdemo.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

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

        if (ConstantsAPI.COMMAND_PAY_BY_WX == type) {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //TODO 支付成功
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    Toast.makeText(this, "支付发生错误", Toast.LENGTH_LONG).show();
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
        }
    }
}
