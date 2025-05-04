package com.example.app_foodstore.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;

public class VoucheLabelView extends androidx.appcompat.widget.AppCompatTextView {

    private Paint paint;
    private Path path;
    private float cutRadius = 40f;

    public VoucheLabelView(Context context) {
        super(context);
        init();
    }

    public VoucheLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_HARDWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Vẽ ảnh như bình thường
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        // Tạo path hình tròn ở 2 góc phải
        path.reset();
        path.addCircle(w, 0, cutRadius, Path.Direction.CCW);          // Top right
        path.addCircle(w, h, cutRadius, Path.Direction.CCW);          // Bottom right

        // Dùng paint trong suốt để "xóa" phần hình ảnh
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPath(path, paint);
        paint.setXfermode(null);
    }
}

