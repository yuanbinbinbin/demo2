package com.yb.demo.utils;

import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;

/**
 * Created by yb on 2017/12/13.
 */
public class SpannableStringUtil {
    private SpannableStringBuilder spannableString;

    private SpannableStringUtil(SpannableStringBuilder spannableString) {
        this.spannableString = spannableString;
    }

    public static SpannableStringUtil create(String s) {
        if (TextUtils.isEmpty(s)) {
            s = "";
        }
        return new SpannableStringUtil(new SpannableStringBuilder(s));
    }

    //在末尾添加文本

    /**
     * 在末尾添加
     *
     * @param s
     * @return
     */
    public SpannableStringUtil append(String s) {
        if (!TextUtils.isEmpty(s)) {
            spannableString.append(s);
        }
        return this;
    }

    //插入文本

    /**
     * 插入文本
     *
     * @param s
     * @param position
     * @return
     */
    public SpannableStringUtil insert(String s, int position) {
        if (!TextUtils.isEmpty(s)) {
            if (position >= spannableString.length()) {
                spannableString.append(s);
            } else {
                spannableString.insert(position, s);
            }
        }
        return this;
    }
    //指定文字颜色

    /**
     * 指定文字颜色
     *
     * @param color
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setTextForegroundColor(@ColorInt int color, int start, int end) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    // 指定文字背景颜色

    /**
     * 指定文字背景颜色
     *
     * @param color
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setTextBackgroundColor(@ColorInt int color, int start, int end) {
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    //指定文字大小

    /**
     * 指定文字大小
     *
     * @param size  相对与原有文字的大小 1.0f时 为原先文字大小， 0.5f时缩小到原先文字的一半
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setTextSize(float size, int start, int end) {
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(size);
        spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置中划线

    /**
     * 设置中划线
     *
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setStrikeThrough(int start, int end) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置下划线

    /**
     * 设置下划线
     *
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setUnderLine(int start, int end) {
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置上标

    /**
     * 设置上标
     *
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setSuperscript(int start, int end) {
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        spannableString.setSpan(superscriptSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置下标

    /**
     * 设置下标
     *
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setSubscript(int start, int end) {
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        spannableString.setSpan(subscriptSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置粗体

    /**
     * 设置粗体
     *
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setBold(int start, int end) {
        StyleSpan boldStyle = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldStyle, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置斜体

    /**
     * 设置斜体
     *
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setItalic(int start, int end) {
        StyleSpan italicStyle = new StyleSpan(Typeface.ITALIC);
        spannableString.setSpan(italicStyle, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置粗斜体

    /**
     * 设置粗斜体
     *
     * @param start 包括
     * @param end   不包括
     * @return
     */
    public SpannableStringUtil setBoldItalic(int start, int end) {
        StyleSpan boldItalicStyle = new StyleSpan(Typeface.BOLD_ITALIC);
        spannableString.setSpan(boldItalicStyle, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    //将指定位置文字替换为表情

    /**
     * 将指定位置文字替换为表情
     *
     * @param drawable
     * @param h
     * @param start    包括
     * @param end      不包括
     * @return
     */
    public SpannableStringUtil setImage(Drawable drawable, int h, int start, int end) {
        if (drawable == null || h <= 0) {
            return this;
        }
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, (int) (drawableWidth * 1.0f * h / drawableHeight), h);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    //设置可点击的文字

    /**
     * 设置可点击的文字
     *
     * @param clickable
     * @param start     包括
     * @param end       不包括
     * @return
     */
    public SpannableStringUtil setClickable(ClickableSpan clickable, int start, int end) {
        spannableString.setSpan(clickable, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置渐变

    /**
     * 设置渐变
     *
     * @param colors
     * @param model
     * @param start
     * @param end
     * @param width  //渐变的宽度
     * @return
     */
    public SpannableStringUtil setGradient(int[] colors, Shader.TileMode model, int start, int end, int width) {
        spannableString.setSpan(new GradientSpan(colors, width, model), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //设置渐变

    /**
     * 设置渐变
     *
     * @param gradient
     * @param start
     * @param end
     * @return
     */
    public SpannableStringUtil setGradient(GradientSpan gradient, int start, int end) {
        spannableString.setSpan(gradient, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    //生成最终的文本

    /**
     * 生成最终的文本
     *
     * @return
     */
    public SpannableStringBuilder makeText() {
        return spannableString;
    }


    public static class GradientSpan extends CharacterStyle implements UpdateAppearance {
        private Shader shader;
        private int[] colors;
        private int width;
        private Shader.TileMode model;
        private Matrix matrix;

        public GradientSpan(int[] colors, int width, Shader.TileMode model) {
            this.colors = colors;
            this.width = width;
            this.model = model;
            this.matrix = new Matrix();
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            if (colors == null || colors.length <= 0) {
                return;
            }

            if (shader == null) {
                shader = new LinearGradient(0, 0, width, 0, colors, null, model);
            }
            tp.setShader(shader);
        }

        //更新渐变位置，让渐变动起来

        /**
         * 更新渐变位置，让渐变动起来
         *
         * @param p
         */
        public void updateGradientPosition(float p) {
            if (p < 0 || shader == null) {
                return;
            }
            matrix.reset();
            matrix.setTranslate(p * width, 0);
            shader.setLocalMatrix(matrix);
        }
    }
}
