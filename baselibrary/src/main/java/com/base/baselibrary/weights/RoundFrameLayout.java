package com.base.baselibrary.weights;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.base.baselibrary.R;

/**
 * desc:强制圆角layout<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2020/3/9 16:43
 */
public class RoundFrameLayout extends FrameLayout {
    private RoundViewDelegate mRoundViewDelegate;
    private static final float DEFAULT_RADIUS = 0f;
    private final float[] mCornerRadii =
            new float[]{DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS};

    public RoundFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRoundViewDelegate = new RoundViewDelegate(this);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundFrameLayout, defStyleAttr, 0);
        int radius = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius, 0);
        mCornerRadii[0] = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius_top_left, radius);
        mCornerRadii[1] = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius_top_right, radius);
        mCornerRadii[2] = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius_bottom_left, radius);
        mCornerRadii[3] = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius_bottom_right, radius);

        setRectRadius(mCornerRadii[0], mCornerRadii[1], mCornerRadii[2], mCornerRadii[3]);
        a.recycle();
    }

    public void setRectRadius(float radius) {
        setRectRadius(radius, radius, radius, radius);
    }

    public void setRectRadius(float topLeft, float topRightPx, float bottomLeftPx, float bottomRightPx) {
        mCornerRadii[0] = topLeft;
        mCornerRadii[1] = topRightPx;
        mCornerRadii[2] = bottomLeftPx;
        mCornerRadii[3] = bottomRightPx;
        if (mRoundViewDelegate != null) {
            mRoundViewDelegate.setRectRadius(mCornerRadii[0], mCornerRadii[1], mCornerRadii[2], mCornerRadii[3]);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRoundViewDelegate.roundRectSet(getWidth(), getHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        mRoundViewDelegate.preDraw(canvas);
        super.draw(canvas);
        mRoundViewDelegate.afterDraw(canvas);
    }

    public static class RoundViewDelegate {
        private final RectF roundRect = new RectF();
        private Paint roundPaint;
        private Paint imagePaint;
        private final Path path = new Path();
        /*圆角的半径，依次为左上角xy半径，右上角，左下角，右下角*/
        private float[] rids = {0.0f, 0.0f, 0.0f, 0.0f};
        private View mView;

        public RoundViewDelegate(View view) {
            this.mView = view;
            init();
        }

        private void init() {
            roundPaint = new Paint();
            roundPaint.setColor(Color.WHITE);
            roundPaint.setAntiAlias(true);
            roundPaint.setStyle(Paint.Style.FILL);
            roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

            imagePaint = new Paint();
            imagePaint.setXfermode(null);
        }

        public void roundRectSet(int width, int height) {
            roundRect.set(0, 0, width, height);
        }

        /**
         * 从新设置圆角
         *
         * @param radius
         */
        public void setRectRadius(float radius) {
            setRectRadius(radius, radius, radius, radius);
        }

        public void setRectRadius(float topLeft, float topRightPx, float bottomLeftPx, float bottomRightPx) {
            rids[0] = topLeft;
            rids[1] = topRightPx;

            rids[2] = bottomLeftPx;
            rids[3] = bottomRightPx;
            if (mView != null) {
                mView.postInvalidate();
            }
        }

        private void drawTopLeft(Canvas canvas) {
            if (rids[0] > 0) {
                path.reset();
                path.moveTo(0, rids[0]);
                path.lineTo(0, 0);
                path.lineTo(rids[0], 0);
                path.arcTo(new RectF(0, 0, rids[0] * 2, rids[0] * 2),
                        -90, -90);
                path.close();
                canvas.drawPath(path, roundPaint);
            }
        }

        private void drawTopRight(Canvas canvas) {
            float topRightRadius = rids[1];
            if (topRightRadius > 0) {
                int width = mView.getWidth();
                path.reset();
                path.moveTo(width - topRightRadius, 0);
                path.lineTo(width, 0);
                path.lineTo(width, topRightRadius);
                path.arcTo(new RectF(width - 2 * topRightRadius, 0, width,
                        topRightRadius * 2), 0, -90);
                path.close();
                canvas.drawPath(path, roundPaint);
            }
        }

        private void drawBottomLeft(Canvas canvas) {
            float bottomLeftRadius = rids[2];
            if (bottomLeftRadius > 0) {
                int height = mView.getHeight();
                path.reset();
                path.moveTo(0, height - bottomLeftRadius);
                path.lineTo(0, height);
                path.lineTo(bottomLeftRadius, height);
                path.arcTo(new RectF(0, height - 2 * bottomLeftRadius,
                        bottomLeftRadius * 2, height), 90, 90);
                path.close();
                canvas.drawPath(path, roundPaint);
            }
        }

        private void drawBottomRight(Canvas canvas) {
            float bottomRightRadius = rids[3];
            if (bottomRightRadius > 0) {
                int height = mView.getHeight();
                int width = mView.getWidth();
                path.reset();
                path.moveTo(width - bottomRightRadius, height);
                path.lineTo(width, height);
                path.lineTo(width, height - bottomRightRadius);
                path.arcTo(new RectF(width - 2 * bottomRightRadius, height - 2
                        * bottomRightRadius, width, height), 0, 90);
                path.close();
                canvas.drawPath(path, roundPaint);
            }
        }

        public void preDraw(Canvas canvas) {
            canvas.saveLayer(roundRect, imagePaint, Canvas.ALL_SAVE_FLAG);
        }

        public void afterDraw(Canvas canvas) {
            drawTopLeft(canvas);
            drawTopRight(canvas);
            drawBottomLeft(canvas);
            drawBottomRight(canvas);
            canvas.restore();
        }
    }
}
