package com.yb.demo.weights.bezier.bubble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.view.View;

import java.util.Random;

/**
 * Created by yb on 2017/12/1.
 */
public class BubbleBean {
    //动画时间
    private int duration = 2000;

    /**
     * 心的当前坐标
     */
    private Point point;
    /**
     * 移动动画
     */
    private ValueAnimator moveAnim;
    /**
     * 放大动画
     */
    private ValueAnimator zoomAnim;
    /**
     * 透明度
     */
    private int alpha = 255;//
    /**
     * 心图
     */
    private Bitmap bitmap;
    /**
     * 绘制bitmap的矩阵  用来做缩放和移动的
     */
    private Matrix matrix = new Matrix();
    /**
     * 缩放系数
     */
    private float sf = 0;
    /**
     * 产生随机数
     */
    private Random random;
    public boolean isEnd = false;//是否结束

    public BubbleBean(Context context, int resId, View container) {
        random = new Random();
        bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        init(new Point(container.getWidth() / 2, container.getHeight() - bitmap.getHeight() / 2), new Point(container.getWidth() / 2, 0));
    }


    public BubbleBean(Bitmap bitmap, View container) {
        random = new Random();
        this.bitmap = bitmap;
        //为了让在起始坐标点时显示完整 需要减去bitmap.getHeight()/2
        init(new Point(container.getWidth() / 2, container.getHeight() - bitmap.getHeight() / 2), new Point(container.getWidth() / 2, 0));
    }

    private void init(final Point startPoint, Point endPoint) {
        isEnd = false;
        point = startPoint;
        Point controllPoint1 = new Point(random.nextInt(startPoint.x), random.nextInt(startPoint.y / 2));
        Point controllPoint2 = new Point(startPoint.x + random.nextInt(startPoint.x), startPoint.y - random.nextInt(startPoint.y / 2));
        Path path = new Path();
        path.reset();
        path.moveTo(startPoint.x, startPoint.y);
//        x1、y1 代表控制点1的 x、y；
//
//        x2、y2 代表控制点2的 x、y；
//
//        x3、y3 代表目标点的 x、y；
        if (random.nextBoolean()) {
            path.cubicTo(controllPoint2.x, controllPoint2.y, controllPoint1.x, controllPoint1.y, endPoint.x, endPoint.y);
        } else {
            path.cubicTo(controllPoint1.x, controllPoint1.y, controllPoint2.x, controllPoint2.y, endPoint.x, endPoint.y);
        }
        final PathMeasure pathMeasure = new PathMeasure(path, false);

        moveAnim = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        moveAnim.setDuration(duration);
        moveAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float distance = (float) animation.getAnimatedValue();
                float[] pos = new float[2];
                pathMeasure.getPosTan(distance, pos, null);
                point.x = (int) pos[0];
                point.y = (int) pos[1];
            }
        });
        moveAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isEnd = true;
            }
        });
        moveAnim.start();
        zoomAnim = ValueAnimator.ofFloat(0, 1f).setDuration(duration / 2);
        zoomAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sf = (float) animation.getAnimatedValue();
                alpha = (int) (sf * 255);
            }
        });
        zoomAnim.start();
    }

//    public void pause(){
//        if(moveAnim !=null&& moveAnim.isRunning()){
//            moveAnim.pause();
//        }
//        if(zoomAnim !=null&& zoomAnim.isRunning()){
//            zoomAnim.pause();
//        }
//    }
//
//    public void resume(){
//        if(moveAnim !=null&& moveAnim.isPaused()){
//            moveAnim.resume();
//        }
//        if(zoomAnim !=null&& zoomAnim.isPaused()){
//            zoomAnim.resume();
//        }
//    }

    public void stop() {
        if (moveAnim != null) {
            moveAnim.cancel();
            moveAnim = null;
        }
        if (zoomAnim != null) {
            zoomAnim.cancel();
            zoomAnim = null;
        }
    }

    /**
     * 主要绘制函数
     */
    public void draw(Canvas canvas, Paint p) {
        if (bitmap != null && canvas != null && p != null) {
            p.setAlpha(alpha);
            matrix.setScale(sf, sf, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            matrix.postTranslate(point.x - bitmap.getWidth() / 2, point.y - bitmap.getHeight() / 2);
            canvas.drawBitmap(bitmap, matrix, p);
        }
    }

}
