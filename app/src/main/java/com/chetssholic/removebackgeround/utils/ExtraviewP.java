package com.chetssholic.removebackgeround.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.chetssholic.removebackgeround.R;


public class ExtraviewP extends AppCompatImageView implements View.OnTouchListener {
    public int a;
    public int b;
    public Bitmap c;
    public float e;
    public float f;
    public Paint i;
    public Bitmap m;
    public Matrix n;
    public float t;
    public float d = 0.0f;
    public int g = -1;
    private boolean valu;
    public float[] h = null;
    public float j = -1.0f;
    public float k = -1.0f;
    public Rect l = new Rect();
    public PointF o = new PointF();
    public int p = 0;
    public float q = 0.0f;
    public float r = 1.0f;
    public Matrix s = new Matrix();
    public PointF u = new PointF();
    public ImageView v = null;

    public ExtraviewP(Context context) {
        super(context);
        c(context);
    }


    public final void c(Context context) {
        setWillNotDraw(false);
        setLayerType(2, null);
        this.g = context.getResources().getColor(R.color.white);
        this.m = BitmapFactory.decodeResource(getResources(), R.drawable.drip_1);
        Paint paint = new Paint();
        this.i = paint;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        this.a = i;
        int i2 = displayMetrics.widthPixels;
        this.b = i2;
        this.j = i2 / 8;
        this.k = i / 8;
        Matrix matrix = new Matrix();
        this.n = matrix;
        matrix.postTranslate(this.j, this.k);
        this.n.postScale(1.0f, 0.3f, this.b / 4, this.a / 4);
        setOnTouchListener(this);
        invalidate();
    }

    public final void d(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    public final float e(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2(motionEvent.getY(0) - motionEvent.getY(1), motionEvent.getX(0) - motionEvent.getX(1)));
    }

    public final float g(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(this.g);
        float[] fArr = new float[9];
        this.n.getValues(fArr);
        float f = fArr[2];
        float f2 = fArr[5];
        float f3 = fArr[0];
        Bitmap bitmap = this.c;
        if (bitmap != null) {


            canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint());
        }
        canvas.setMatrix(this.n);
        if (this.j >= 0.0f && this.k >= 0.0f) {
            this.i.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;


            Bitmap bitmap1 = Bitmap.createScaledBitmap(m, width, 800, false);
            canvas.drawBitmap(bitmap1, f, f2, this.i);
        }
        this.i.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Rect rect = this.l;
        int i = this.b * 100;
        rect.set(0 - i, 0 - (this.a * 100), i, ((int) fArr[5]) + 2);
        canvas.drawRect(this.l, this.i);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (valu) {

            this.v = (ImageView) view;
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                Log.e("TAG", "onTouch: 111");
                this.s.set(this.n);
                this.u.set(motionEvent.getX(), motionEvent.getY());
                this.p = 1;
                this.h = null;
            } else if (action != 1) {
                Log.e("TAG", "onTouch: 222");
                if (action == 2) {

                    Log.e("TAG", "onTouch: 222_111");
                    int i = this.p;
                    if (i == 1) {
                        Log.e("TAG", "onTouch: 222_111_111");
                        this.n.set(this.s);
                         this.e = motionEvent.getX() - this.u.x;
                        float y = motionEvent.getY() - this.u.y;
                        this.f = y;
                        this.n.postTranslate(this.e, y);
                    } else if (i == 2) {


                        Log.e("TAG", "onTouch: 222_111_222");
                        float g = g(motionEvent);
                    if (g > 10.0f) {
                        Log.e("TAG", "onTouch: 222_111_222__111");
                        this.n.set(this.s);
                        float f = g / this.r;
                        this.t = f;
                        Matrix matrix = this.n;
                        PointF pointF = this.o;
                        matrix.postScale(f, f, pointF.x, pointF.y);
                    }
                    if ((this.h != null && motionEvent.getPointerCount() == 2) || motionEvent.getPointerCount() == 3) {

                        Log.e("TAG", "onTouch: 222_111_222__2222");
                        float e = e(motionEvent);
                        this.q = e;
                        float f2 = e - this.d;
                        float[] fArr = new float[9];
                        this.n.getValues(fArr);
                        float f3 = fArr[2];
                        float f4 = fArr[5];
                        float f5 = fArr[0];
                        this.n.postRotate(f2, f3 + ((this.v.getWidth() / 2) * f5), f4 + ((this.v.getHeight() / 2) * f5));
                    }


                    }


                } else if (action == 5) {
                    Log.e("TAG", "onTouch: 222_222");
                    float g2 = g(motionEvent);
                    this.r = g2;
                    if (g2 > 10.0f) {
                        this.s.set(this.n);
                        d(this.o, motionEvent);
                        this.p = 2;
                    }
                    float[] fArr2 = new float[4];
                    this.h = fArr2;
                    fArr2[0] = motionEvent.getX(0);
                    this.h[1] = motionEvent.getX(1);
                    this.h[2] = motionEvent.getY(0);
                    this.h[3] = motionEvent.getY(1);
                    this.d = e(motionEvent);
                } else if (action == 6) {
                    this.p = 0;
                    this.h = null;
                }
            }
            this.v.setImageMatrix(this.n);
            return true;
        } else {
            return false;
        }
    }

    public void setBitmap(int i) {
        setBackgroundColor(i);
        invalidate();
    }

    public void setColor(int i) {
        this.g = i;
        invalidate();
    }

    public void setMaskBitmap(Activity activity, Bitmap bitmap) {
        this.m = bitmap;
        invalidate();
    }

    public void setTouch(Boolean bool) {
        if (bool.booleanValue()) {
            setOnTouchListener(this);
        } else {
            setOnTouchListener(null);
        }
        invalidate();
    }

    public void setbgBitmap(Bitmap bitmap) {
        this.c = bitmap;
        invalidate();
    }

    public void setval(boolean valu) {
        this.valu = valu;
        invalidate();
    }

    public void setbgColor(int i) {
        setBackgroundColor(i);
        invalidate();
    }

    public ExtraviewP(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c(context);
    }

    public ExtraviewP(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        c(context);
    }
}
