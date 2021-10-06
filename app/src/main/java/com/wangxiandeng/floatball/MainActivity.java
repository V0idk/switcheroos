package com.wangxiandeng.floatball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends Activity {
    private Button mBtnStart;
    private Button mBtnQuit;
    public static final int REQUEST_MEDIA_PROJECTION = 18;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestCapturePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //5.0 之后才允许使用屏幕截图

            return;
        }

        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager)
                getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(
                mediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
                Toast.makeText(this, "请先允许FloatBall出现在顶部", Toast.LENGTH_SHORT).show();
            }
        }
        requestCapturePermission();
    }


    private void initView() {
        ImageView mBackground = (ImageView) findViewById(R.id.icon_float);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnQuit = (Button) findViewById(R.id.btn_quit);
//        mBackground.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, final MotionEvent event) {
//                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_POINTER_DOWN:
//                        break;
//                }
//                return true;
//            }
//        });
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent1 = new Intent(MainActivity.this, ScreenProxyService.class);
//                intent1.setClass(MainActivity.this, ScreenProxyService.class);
//                startService(intent1);

                checkAccessibility();
                Intent intent = new Intent(MainActivity.this, FloatBallService.class);
                Bundle data = new Bundle();
                data.putInt("type", FloatBallService.TYPE_ADD);
                intent.putExtras(data);
                startService(intent);
            }
        });
        mBtnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FloatBallService.class);
                Bundle data = new Bundle();
                data.putInt("type", FloatBallService.TYPE_DEL);
                intent.putExtras(data);
                startService(intent);
            }
        });
    }

    private void checkAccessibility() {
        // 判断辅助功能是否开启
        if (!AccessibilityUtil.isAccessibilitySettingsOn(this)) {
            // 引导至辅助功能设置页面
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            Toast.makeText(this, "请先开启FloatBall辅助功能", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        checkAccessibility();
//        Screenshot screenshot = new Screenshot("/storage/emulated/0/1");
//        screenshot.callForTheProjectionManager(this);
//        screenshot.startCaptureHandlingThread();
//        screenshot.afterActivityResult(this, resultCode, data);


        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION:
                if (resultCode == RESULT_OK && data != null) {
                    checkAccessibility();
                    FloatBallService.setResultData(data);
                    Bundle data1 = new Bundle();
                    data1.putInt("type", FloatBallService.TYPE_ADD);
                    data.putExtras(data1);
                    data.setClass(MainActivity.this, FloatBallService.class);
                    startService(data);
                }
                break;

        }

    }
}
