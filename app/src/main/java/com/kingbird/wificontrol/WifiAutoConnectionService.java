package com.kingbird.wificontrol;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import com.socks.library.KLog;

/**
 * @ClassName: WifiAutoConnectionService
 * @Description: WiFi自动连接服务类
 * @Author: Pan
 * @CreateDate: 2020/5/8 14:51:28
 */
public class WifiAutoConnectionService extends Service {

//    public static final String SSID = "HUAWEI";
//    public static final String PWD = "nihao321";
    private static final String TAG = WifiAutoConnectionService.class.getSimpleName();
    private static final String KEY_SSID = "KEY_SSID";
    private static final String KEY_PWD = "KEY_PWD";


    /**
     * wifi名
     */
    private String mSsid = "";
    /**
     * 密码
     */
    private String mPwd = "";


    /**
     * 负责不断尝试连接指定wifi
     */
    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            WifiConnectionManager.getInstance(WifiAutoConnectionService.this).connect(mSsid, mPwd);
            boolean connected = WifiConnectionManager.getInstance(WifiAutoConnectionService.this).isConnected(mSsid);
            KLog.e("热点连接结果：" + connected);
            Log.d(TAG, "handleMessage: wifi connected = " + connected);
            if (!connected) {
                Log.d(TAG, "handleMessage: re-try in 5 seconds");
                mHandler.sendEmptyMessageDelayed(0, 5000);//5s循环
            }
            return true;
        }
    });

    /**
     * 连接指定wifi热点, 失败后5s循环
     *
     * @param context 用于启动服务的上下文
     * @param ssid    默认HUD-WIFI
     * @param pwd     (WPA加密)默认12345678
     */
    public static void start(Context context, String ssid, String pwd) {
        Intent starter = new Intent(context, WifiAutoConnectionService.class);
        starter.putExtra(KEY_SSID, ssid).putExtra(KEY_PWD, pwd);
        context.startService(starter);
        Log.d(TAG, "start: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * @return always null
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSsid = intent.getStringExtra(KEY_SSID);
        mPwd = intent.getStringExtra(KEY_PWD);
        KLog.e("热点名称(输入)：" + mSsid);
        KLog.e("热点密码(输入)：" + mPwd);

        SpUtil.writeString(Const.SSID, mSsid);
        SpUtil.writeString(Const.PASSWORD, mPwd);
//        mSsid = "Kingbird-DL";
//        mPwd = "kingbird20015";
//        KLog.e("热点名称：" + mSsid);
//        KLog.e("热点密码：" + mPwd);
        mHandler.sendEmptyMessage(0);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

}
