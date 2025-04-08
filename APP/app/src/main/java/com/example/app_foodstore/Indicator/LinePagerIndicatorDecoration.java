package com.example.app_foodstore.Indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
    private final float DP;
    /*private MainActivity MainActivity;*/

    private final int colorActive = 0xFFFFFFFF; // Màu trắng
    private final int colorInactive = 0x66FFFFFF; // Màu trắng mờ

    private final float indicatorHeight = 16;
    private final float indicatorStrokeWidth = 4;
    private final float indicatorItemLength = 8;
    private final float indicatorItemPadding = 8;

    private final Paint paint = new Paint();

    public LinePagerIndicatorDecoration(Context context) {
        /*this.MainActivity = activity;*/
        this.DP = context.getResources().getDisplayMetrics().density;
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(indicatorStrokeWidth * DP);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int itemCount = parent.getAdapter().getItemCount();
        if (itemCount == 0) return;
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int itemsPerPage = getItemsPerPage(parent, layoutManager);
        int totalPages = (int) Math.ceil((double) itemCount / itemsPerPage);

        if (totalPages <= 1) return; // Không cần indicator nếu chỉ có 1 trang

        int numIndicator = itemCount - itemsPerPage;
        float totalLength = indicatorItemLength * (numIndicator) * DP;
        float paddingBetweenItems = Math.max(0, numIndicator - 1) * indicatorItemPadding * DP;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;
        float indicatorPosY = parent.getHeight() - indicatorHeight * DP;

        drawInactiveIndicators(canvas, indicatorStartX, indicatorPosY, numIndicator);

        /*LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();*/
        int activePosition = layoutManager.findFirstVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) return;

        View activeChild = layoutManager.findViewByPosition(activePosition);
        float progress = (activeChild.getLeft() * -1) / (float) activeChild.getWidth();
        drawHighlights(canvas, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
    }

    private void drawInactiveIndicators(Canvas canvas, float startX, float posY, int itemCount) {
        paint.setColor(colorInactive);
        float itemWidth = indicatorItemLength + indicatorItemPadding;
        float start = startX;

        for (int i = 0; i < itemCount; i++) {
            canvas.drawLine(start, posY, start + indicatorItemLength * DP, posY, paint);
            start += itemWidth * DP;
        }
    }

    private void drawHighlights(Canvas canvas, float startX, float posY, int highlightPosition, float progress, int itemCount) {
        paint.setColor(colorActive);
        float itemWidth = indicatorItemLength + indicatorItemPadding;
        float highlightStart = startX + itemWidth * highlightPosition * DP;

        canvas.drawLine(highlightStart, posY, highlightStart + indicatorItemLength * DP, posY, paint);

        if (progress > 0 && highlightPosition < itemCount - 1) {
            float nextItemStart = highlightStart + itemWidth * DP;
            canvas.drawLine(nextItemStart, posY, nextItemStart + indicatorItemLength * DP * progress, posY, paint);
        }

    }
    private int getItemsPerPage(RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        View firstChild = recyclerView.getChildAt(0);
        if (firstChild == null) return 1;

        int itemHeight = firstChild.getHeight();
        int recyclerViewHeight = recyclerView.getHeight();
        return Math.max(1, recyclerViewHeight / itemHeight);
    }
}
