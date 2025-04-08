package com.example.app_foodstore.Indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DotPagerIndicatorDecoration extends RecyclerView.ItemDecoration {

    private final float DP;
    private final int colorActive = 0xFFCCCCCC;    // Màu chấm đang chọn
    private final int colorInactive = 0xFFF6F6F6;  // Màu chấm chưa chọn

    private final float dotRadius = 4;       // bán kính chấm
    private final float dotPadding = 8;      // khoảng cách

    private final Paint paint = new Paint();

    private final int itemsPerPage;
    private final int orientation;

    public DotPagerIndicatorDecoration(Context context, int itemsPerPage, int orientation) {
        this.DP = context.getResources().getDisplayMetrics().density;
        this.itemsPerPage = itemsPerPage;
        this.orientation = orientation;

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.Adapter<?> adapter = parent.getAdapter();
        if (adapter == null || adapter.getItemCount() == 0) return;

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!(layoutManager instanceof GridLayoutManager)) return;

        GridLayoutManager glm = (GridLayoutManager) layoutManager;
        if (glm.getOrientation() != orientation) return;

        int itemCount = adapter.getItemCount();
        int totalPages = (int) Math.ceil((double) itemCount / itemsPerPage);
        if (totalPages <= 1) return;

        int firstVisibleItem = glm.findFirstVisibleItemPosition();
        int lastVisibleItem = glm.findLastVisibleItemPosition();

        int currentPage = ((lastVisibleItem + 1) / itemsPerPage) - 1;
        currentPage = Math.max(0, Math.min(currentPage, totalPages - 1)); // tránh out of bounds

        float dotDiameter = dotRadius * 2 * DP;

        if (orientation == RecyclerView.HORIZONTAL) {
            float totalWidth = (dotDiameter + dotPadding * DP) * totalPages - dotPadding * DP;
            float startX = (parent.getWidth() - totalWidth) / 2F;
            float posY = parent.getHeight() - 8 * DP;

            for (int i = 0; i < totalPages; i++) {
                paint.setColor(i == currentPage ? colorActive : colorInactive);
                float cx = startX + i * (dotDiameter + dotPadding * DP) + dotRadius * DP;
                canvas.drawCircle(cx, posY, dotRadius * DP, paint);
            }

        } else {
            float totalHeight = (dotDiameter + dotPadding * DP) * totalPages - dotPadding * DP;
            float startY = (parent.getHeight() - totalHeight) / 2F;
            float posX = parent.getWidth() - 8 * DP;

            for (int i = 0; i < totalPages; i++) {
                paint.setColor(i == currentPage ? colorActive : colorInactive);
                float cy = startY + i * (dotDiameter + dotPadding * DP) + dotRadius * DP;
                canvas.drawCircle(posX, cy, dotRadius * DP, paint);
            }
        }

        Log.d("DotIndicator", "onDrawOver called");
        Log.d("DotIndicator", "firstVisibleItem = " + firstVisibleItem);
        Log.d("DotIndicator", "Current page = " + currentPage);
        Log.d("DotIndicator", "Last visible item = " + lastVisibleItem);
    }
}
