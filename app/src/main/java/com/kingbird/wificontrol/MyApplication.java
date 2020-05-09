package com.kingbird.wificontrol;

import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;

/**
 * 说明：
 *
 * @author Pan Yingdao
 * @time : 2020/5/8/012
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        KLog.init(LOG_DEBUG, "Pan");
//        Plog.init(true);
        KLog.init(true, "Pan");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        LitePal.initialize(this);
    }
}
