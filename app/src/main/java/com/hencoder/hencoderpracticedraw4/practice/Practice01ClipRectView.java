package com.hencoder.hencoderpracticedraw4.practice;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice01ClipRectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degreeY;
    int rotate;
    int degreeYY;
    ObjectAnimator animator1 = ObjectAnimator.ofInt(this, "degreeY", 0, -45);
    ObjectAnimator animator2 = ObjectAnimator.ofInt(this, "rotate", 0, 270);
    ObjectAnimator animator3 = ObjectAnimator.ofInt(this, "degreeYY", 0, 45);
    AnimatorSet set = new AnimatorSet();

    public Practice01ClipRectView(Context context) {
        super(context);
    }

    public Practice01ClipRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice01ClipRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

        animator1.setDuration(600);
        animator1.setInterpolator(new LinearInterpolator());

        animator2.setDuration(1000);
        animator2.setInterpolator(new DecelerateInterpolator());

        animator3.setDuration(600);
        animator3.setStartDelay(500);
//        animator3.setInterpolator(new LinearInterpolator());

        set.playSequentially(animator1, animator2, animator3);
        set.start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                        set.start();
                    }
                }, 1000);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // ++repeatCount;
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        set.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        set.end();
    }

    @SuppressWarnings("unused")
    public void setDegreeY(int degree) {
        this.degreeY = degree;
        invalidate();
    }

    @SuppressWarnings("unused")
    public void setDegreeYY(int degree) {
        this.degreeYY = degree;
        invalidate();
    }

    public void setRotate(int r) {
        this.rotate = r;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;


        // **** 波浪部分绘制 ****
        canvas.save();
        // 按画布中心旋转
        canvas.rotate(-rotate, centerX, centerY);
        // 截取旋转之后的右半部分
        canvas.clipRect(centerX, 0, getWidth(), getHeight());

        // 3D旋转设定
        camera.save();
        canvas.translate(centerX, centerY);
        camera.rotateY(this.degreeY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        // 应用3D完成

        // 按画布中心旋转
        canvas.rotate(rotate, centerX, centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
        // **** 波浪部分绘制完成 ****








        // **** 跟波浪部分互补的绘制 ****
        canvas.save();
        // 按画布中心旋转
        canvas.rotate(-rotate, centerX, centerY);
        // 截取画布的左半部分
        canvas.clipRect(0, 0, centerX, getHeight());


        // 最后一步3D旋转设定
        camera.save();
        canvas.translate(centerX, centerY);
        camera.rotateY(this.degreeYY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();


        // 按画布中心回旋
        canvas.rotate(rotate, centerX, centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
        // **** 波浪互补部分绘制完成 ****

    }

    private void reset() {
        this.rotate = 0;
        this.degreeY = 0;
        this.degreeYY = 0;
    }
}
