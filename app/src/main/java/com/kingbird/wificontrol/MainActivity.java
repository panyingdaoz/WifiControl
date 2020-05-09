package com.kingbird.wificontrol;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @ClassName: Const
 * @Description: Const接口
 * @Author: Pan
 * @CreateDate: 2020/5/8 14:51:28
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEtAccount;
    private EditText mEtPwd;
    private WifiStateReceiver wifiStateReceiver;
    private Button mBtnAuto;
    private Button mBtnDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        mBtnAuto.setOnClickListener(this);
        mBtnDisconnect.setOnClickListener(this);
    }

    private void initView() {
        mEtAccount = findViewById(R.id.et_account);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnAuto = findViewById(R.id.btn_auto);
        mBtnDisconnect = findViewById(R.id.btn_disconnect);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiStateReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_auto: {
                if (wifiStateReceiver == null) {
                    wifiStateReceiver = new WifiStateReceiver(MainActivity.this);
                    registerReceiver(wifiStateReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
                    WifiAutoConnectionService.start(this,mEtAccount.getText().toString().trim(),mEtPwd.getText().toString().trim());
                }
            }
            break;
            case R.id.btn_disconnect: {
               WifiConnectionManager.getInstance(this).disconnect();
               WifiConnectionManager.getInstance(this).closeWifi();
            }
            break;
            default:
        }
    }
}
