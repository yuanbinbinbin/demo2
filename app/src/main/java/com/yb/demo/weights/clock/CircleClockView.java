package com.yb.demo.weights.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yb.demo.R;

import java.util.Date;

/**
 * 圆形时钟
 * Created by yb on 2017/12/6.
 */
public class CircleClockView extends View {

    public static final int TYPE_CIRCLE_CLOCK_SYSTEM = 1;//使用系统时间
    public static final int TYPE_CIRCLE_CLOCK_CUSTOM = 2;//使用自定义之间

    private int width;
    private int height;
    private int circleRadius;//圆宽高
    private int circleX;//圆心X坐标
    private int circleY;//圆心Y坐标
    private int lineColor;//线的颜色
    private int hour = -1;//时
    private int minute;//分
    private int second;//秒

    private int type;

    private Paint paint;//画笔

    public CircleClockView(Context context) {
        super(context);
        init();
    }

    public CircleClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        lineColor = getResources().getColor(R.color.color_21a9ff);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w - getPaddingRight() - getPaddingLeft();
        height = h - getPaddingBottom() - getPaddingTop();
        circleRadius = width > height ? height / 2 : width / 2;
        circleRadius = (int) (circleRadius - circleRadius / 25f);
        circleX = w / 2;
        circleY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (width <= 0 || height <= 0) {
            return;
        }
        float circleWidth = circleRadius / 50f;

        float fontSize = circleRadius / 10f;

        //绘制表盘
        //画圆
        paint.setStrokeWidth(circleWidth);//宽度为半径的50分之一
        canvas.drawCircle(circleX, circleY, circleRadius, paint);

        //画刻度
        for (int i = 0; i < 12; i++) {
            if (i == 0 || i == 3 || i == 6 || i == 9) {
                paint.setStrokeWidth(circleWidth * 2);
                canvas.drawLine(circleX, circleY - circleRadius, circleX, circleY - circleRadius + circleWidth * 6, paint);
                String content = String.valueOf(i);
                paint.setStrokeWidth(0);
                paint.setTextSize(fontSize);
                canvas.drawText(content, circleX - paint.measureText(content) / 2, circleY - circleRadius + circleWidth * 6 + fontSize + 5, paint);
            } else {
                paint.setStrokeWidth(circleWidth);
                canvas.drawLine(circleX, circleY - circleRadius, circleX, circleY - circleRadius + circleWidth * 3, paint);
                paint.setStrokeWidth(0);
                paint.setTextSize(fontSize);
                String content = String.valueOf(i);
                canvas.drawText(content, circleX - paint.measureText(content) / 2, circleY - circleRadius + circleWidth * 3 + fontSize + 5, paint);
            }
            canvas.rotate(30, circleX, circleY);//旋转画布
        }
        //画圆心
        paint.setStrokeWidth(circleWidth * 2);
        canvas.drawPoint(circleX, circleY, paint);

//        canvas.save();
        //绘制指针
        if (hour < 0) {
            setTimeMS(System.currentTimeMillis());
        } else {
            second++;
            if (second > 60) {
                second = 0;
                minute++;
                if (minute > 60) {
                    minute = 0;
                    hour++;
                    if (hour > 24) {
                        hour = 0;
                    }
                }
            }
        }
        float secondRotate = second * 6;
        float minuteRotate = minute * 6 + 6f * second / 60;
        float hourRotate = (hour % 12) * 30 + 30f * minute / 60f + 6f * second / 360;
        //画时针
        canvas.rotate(hourRotate, circleX, circleY);
        paint.setStrokeWidth(circleWidth * 2);
        canvas.drawLine(circleX, circleY, circleX, circleY / 2 + 2 * fontSize, paint);
        canvas.rotate(-hourRotate, circleX, circleY);
        //画分针
        canvas.rotate(minuteRotate, circleX, circleY);
        paint.setStrokeWidth(circleWidth);
        canvas.drawLine(circleX, circleY, circleX, circleY / 4 + 2 * fontSize, paint);
        canvas.rotate(-minuteRotate, circleX, circleY);
        //画秒针
        canvas.rotate(secondRotate, circleX, circleY);
        paint.setStrokeWidth(circleWidth / 2);
        canvas.drawLine(circleX, circleY, circleX, circleY / 8 + 2 * fontSize, paint);
        canvas.rotate(-secondRotate, circleX, circleY);
//        canvas.restore();
        postInvalidateDelayed(1000);
    }

    private void setTime(int hour, int minute, int secound) {
        this.hour = hour;
        this.minute = minute;
        this.second = secound;
    }

    public void setTimeMS(long time) {
        setTime(new Date(time));
    }

    public void setTimeS(long time) {
        setTimeMS(time * 1000);
    }

    public void setTime(Date date) {
        hour = date.getHours();
        minute = date.getMinutes();
        second = date.getSeconds();
    }
}
