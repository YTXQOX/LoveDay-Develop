package com.ljstudio.android.loveday.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import com.ljstudio.android.loveday.R;
import com.ljstudio.android.loveday.utils.SystemOutUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by chenjianbin on 2018-4-9.
 */

public class PPangActivity extends AppCompatActivity {

    private WebView webView;
    private Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppang);

        webView = findViewById(R.id.id_ppang_web_view);
        button = findViewById(R.id.id_ppang_button);

//        setStatusBar(ContextCompat.getColor(this, R.color.colorWhite));
//        setStatusBar(Color.parseColor("#FF00FFF0"));
        getColor2Set();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getColor2Set() {
        String strColor = "#FFFFFFFF";
        Bitmap bitmap = convertViewToBitmap(button);
//        Bitmap bitmap = getBitmapFromView(button);

        if (null != bitmap) {
            SystemOutUtil.sysOut("bitmap.getWidth()==>" + bitmap.getWidth());
            SystemOutUtil.sysOut("bitmap.getHeight()==>" + bitmap.getHeight());

            int pixel = bitmap.getPixel(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            SystemOutUtil.sysOut("pixel==>" + pixel);

            //获取颜色
            int redValue = Color.red(pixel);
            int greenValue = Color.green(pixel);
            int blueValue = Color.blue(pixel);
            SystemOutUtil.sysOut("redValue==>" + redValue);
            SystemOutUtil.sysOut("greenValue==>" + greenValue);
            SystemOutUtil.sysOut("blueValue==>" + blueValue);

            strColor = "#" + Integer.toHexString(pixel).toUpperCase();
            SystemOutUtil.sysOut("strColor==>" + strColor);

            bitmap.recycle();
        }

        setStatusBar(Color.parseColor(strColor));
    }

    public Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    private Bitmap getBitmapFromView(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        SystemOutUtil.sysOut("width==>" + width);
        SystemOutUtil.sysOut("height==>" + height);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        // Draw background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }

        // Draw view to canvas
        view.draw(canvas);
        return bitmap;
    }

    private void setStatusBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(false);
            tintManager.setTintColor(color);
        }
    }
}