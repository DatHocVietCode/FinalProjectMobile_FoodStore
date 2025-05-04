package com.example.app_foodstore.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class VoucherContentLayout extends LinearLayout {

    private Paint paint;
    private Path path;
    private float cutRadius = 40f;

    public VoucherContentLayout(Context context) {
        super(context);
        init();
    }

    public VoucherContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        path.reset();
        // Cắt lõm 2 góc trái (top-left và bottom-left)
        path.addCircle(0, 0, cutRadius, Path.Direction.CCW);    // Top left
        path.addCircle(0, h, cutRadius, Path.Direction.CCW);    // Bottom left

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPath(path, paint);
        paint.setXfermode(null);
    }
}
