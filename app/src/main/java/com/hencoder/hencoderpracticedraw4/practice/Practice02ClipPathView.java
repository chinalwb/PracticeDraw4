package com.hencoder.hencoderpracticedraw4.practice;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.hencoder.hencoderpracticedraw4.R;

import java.security.PrivilegedAction;

public class Practice02ClipPathView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);
    Path path1 = new Path();
    Path path2 = new Path();
    int bw, bh;
    int bx = 200;
    int by = 400;
    int piSize = 150 * 2;

    final double PI = Math.PI;

    int[] ds = new int[] {0, 10, 20, 30, 45, 50, 60, 70, 80, 90, 100, 110, 120, 130, 135, 140, 150, 160, 170, 180,
            190, 200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300, 310, 320, 330, 340, 350, 360};
    Point[] points = new Point[ds.length];
    int degreeIndex = 0;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degreeIndex", 0, ds.length - 1);

    public Practice02ClipPathView(Context context) {
        super(context);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        path1.moveTo(bx, by);
        path1.rLineTo(piSize, 0);
        path1.rLineTo(piSize, 0);


        path2.moveTo(bx, by);
        for (int i = 0; i < ds.length; i++) {
            double d = ds[i];
            double angle = (d / 180) * PI;
            double dx = d / 180 * piSize;
            double dy = Math.sin(angle) * piSize / 2;
            Point point = new Point((int) (bx + dx), (int) (by - dy));
            if (d == 360) {
                Log.e("x", "dx == " + dx + ", dy == " + dy);
            }
            points[i] = point;
            path2.lineTo(point.x, point.y);
        }

        animator.setStartDelay(1000);
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    public void setDegreeIndex(int degreeIndex) {
        this.degreeIndex = degreeIndex;
        invalidate();
    }

    @TargetApi(21)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        canvas.drawPath(path1, paint);


        paint.setColor(Color.BLUE);
        canvas.drawPath(path2, paint);

        paint.setColor(Color.CYAN);
        canvas.drawCircle(points[degreeIndex].x, points[degreeIndex].y, 12, paint);

        paint.setColor(Color.RED);
        canvas.drawCircle(bx, by, 8, paint);
        canvas.drawCircle(bx + piSize, by, 8, paint);
        canvas.drawCircle(bx + piSize * 2, by, 8, paint);
    }
}
