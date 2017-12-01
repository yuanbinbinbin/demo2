package com.yb.demo.weights;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yb.demo.R;

/**
 * Created by yb on 2017/11/23.
 */
public class AnimWidthFingerView extends View {
    private Paint paint;
    private boolean isSelected;
    private float fingerX;
    private float fingerY;
    private Matrix matrix;

    public AnimWidthFingerView(Context context) {
        this(context, null);
    }

    public AnimWidthFingerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimWidthFingerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        isSelected = false;
        matrix = new Matrix();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                fingerX = event.getRawX();
                fingerY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                fingerX = -1;
                fingerY = -1;
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isSelected) {
            drawSelectedStatus(canvas);
        } else {
            drawUnSelectedStatus(canvas);
        }
    }

    private void drawSelectedStatus(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        width = width - getPaddingLeft() - getPaddingRight();
        height = height - getPaddingBottom() - getPaddingTop();
        if (width <= 0 || height <= 0) {
            canvas.drawColor(Color.WHITE);
            return;
        }
    }

    private float percent = 2.0f / 3;//上下 首部占2/3

    private void drawUnSelectedStatus(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();
//        float lineWidth = width / 50f;
//
        int left = getPaddingLeft();
        int top = getPaddingTop();
//        int right = width - getPaddingRight();
//        int bottom = height - getPaddingBottom();
//        width = right - left;
//        height = bottom - top;
//        if (width <= 0 || height <= 0) {
//            canvas.drawColor(Color.WHITE);
//            return;
//        }
//
//        float headHeight = height * percent;
//        float radius = (width > headHeight ? headHeight : width) / 2;
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(getResources().getColor(R.color.color_999999));
//        paint.setStrokeWidth(lineWidth);
//        canvas.drawCircle(left + width / 2, top + headHeight / 2, radius, paint);
//
//        float startX = left + width / 2 - radius / 2;
//        float startY = top + headHeight / 3;
//        float endX = startX;
//        float endY = startY + headHeight / 3;
//        canvas.drawLine(startX, startY, endX, endY, paint);
//
//        startX = startX + radius;
//        startY = startY;
//        endX = startX;
//        endY = endY;
//        canvas.drawLine(startX, startY, endX, endY, paint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.skin_tab_icon_contact_normal);
        matrix.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, width, height), Matrix.ScaleToFit.FILL);
        canvas.drawBitmap(bitmap, matrix, paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.skin_tab_icon_contact_normal_eye), matrix, paint);
    }
}
