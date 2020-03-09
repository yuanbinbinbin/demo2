package com.base.baselibrary.weights;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * desc:流式布局<br>
 * author : yuanbin<br>
 * tel : 17610999926<br>
 * email : yuanbin@koalareading.com<br>
 * date : 2020/3/9 14:06<br>
 * 1. 在自定义View中处理padding，只需要在onDraw()中处理，别忘记处理布局为wrap_content的情况。<br>
 * 2. 在自定义ViewGroup中处理padding，只需要在onLayout()中，给子View布局时算上padding的值即可，也别忘记处理布局为wrap_content的情况。<br>
 * 3. 自定义View无需处理margin，在自定义ViewGroup中处理margin时，需要在onMeasure()中根据margin计算ViewGroup的宽、高，同时在onLayout中布局子View时也别忘记根据margin来布局。<br>
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        int width = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        int childCount = getChildCount();
        int maxWidth = measureWidth - getPaddingLeft() - getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (GONE != child.getVisibility()) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                MarginLayoutParams childParams = (MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth() + childParams.leftMargin + childParams.rightMargin;
                int childHeight = child.getMeasuredHeight() + childParams.topMargin + childParams.bottomMargin;
                if (lineWidth + childWidth > maxWidth) {
                    //需要换行
                    width = Math.max(width, lineWidth);
                    height += lineHeight;
                    lineWidth = childWidth;
                    lineHeight = childHeight;
                } else {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }
                if (i == childCount - 1) {
                    width = Math.max(width, childWidth);
                    height += lineHeight;
                }
            }
        }

        ///< wrap_content的模式
        if (measureWidthMode == MeasureSpec.AT_MOST && measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(
                    width + getPaddingLeft() + getPaddingRight(),
                    height + getPaddingTop() + getPaddingBottom()
            );
        }
        ///< 精确尺寸的模式
        else if (measureWidthMode == MeasureSpec.EXACTLY && measureHeightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(measureWidth, measureHeight);
        }
        ///< 宽度尺寸不确定，高度确定
        else if (measureHeightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(
                    width + getPaddingLeft() + getPaddingRight(),
                    measureHeight
            );
        }
        ///< 宽度确定，高度不确定
        else if (measureWidthMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(
                    measureWidth,
                    height + getPaddingTop() + getPaddingBottom()
            );
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int measureWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int top = getPaddingTop();
        int left = getPaddingLeft();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (GONE != child.getVisibility()) {
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                if (lineWidth + childWidth > measureWidth) {
                    //需要换行
                    top += lineHeight;
                    left = getPaddingLeft();
                    lineWidth = childWidth;
                    lineHeight = childHeight;
                } else {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }
                int lc = lp.leftMargin + left;
                int tc = lp.topMargin + top;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);

                left += childWidth;
            }
        }
    }


    //region 重写生成LayoutParams
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
    //endregion
}
