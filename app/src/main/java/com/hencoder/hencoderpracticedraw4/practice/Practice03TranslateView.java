package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice03TranslateView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(0, 0);
    Point point2 = new Point(600, 200);

    public Practice03TranslateView(Context context) {
        super(context);
    }

    public Practice03TranslateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice03TranslateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(40);
        paint.setColor(Color.GREEN);
        canvas.drawPoint(0, 0, paint);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(0);
        canvas.drawLine(0, 0, 100, 100, paint);
        paint.setAlpha(50);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setAlpha(255);

        canvas.save();
        canvas.translate(100, 100);
        paint.setStrokeWidth(20);
        paint.setColor(Color.RED);
        canvas.drawPoint(0, 0, paint);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(200, 100);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}