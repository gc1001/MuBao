package com.example.mubao;


import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.quicksdk.BaseCallBack;
import com.quicksdk.Extend;
import com.quicksdk.FuncType;
import com.quicksdk.Payment;
import com.quicksdk.QuickSDK;
import com.quicksdk.Sdk;
import com.quicksdk.User;
import com.quicksdk.entity.GameRoleInfo;
import com.quicksdk.entity.OrderInfo;
import com.quicksdk.entity.UserInfo;
import com.quicksdk.notifier.ExitNotifier;
import com.quicksdk.notifier.InitNotifier;
import com.quicksdk.notifier.LoginNotifier;
import com.quicksdk.notifier.LogoutNotifier;
import com.quicksdk.notifier.PayNotifier;
import com.quicksdk.notifier.SwitchAccountNotifier;

import org.json.JSONException;
import org.json.JSONObject;

import quicksdk_packName.R;

public class MainActivity extends Activity {

    public static String tag = "母包日志";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.quicksdk.Sdk.getInstance().onCreate(this);
        setContentView(R.layout.activity_main);

        initView();
        initQuickNotifier();
        initQuick();


        Extend.getInstance().isFunctionSupported(FuncType.CUSTOM);



    }

    private void test(){
        /*boolean lowMemoryKillReportSupported = ActivityManager.isLowMemoryKillReportSupported();
        Log.d(tag,lowMemoryKillReportSupported+"");*/
//        String testConfig = Extend.getInstance().getExtrasConfig("test1");
//        String testConfig2 = Extend.getInstance().getExtrasConfig("test2");
//        Log.d(tag,"自定义参数1 ==== "+testConfig);
//        Log.d(tag,"自定义参数2 ==== "+testConfig2);
        int MSG_ENTER_REALNAME = 125;
        Log.e("test1111111", "enter getrealName info");
        if (Extend.getInstance().isFunctionSupported(MSG_ENTER_REALNAME))
        {
            Extend.getInstance().callFunctionWithParamsCallBack(this, MSG_ENTER_REALNAME,
                    new BaseCallBack() {

                        @Override
                        public void onSuccess(Object... arg0) {
                            // TODO Auto-generated method stub
                            if (arg0 != null && arg0.length > 0) {
                                JSONObject jsonObject = (JSONObject) arg0[0];
                                Log.d("json", "==========" + jsonObject.toString());
//	                                // 用户id
//	                                String uid = jsonObject.getString("uid");
//	                                // 年龄, 如果渠道没返回默认为-1
//	                                int age = jsonObject.getInt("age");
//	                                // 是否已实名 true表示已实名
//	                                // false表示未实名,如果渠道没返回默认为false
//	                                boolean realName = jsonObject.getBoolean("realName");
//	                                // oppo实名认证失败之后是否可以继续游戏 true表示可以
//	                                // false表示不可以,如果渠道没返回默认为true
//	                                boolean resumeGame = jsonObject.getBoolean("resumeGame");
//	                                // 预留字段,如果渠道没返回默认为""的字符串
//	                                String other = jsonObject.getString("other");
//
//                                callUnityFunc("onGetRealNameInfoSuccess", jsonObject.toString());
                            }
                        }

                        @Override
                        public void onFailed(Object... arg0) {
                            // TODO Auto-generated method stub
                            //callUnityFunc("onGetRealNameInfoFail", "get realName failed");
                            Log.d(tag,"get realName failed");
                        }
                    });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        com.quicksdk.Sdk.getInstance().onStart(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        com.quicksdk.Sdk.getInstance().onRestart(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        com.quicksdk.Sdk.getInstance().onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        com.quicksdk.Sdk.getInstance().onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        com.quicksdk.Sdk.getInstance().onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        com.quicksdk.Sdk.getInstance().onDestroy(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        com.quicksdk.Sdk.getInstance().onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        com.quicksdk.Sdk.getInstance().onActivityResult(this, requestCode, resultCode, data);
    }

    private void toastUtil(String val) {
//        Toast.makeText(this, val, Toast.LENGTH_SHORT).show();
        Log.d(tag, val);
        textView.setText(val);
    }

    private void printUserInfo(UserInfo userInfo) {
        Log.d(tag, "uid == " + userInfo.getUID());
        Log.d(tag, "username == " + userInfo.getUserName());
        Log.d(tag, "token == " + userInfo.getToken());
        String val = "登录成功n" +
                "\nuid == " + userInfo.getUID()+
                "\nusername == " + userInfo.getUserName()+
                "\ntoken == " + userInfo.getToken();
        toastUtil(val);
    }

    //设置通知
    private void initQuickNotifier() {
        QuickSDK.getInstance().setInitNotifier(new InitNotifier() {
            @Override
            public void onSuccess() {
                toastUtil("初始化成功！！！");
            }

            @Override
            public void onFailed(String s, String s1) {
                toastUtil("初始化失败！！！");
            }
        })
                .setLoginNotifier(new LoginNotifier() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        if (userInfo != null){
                            toastUtil("++++++++++++++++++++");
                        }
                        toastUtil("登录成功");
                        printUserInfo(userInfo);
                        verifyRealName();
                    }

                    @Override
                    public void onCancel() {

                        toastUtil("登录取消");
                    }

                    @Override
                    public void onFailed(String s, String s1) {
                        toastUtil("登录失败\t" + s + "\t" + s1);
                    }
                })
                .setLogoutNotifier(new LogoutNotifier() {
                    @Override
                    public void onSuccess() {
                        toastUtil("注销成功");
                    }

                    @Override
                    public void onFailed(String s, String s1) {
                        toastUtil("注销失败\t" + s + "\t" + s1);
                    }
                })
                .setSwitchAccountNotifier(new SwitchAccountNotifier() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        toastUtil("切换账号成功");
                        printUserInfo(userInfo);
                        verifyRealName();
                    }

                    @Override
                    public void onCancel() {
                        toastUtil("切换账号取消");
                    }

                    @Override
                    public void onFailed(String s, String s1) {
                        toastUtil("切换账号失败\t" + s + "\t" + s1);
                    }
                })
                .setPayNotifier(new PayNotifier() {
                    @Override
                    public void onSuccess(String sdkOrderID, String cpOrderID, String extrasParams) {
                        toastUtil("支付成功\t" + "sdkOrderId " + sdkOrderID + "\tcpOrderID " + cpOrderID + "\textrasParams " + extrasParams);
                    }

                    @Override
                    public void onCancel(String s) {
                        toastUtil("支付取消");
                    }

                    @Override
                    public void onFailed(String cpOrderID, String message, String trace) {
                        toastUtil("支付失败\t" + "cpOrderID " + cpOrderID + "\tmessage " + message + "\ttrace " + trace);
                    }
                })
                .setExitNotifier(new ExitNotifier() {
                    @Override
                    public void onSuccess() {
                        toastUtil("退出成功");
                        MainActivity.this.finish();
                        System.exit(1);
                    }

                    @Override
                    public void onFailed(String s, String s1) {
                        toastUtil("退出失败\t" + s + "\t" + s1);
                    }
                });
    }




    //申请权限和sdk初始化
    private void initQuick() {
        try {
            //check权限
            if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                    || (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                //没有 ，  申请权限  权限数组
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                // 有 则执行初始化
                Sdk.getInstance().init(MainActivity.this, "88049844578484520615487574815873", "82414864");
            }
        } catch (Exception e) {
            //异常  继续申请
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

//        Log.d(tag,"22222222");
//        Sdk.getInstance().init(MainActivity.this, "88049844578484520615487574815873", "82414864");



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //申请成功
            Sdk.getInstance().init(MainActivity.this, "87479782996090661335532207214222", "96093813");
        } else {
            //没有权限再次申请
            initQuick();
        }
    }

    private void initView() {
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录
//                User.getInstance().login(MainActivity.this);
//                uploadRoleInfo();
                Contral.login(MainActivity.this);

            }
        });

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().logout(MainActivity.this);

            }
        });

        Button exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               exit();
            }
        });

        Button pay = findViewById(R.id.zhifu);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               payment();
               uploadRoleInfo();

            }
        });
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });

        textView = findViewById(R.id.tv);




    }

    public void m() {
        User.getInstance().login(MainActivity.this);
        uploadRoleInfo();
    }


    //上传角色信息
    private void uploadRoleInfo() {
        //注：GameRoleInfo的字段，以下所有参数必须传，没有的请模拟一个参数传入;
        GameRoleInfo roleInfo = new GameRoleInfo();
        roleInfo.setServerID("1");//数字字符串，不能含有中文字符
        roleInfo.setServerName("服务器1");
        roleInfo.setGameRoleName("石头");
        roleInfo.setGameRoleID("1121121");
        roleInfo.setGameBalance("5000");
        roleInfo.setVipLevel("4");//设置当前用户vip等级，必须为数字整型字符串,请勿传"vip1"等类似字符串
        roleInfo.setGameUserLevel("12");//设置游戏角色等级
        roleInfo.setPartyName("无敌联盟");//设置帮派名称
        roleInfo.setRoleCreateTime("1473141432"); //UC，当乐与1881，TT渠道必传，值为10位数时间戳
        roleInfo.setPartyId("1100"); //360渠道参数，设置帮派id，必须为整型字符串
        roleInfo.setGameRoleGender("男");//360渠道参数
        roleInfo.setGameRolePower("38"); //360,TT语音渠道参数，设置角色战力，必须为整型字符串
        roleInfo.setPartyRoleId("11"); //360渠道参数，设置角色在帮派中的id
        roleInfo.setPartyRoleName("帮主"); //360渠道参数，设置角色在帮派中的名称
        roleInfo.setProfessionId("38"); //360渠道参数，设置角色职业id，必须为整型字符串
        roleInfo.setProfession("法师"); //360渠道参数，设置角色职业名称
        roleInfo.setFriendlist("无"); //360渠道参数，设置好友关系列表，格式请参考：http://open.quicksdk.net/help/detail/aid/190

        User.getInstance().setGameRoleInfo(MainActivity.this, roleInfo, false);
    }

    public void payment() {
        GameRoleInfo roleInfo = new GameRoleInfo();
        roleInfo.setServerID("1");//数字字符串
        roleInfo.setServerName("服务器1");
        roleInfo.setGameRoleName("石头");
        roleInfo.setGameRoleID("1121121");
        roleInfo.setGameUserLevel("12");
        roleInfo.setVipLevel("4");
        roleInfo.setGameBalance("5000");
        roleInfo.setPartyName("xx联盟");
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCpOrderID("20150917003200119310");
        orderInfo.setGoodsName("元宝");//商品名称，不带数量
        orderInfo.setCount(60);//游戏币数量
        orderInfo.setAmount(6);
        orderInfo.setGoodsID("1");
        orderInfo.setGoodsDesc("Diamond_60_商品描述");
        orderInfo.setPrice(0.1);
        orderInfo.setExtrasParams("额外参数");
        Payment.getInstance().pay(MainActivity.this, orderInfo, roleInfo);

    }

    private void exit() {
        if (QuickSDK.getInstance().isShowExitDialog()) {
            Sdk.getInstance().exit(MainActivity.this);
        } else {
            // 游戏调用自身的退出对话框，点击确定后，调用quick的exit接口
            new AlertDialog.Builder(MainActivity.this).setTitle("退出").setMessage("是否退出游戏?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Sdk.getInstance().exit(MainActivity.this);
                    MainActivity.this.finish();
                    System.exit(1);
                }
            }).setNegativeButton("取消", null).show();
        }
    }

    private void verifyRealName() {
        final Activity activity = this;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 判断渠道是否支持实名认证功能
                if (Extend.getInstance().isFunctionSupported(FuncType.REAL_NAME_REGISTER)) {
                    Extend.getInstance().callFunctionWithParamsCallBack(activity, FuncType.REAL_NAME_REGISTER, new BaseCallBack() {
                        @Override
                        public void onSuccess(Object... arg0) {
                            if (arg0 != null && arg0.length > 0) {
                                JSONObject jsonObject = (JSONObject) arg0[0];
                                Log.d("json", "==========" + jsonObject.toString());
                                try {
                                    // 用户id
                                    String uid = jsonObject.getString("uid");
                                    // 年龄, 如果渠道没返回默认为-1
                                    int age = jsonObject.getInt("age");
                                    // 是否已实名 true表示已实名
                                    // false表示未实名,如果渠道没返回默认为false
                                    boolean realName = jsonObject.getBoolean("realName");
                                    // oppo实名认证失败之后是否可以继续游戏 true表示可以
                                    // false表示不可以,如果渠道没返回默认为true
                                    boolean resumeGame = jsonObject.getBoolean("resumeGame");
                                    // 预留字段,如果渠道没返回默认为""的字符串
                                    String other = jsonObject.getString("other");
                                    // 游戏根据返回信息做对应的逻辑处理

                                    Log.d(tag,"realName \t"+realName);
                                    Log.d(tag,"resumeGame \t"+resumeGame);
                                    Log.d(tag,"age \t"+age);
                                    Log.d(tag,"uid \t"+uid);

                                    Log.d(tag,"other \t"+other);


                                } catch ( JSONException e) {

                                }
                            }
                        }

                        @Override
                        public void onFailed(Object... arg0) {

                        }
                    });
                }
            }
        });
    }


}