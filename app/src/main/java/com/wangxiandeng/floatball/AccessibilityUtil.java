package com.wangxiandeng.floatball;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangxiandeng on 2016/11/25.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class AccessibilityUtil {
    public static AccessibilityService tmp;
    public static Bitmap screenshot(AccessibilityService service) {
        FloatBallService cast = (FloatBallService) service;
        return cast.screenshot();
    }


    public static void saveBitMap(String filename, Bitmap bmp) {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void click(AccessibilityService service, int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder.addStroke(
                new GestureDescription.StrokeDescription(path, 0, 100))
                .build();
        service.dispatchGesture(gestureDescription, new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
            }
        }, null);
    }

    //商店

    public static void shop(AccessibilityService service) {
        click(service, 2230, 66);
    }

    //出售

    public static void sell(AccessibilityService service) {
        click(service, 1989, 862);
    }

    //关闭close
    public static void close(AccessibilityService service) {
        click(service, 2059, 108);
    }


    //攻击栏
    public static void attack(AccessibilityService service) {
        click(service, 362, 367);
    }

    //法术栏
    public static void magic(AccessibilityService service) {
        click(service, 364, 443);
    }

    //防御栏
    public static void equipment(AccessibilityService service) {
        click(service, 360, 560);
    }

    //购买
    public static void buy(AccessibilityService service) {
        click(service, 1950, 961);
    }

    //复活甲
    public static void fuhuojia(AccessibilityService service) throws InterruptedException {
        Thread.sleep(50);
        equipment(service);
        Thread.sleep(50);
        click(service, 1630, 603);
    }

    //名刀

    public static void mingdao(AccessibilityService service) throws InterruptedException {
        Thread.sleep(50);
        attack(service);
        Thread.sleep(50);
        click(service, 1501, 330);
    }

    //辉月

    public static void huiyue(AccessibilityService service) throws InterruptedException {
        Thread.sleep(50);
        magic(service);
        Thread.sleep(50);
        click(service, 795, 465);
    }

    //血魔

    public static void xuemo(AccessibilityService service) throws InterruptedException {
        Thread.sleep(50);
        equipment(service);
        Thread.sleep(50);
        click(service, 1643, 308);
    }

    //炽热支配着

    public static void chire(AccessibilityService service) throws InterruptedException {
        Thread.sleep(50);
        magic(service);
        Thread.sleep(50);
        click(service, 1491, 327);
    }


    public static void eq1(AccessibilityService service) {
        click(service, 885, 959);
    }


    public static void eq2(AccessibilityService service) {
        click(service, 1033, 959);
    }


    public static void eq3(AccessibilityService service) {
        click(service, 1190, 959);
    }


    public static void eq4(AccessibilityService service) {
        click(service, 1360, 959);
    }


    public static void eq5(AccessibilityService service) {
        click(service, 1517, 959);
    }


    public static void eq6(AccessibilityService service) {
        click(service, 1680, 959);
    }

    //桌面微信

    public static void wechat(AccessibilityService service) {
        click(service, 764, 2063);
    }

    public static class Point {
        int x;
        int y;
    }

    // 复活甲位置

    public static int fuhuojiapos(Bitmap image) {
        if (image == null) {
            return -1;
        }
        int[][] points = {
                {381, 1306, -11247513},
                {456, 1313, -12750926},
                {537, 1309, -7708360},
                {594, 1310, -10132104},
                {677, 1308, -3552357},
                {748, 1316, -5914957}
        };
        for (int i = 0; i < points.length; i++) {
            int[] data = points[i];
            int x = data[0];
            int y = data[1];
            int rgb = data[2];
            if (image.getPixel(x, y) == rgb) {
                return i;
            }
        }
        return -1;
//        saveBitMap("/storage/emulated/0/1/1.png", image);
    }


    public static void clickEQ(AccessibilityService service, int index) {
        if (index == 0) {
            eq1(service);
        }
        if (index == 1) {
            eq2(service);
        }
        if (index == 2) {
            eq3(service);
        }
        if (index == 3) {
            eq4(service);
        }
        if (index == 4) {
            eq5(service);
        }
        if (index == 5) {
            eq6(service);
        }
    }


    public static void miaohuan(AccessibilityService service) {
        try {
            shop(service);
            Thread.sleep(200);
            Bitmap image = screenshot(service);
            int fhj = fuhuojiapos(image);
            if (fhj < 0) {
                sell(service);
                fuhuojia(service);
            } else {
                clickEQ(service, fhj);
                Thread.sleep(150);
                sell(service);
                mingdao(service);
            }
            Thread.sleep(150);
            buy(service);
            Thread.sleep(50);
            close(service);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void doBack(AccessibilityService service) {
//        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
        miaohuan(service);
    }


    public static void doPullDown(AccessibilityService service) {
        miaohuan(service);
//        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }


    public static void doPullUp(AccessibilityService service) {
//        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
    }


    public static void doLeftOrRight(AccessibilityService service) {
        miaohuan(service);
//        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }


    void f() {
        //        handler.postDelayed(autoTouchRunnable, 5);
        //        System.out.println("Done");
        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                Instrumentation inst = new Instrumentation();
        //                inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(),
        //                        MotionEvent.ACTION_DOWN, 764, 2063, 0));
        //                inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(),
        //                        MotionEvent.ACTION_UP, 764, 2063, 0));
        //            }
        //        }).start();
        //        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
        //        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

}
