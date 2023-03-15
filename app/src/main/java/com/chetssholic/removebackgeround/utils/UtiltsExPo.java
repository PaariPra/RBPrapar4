package com.chetssholic.removebackgeround.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;

import com.chetssholic.removebackgeround.R;

public class UtiltsExPo extends AppCompatImageView implements View.OnTouchListener {
  public float a = 0.0F;
  
  public int b = -1;
  
  public int c = -1;
  
  public float[] d = null;
  
  public Paint e;
  
  public float f = -1.0F;
  
  public float g = -1.0F;
  
  public Paint h = new Paint();
  
  public Bitmap i;

  private  boolean valu=false;
  public Matrix j;
  
  public PointF k = new PointF();
  
  public int l = 0;
  
  public float m = 0.0F;
  
  public float n = 1.0F;
  
  public Matrix o = new Matrix();
  
  public PointF p = new PointF();
  
  public int q = -1;
  
  public ImageView r;
  
  public UtiltsExPo(Context paramContext) {
    super(paramContext);
    c(paramContext);
  }
  
  public UtiltsExPo(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    c(paramContext);
  }
  
  public UtiltsExPo(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    c(paramContext);
  }
  
  @SuppressLint({"WrongConstant"})
  public final void c(Context paramContext) {
    setWillNotDraw(false);
    setLayerType(2, null);
    Paint paint = new Paint();
    this.e = paint;
    paint.setColor(-1);
    this.e.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    this.i = BitmapFactory.decodeResource(getResources(), R.drawable.pot_1);
    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((Activity)getContext()).getWindowManager().getDefaultDisplay()
            .getMetrics(displayMetrics);
    int i = displayMetrics.heightPixels;
    this.f = (displayMetrics.widthPixels / 2);
    this.g = (i / 2);
    this.j = new Matrix();
    setOnTouchListener(this);
    invalidate();
  }
  
  public void d(Boolean paramBoolean) {
    if (paramBoolean.booleanValue()) {
      setOnTouchListener(this);
    } else {
      setOnTouchListener(null);
    } 
    invalidate();
  }
  
  public final void e(PointF paramPointF, MotionEvent paramMotionEvent) {
    paramPointF.set((paramMotionEvent.getX(0) + paramMotionEvent.getX(1)) / 2.0F, (paramMotionEvent.getY(0) + paramMotionEvent.getY(1)) / 2.0F);
  }
  
  public final float g(MotionEvent paramMotionEvent) {
    return (float)Math.toDegrees(Math.atan2((paramMotionEvent.getY(0) - paramMotionEvent.getY(1)), (paramMotionEvent.getX(0) - paramMotionEvent.getX(1))));
  }
  
  public final float i(MotionEvent paramMotionEvent) {
    float f1 = paramMotionEvent.getX(0) - paramMotionEvent.getX(1);
    float f2 = paramMotionEvent.getY(0) - paramMotionEvent.getY(1);
    return (float)Math.sqrt((f1 * f1 + f2 * f2));
  }
  
  public void onDraw(Canvas paramCanvas) {
    this.h.setShader((Shader)new LinearGradient(0.0F, 0.0F, 0.0F,
            getHeight(), this.q, this.b, Shader.TileMode.MIRROR));
    paramCanvas.drawPaint(this.h);
    if (this.f >= 0.0F && this.g >= 0.0F)
      paramCanvas.drawBitmap(this.i, this.j, this.e); 
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
    if (valu) {
      this.r = (ImageView) paramView;
      int i = paramMotionEvent.getAction() & 0xFF;
      if (i == 0) {
        this.o.set(this.j);
        this.p.set(paramMotionEvent.getX(), paramMotionEvent.getY());
        this.l = 1;
        this.d = null;
      } else {
        if (i == 1) {
          this.l = 0;
          this.d = null;
          this.r.setImageMatrix(this.j);
          return true;
        }
        if (i == 2) {
          i = this.l;
          if (i == 1) {
            this.j.set(this.o);
            float f1 = paramMotionEvent.getX();
            float f2 = this.p.x;
            float f3 = paramMotionEvent.getY();
            float f4 = this.p.y;
            this.j.postTranslate(f1 - f2, f3 - f4);
          } else if (i == 2) {
            float f = i(paramMotionEvent);
            if (f > 10.0F) {
              this.j.set(this.o);
              f /= this.n;
              Matrix matrix = this.j;
              PointF pointF = this.k;
              matrix.postScale(f, f, pointF.x, pointF.y);
            }
            if ((this.d != null && paramMotionEvent.getPointerCount() == 2) || paramMotionEvent.getPointerCount() == 3) {
              f = g(paramMotionEvent);
              this.m = f;
              float f1 = this.a;
              float[] arrayOfFloat = new float[9];
              this.j.getValues(arrayOfFloat);
              float f2 = arrayOfFloat[2];
              float f3 = arrayOfFloat[5];
              float f4 = arrayOfFloat[0];
              float f5 = (this.r.getWidth() / 2);
              float f6 = (this.r.getHeight() / 2);
              this.j.postRotate(f - f1, f2 + f5 * f4, f3 + f4 * f6);
            }
          }
        } else if (i == 5) {
          float f = i(paramMotionEvent);
          this.n = f;
          if (f > 10.0F) {
            this.o.set(this.j);
            e(this.k, paramMotionEvent);
            this.l = 2;
          }
          float[] arrayOfFloat = new float[4];
          this.d = arrayOfFloat;
          arrayOfFloat[0] = paramMotionEvent.getX(0);
          this.d[1] = paramMotionEvent.getX(1);
          this.d[2] = paramMotionEvent.getY(0);
          this.d[3] = paramMotionEvent.getY(1);
          this.a = g(paramMotionEvent);
        } else if (i == 6) {
          this.l = 0;
          this.d = null;
          this.r.setImageMatrix(this.j);
          return true;
        }
      }
      this.r.setImageMatrix(this.j);
      return true;
    }
    else
    {
      return  false;
    }
  }
  
  public void setBitmap(int paramInt) {
    setBackgroundColor(paramInt);
    invalidate();
  }
  public void setval(boolean valu) {
    this.valu = valu;
    invalidate();
  }
  
  public void setColor(int paramInt1, int paramInt2) {
    this.q = paramInt1;
    this.b = paramInt2;
    invalidate();
  }
  
  public void setMask(Bitmap paramBitmap) {
    this.i = paramBitmap;
    invalidate();
  }
  
  public void setbgColor(int paramInt) {
    setBackgroundColor(paramInt);
    invalidate();
  }
}
