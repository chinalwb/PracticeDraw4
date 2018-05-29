package com.hencoder.hencoderpracticedraw4.practice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.CursorAnchorInfo;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice14FlipboardView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degree;
    int rotate;

    //Y轴方向旋转角度
    private float degreeY = -45;
    //不变的那一半，Y轴方向旋转角度
    private float fixDegreeY;
    //Z轴方向（平面内）旋转的角度
    private float degreeZ;

//    ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "degreeY", 0, -45);
//    {
//        animator1.setDuration(1000);
//        animator1.setStartDelay(500);
//    }

    ObjectAnimator animator2 = ObjectAnimator.ofFloat(this, "degreeZ", 0, 270);
    {
        animator2.setDuration(8000);
        animator2.setStartDelay(500);
    }

//    ObjectAnimator animator3 = ObjectAnimator.ofFloat(this, "fixDegreeY", 0, 30);
//    {
//        animator3.setDuration(500);
//        animator3.setStartDelay(500);
//    }

    final AnimatorSet set = new AnimatorSet();
    {
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                        set.start();
                    }
                }, 1000);
            }
        });
//        set.playSequentially(animator1, animator2, animator3);
        set.play(animator2);
        set.start();

        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(Color.RED);
    }


    public Practice14FlipboardView(Context context) {
        super(context);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

//        animator1.setDuration(10000);
//        animator1.setInterpolator(new LinearInterpolator());
//        animator1.setRepeatCount(ValueAnimator.INFINITE);
////        animator1.setRepeatMode(ValueAnimator.REVERSE);
//
//        animator1.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                // ++repeatCount;
//            }
//        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator2.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator2.end();
    }

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
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

        //画变换的一半
        //先旋转，再裁切，再使用camera执行3D动效,**然后保存camera效果**,最后再旋转回来
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        camera.rotateY(degreeY);
        camera.applyToCanvas(canvas);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(0, -centerY, centerX, centerY);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        //画不变换的另一半
        canvas.save();
//        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        //此时的canvas的坐标系已经旋转，所以这里是rotateY
//        camera.rotateY(fixDegreeY);
//        camera.applyToCanvas(canvas);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
//        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }


    /**
     * 启动动画之前调用，把参数reset到初始状态
     */
    public void reset() {
        degreeY = -45;
        fixDegreeY = 0;
        degreeZ = 0;
    }

    @Keep
    public void setFixDegreeY(float fixDegreeY) {
        this.fixDegreeY = fixDegreeY;
        invalidate();
    }

    @Keep
    public void setDegreeY(float degreeY) {
        this.degreeY = degreeY;
        invalidate();
    }

    @Keep
    public void setDegreeZ(float degreeZ) {
        this.degreeZ = degreeZ;
        invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }
}
