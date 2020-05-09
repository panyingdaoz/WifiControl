package com.kingbird.wificontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.socks.library.KLog;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName: WifiStateReceiver
 * @Description: WiFi状态广播
 * @Author: Pan
 * @CreateDate: 2020/5/8 14:51:28
 */
public class WifiStateReceiver extends BroadcastReceiver {

    private static final String TAG = WifiStateReceiver.class.getName();
    private Context mContext;
    List<ScanResult> scanResults;

    public WifiStateReceiver(Context context) {
        this.mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String ssId;
        if (!Objects.equals(intent.getAction(), WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            return;
        }
        scanResults = WifiConnectionManager.getInstance(mContext).getWifiManager().getScanResults();
        for (int i = 0; i < scanResults.size(); i++) {
            ssId = (scanResults.get(i)).SSID;
            Log.e(TAG, "scanResults:----" + ssId);
        }
//        if (!WifiConnectionManager.getInstance(mContext).isConnected("HUAWEI")) {
//            WifiConnectionManager.getInstance(mContext).connect("HUAWEI", "nihao321");
//        } else

        String mSsid = SpUtil.readString(Const.SSID);
        String password = SpUtil.readString(Const.PASSWORD);
        KLog.e("读取到的ssid：" + mSsid);
        KLog.e("读取到的password：" + password);
//        assert ssId != null;
//        if (ssId.equals("Kingbird-DL")) {
            if (!WifiConnectionManager.getInstance(mContext).isConnected("Kingbird-DL")) {
                boolean result = WifiConnectionManager.getInstance(mContext).connect("Kingbird-DL", "kingbird20015");
                KLog.e("指定热点连接结果：" + result);
            } else {
                KLog.e("指定热点已连接");
            }
//        }
    }

}
