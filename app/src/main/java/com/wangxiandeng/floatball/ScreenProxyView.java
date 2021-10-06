package com.wangxiandeng.floatball;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ScreenProxyView extends RelativeLayout {
    private ImageView proxy;

    @SuppressLint("ClickableViewAccessibility")
    public ScreenProxyView(Context context) {
        super(context);
        inflate(getContext(), R.layout.proxy_layout, this);
        proxy = (ImageView) findViewById(R.id.proxy_view);

        proxy.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                return false;
            }
        });

    }


}
