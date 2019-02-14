package android.com.wechatsdkdemo;

import android.app.Application;

/**
 * application
 */

public class APP extends Application {

    private static APP mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static APP getInstance(){
        return mApp;
    }

}
