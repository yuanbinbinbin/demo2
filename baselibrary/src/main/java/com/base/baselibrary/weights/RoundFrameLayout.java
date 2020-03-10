package com.base.baselibrary.weights;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
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
    private final float[] mCornerRadii = new float[]{DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS};
    private boolean isOval;
    private float borderWidth = 0;
    private int borderColor;

    public RoundFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //对于ViewGroup 如果设置了background会走draw方法，如果不设置，就不会走，除非调用下边的方法
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mRoundViewDelegate = new RoundViewDelegate(this);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundFrameLayout, defStyleAttr, 0);
        isOval = a.getBoolean(R.styleable.RoundFrameLayout_rfl_is_oval, false);
        float borderWidth = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_border_width, 0);
        int borderColor = a.getColor(R.styleable.RoundFrameLayout_rfl_border_color, Color.BLACK);
        int radius = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius, 0);
        mCornerRadii[0] = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius_top_left, radius);
        mCornerRadii[1] = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius_top_right, radius);
        mCornerRadii[2] = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius_bottom_left, radius);
        mCornerRadii[3] = a.getDimensionPixelSize(R.styleable.RoundFrameLayout_rfl_corner_radius_bottom_right, radius);

        setBorderColor(borderColor);
        setBorderWidth(borderWidth);
        setRectRadius(mCornerRadii[0], mCornerRadii[1], mCornerRadii[2], mCornerRadii[3]);
        setOval(isOval);
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

    public void setOval(boolean oval) {
        this.isOval = oval;
        if (mRoundViewDelegate != null) {
            mRoundViewDelegate.setOval(oval);
        }
    }

    public void setBorderWidth(float borderWidthPx) {
        if (this.borderWidth != borderWidthPx) {
            this.borderWidth = borderWidthPx;
            if (mRoundViewDelegate != null) {
                mRoundViewDelegate.setBorderWidth(borderWidthPx);
            }
        }
    }

    public void setBorderColor(@ColorInt int color) {
        if (borderColor != color) {
            this.borderColor = color;
            if (mRoundViewDelegate != null) {
                mRoundViewDelegate.setBorderColor(color);
            }
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
        private final RectF tempRect = new RectF();
        private final RectF borderRect = new RectF();
        private Paint ovalPaint;
        private Paint roundPaint;
        private Paint imagePaint;
        private Paint borderPaint;
        private final Path path = new Path();
        private final Path borderPath = new Path();
        /*圆角的半径，依次为左上角xy半径，右上角，左下角，右下角*/
        private float[] rids = {0.0f, 0.0f, 0.0f, 0.0f};
        private boolean isOval = false;
        private float borderWidth = 0;
        private float halfBorderWidth = 0;
        private int borderColor = Color.BLACK;
        private View mView;

        public RoundViewDelegate(View view) {
            this.mView = view;
            init();
        }

        private void init() {

            ovalPaint = new Paint();
            ovalPaint.setAntiAlias(true);
            ovalPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            roundPaint = new Paint();
            roundPaint.setAntiAlias(true);
            roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

            imagePaint = new Paint();
            imagePaint.setAntiAlias(true);
            imagePaint.setStyle(Paint.Style.FILL);
            imagePaint.setColor(Color.WHITE);

            borderPaint = new Paint();
            borderPaint.setAntiAlias(true);
            borderPaint.setStyle(Paint.Style.STROKE);
        }

        public void roundRectSet(int width, int height) {
            roundRect.set(0, 0, width, height);
            borderRect.set(halfBorderWidth, halfBorderWidth, width - halfBorderWidth, height - halfBorderWidth);
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

        public void setOval(boolean oval) {
            this.isOval = oval;
            if (mView != null) {
                mView.postInvalidate();
            }
        }

        public void setBorderColor(@ColorInt int color) {
            if (color != borderColor) {
                borderColor = color;
                borderPaint.setColor(color);
                if (mView != null) {
                    mView.postInvalidate();
                }
            }
        }

        public void setBorderWidth(float borderWidthPx) {
            if (borderWidth != borderWidthPx) {
                borderWidth = borderWidthPx;
                borderPaint.setStrokeWidth(borderWidthPx);
                halfBorderWidth = borderWidth / 2;
                borderRect.set(halfBorderWidth, halfBorderWidth, roundRect.right - halfBorderWidth, roundRect.bottom - halfBorderWidth);
                if (mView != null) {
                    mView.postInvalidate();
                }
            }
        }

        public void preDraw(Canvas canvas) {
            canvas.saveLayer(roundRect, imagePaint, Canvas.ALL_SAVE_FLAG);
            if (isOval) {
                canvas.drawOval(roundRect, imagePaint);
                canvas.saveLayer(roundRect, ovalPaint, Canvas.ALL_SAVE_FLAG);
            } else {
                drawTopLeft(canvas);
                drawTopRight(canvas);
                drawBottomLeft(canvas);
                drawBottomRight(canvas);
                canvas.saveLayer(roundRect, roundPaint, Canvas.ALL_SAVE_FLAG);
            }
        }

        public void afterDraw(Canvas canvas) {
            canvas.restore();
            drawBorder(canvas);
        }

        private void drawTopLeft(Canvas canvas) {
            if (rids[0] > 0) {
                path.reset();
                path.moveTo(0, rids[0]);
                path.lineTo(0, 0);
                path.lineTo(rids[0], 0);
                tempRect.set(0, 0, rids[0] * 2, rids[0] * 2);
                path.arcTo(tempRect, -90, -90);
                path.close();
                canvas.drawPath(path, imagePaint);
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
                tempRect.set(width - 2 * topRightRadius, 0, width, topRightRadius * 2);
                path.arcTo(tempRect, 0, -90);
                path.close();
                canvas.drawPath(path, imagePaint);
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
                tempRect.set(0, height - 2 * bottomLeftRadius, bottomLeftRadius * 2, height);
                path.arcTo(tempRect, 90, 90);
                path.close();
                canvas.drawPath(path, imagePaint);
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
                tempRect.set(width - 2 * bottomRightRadius, height - 2 * bottomRightRadius, width, height);
                path.arcTo(tempRect, 0, 90);
                path.close();
                canvas.drawPath(path, imagePaint);
            }
        }

        private void drawBorder(Canvas canvas) {
            if (borderWidth > 0) {
                if (isOval) {
                    canvas.drawOval(borderRect, borderPaint);
                } else {
                    float topLeftRadius = rids[0];
                    float topRightRadius = rids[1];
                    float bottomLeftRadius = rids[2];
                    float bottomRightRadius = rids[3];
                    borderPath.reset();
                    //左上角弧度
                    if (topLeftRadius > 0) {
                        borderPath.moveTo(0 + borderRect.left, topLeftRadius + borderRect.top);
                        tempRect.set(borderRect.left, borderRect.top, topLeftRadius * 2 + borderRect.left, topLeftRadius * 2 + borderRect.top);
                        borderPath.addArc(tempRect, -90, -90);
                    } else {
                        borderPath.moveTo(roundRect.left, roundRect.top);
                        borderPath.lineTo(borderRect.left, borderRect.top);
                    }
                    //顶部横线
                    borderPath.moveTo(borderRect.left + topLeftRadius, 0 + borderRect.top);
                    borderPath.lineTo(borderRect.right - topRightRadius, 0 + borderRect.top);
                    //右上角弧度
                    if (topRightRadius > 0) {
                        borderPath.moveTo(borderRect.right - topRightRadius, 0 + borderRect.top);
                        tempRect.set(borderRect.right - 2 * topRightRadius, 0 + borderRect.top, borderRect.right, 2 * topRightRadius + borderRect.top);
                        borderPath.addArc(tempRect, 0, -90);
                    } else {
                        borderPath.moveTo(roundRect.right, roundRect.top);
                        borderPath.lineTo(borderRect.right, borderRect.top);
                    }
                    //右边竖线
                    borderPath.moveTo(borderRect.right, borderRect.top + topRightRadius);
                    borderPath.lineTo(borderRect.right, borderRect.bottom - bottomRightRadius);
                    //右下角弧度
                    if (bottomRightRadius > 0) {
                        borderPath.moveTo(borderRect.right, borderRect.bottom - bottomRightRadius);
                        tempRect.set(borderRect.right - 2 * bottomRightRadius, borderRect.bottom - 2 * bottomRightRadius, borderRect.right, borderRect.bottom);
                        borderPath.addArc(tempRect, 0, 90);
                    } else {
                        borderPath.moveTo(roundRect.right, roundRect.bottom);
                        borderPath.lineTo(borderRect.right, borderRect.bottom);
                    }
                    //底部横线
                    borderPath.moveTo(borderRect.right - bottomRightRadius, borderRect.bottom);
                    borderPath.lineTo(borderRect.left + bottomLeftRadius, borderRect.bottom);
                    //左下角弧度
                    if (bottomLeftRadius > 0) {
                        borderPath.moveTo(borderRect.left + bottomLeftRadius, borderRect.bottom);
                        tempRect.set(borderRect.left, borderRect.bottom - 2 * bottomLeftRadius, borderRect.left + bottomLeftRadius * 2, borderRect.bottom);
                        borderPath.addArc(tempRect, 90, 90);
                    } else {
                        borderPath.moveTo(roundRect.left, roundRect.bottom);
                        borderPath.lineTo(borderRect.left, borderRect.bottom);
                    }
                    //左边竖线
                    borderPath.moveTo(borderRect.left, borderRect.bottom - bottomLeftRadius);
                    borderPath.lineTo(borderRect.left, borderRect.top + topLeftRadius);
                    canvas.drawPath(borderPath, borderPaint);
                }
            }
        }
    }
}
