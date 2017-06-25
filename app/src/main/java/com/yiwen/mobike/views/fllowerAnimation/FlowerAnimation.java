package com.yiwen.mobike.views.fllowerAnimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FlowerAnimation extends View
  implements AnimatorUpdateListener
{
  private static final Paint y = new Paint();
  int[] a;
  ObjectAnimator b;
  ObjectAnimator c;
  ObjectAnimator d;
  private float e = 0.0F;
  private float f = 0.0F;
  private float g = 0.0F;
  private List<Flower> h = new ArrayList();
  private List<Flower> i = new ArrayList();
  private List<Flower> j = new ArrayList();
  private int k = 1500;
  private int l = 200;
  private int m;
  private int n;
  private int o;
  private int p;
  private float q;
  private int r;
  private int s;
  private int t;
  private Paint u;
  private PathMeasure v;
  private float w;
  private String x;

  static
  {
    y.setStyle(Style.FILL);
  }

  public FlowerAnimation(Context paramContext)
  {
    super(paramContext);
    int[] arrayOfInt = new int[3];
//    arrayOfInt[0] = ContextCompat.getColor(getContext(), 2131624049);
//    arrayOfInt[1] = ContextCompat.getColor(getContext(), 2131624050);
//    arrayOfInt[2] = ContextCompat.getColor(getContext(), 2131624051);
    this.a = arrayOfInt;
    this.m = 8;
    this.n = 0;
    this.o = 0;
    this.p = 15;
    this.q = 0.2F;
    this.r = 28;
    this.s = 35;
    this.t = (int)TypedValue.applyDimension(1, 1.0F, getResources().getDisplayMetrics());
    this.v = null;
    this.w = TypedValue.applyDimension(1, 60.0F, getResources().getDisplayMetrics());
    this.x = getClass().getSimpleName();
    a(paramContext);
  }

  public static Bitmap a(int paramInt1, int paramInt2)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramInt2, paramInt2, Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    y.setColor(paramInt1);
    float f1 = paramInt2 / 2.0F;
    localCanvas.drawCircle(f1, f1, f1, y);
    return localBitmap;
  }

  private List<a> a(a parama)
  {
    ArrayList localArrayList = new ArrayList();
    Random localRandom = new Random();
    int i1 = 0;
    while (i1 < this.p)
    {
      if (i1 == 0)
      {
        localArrayList.add(parama);
        i1++;
        continue;
      }
      a locala = new a(0.0F, 0.0F);
      if (localRandom.nextInt(100) % 2 == 0)
        parama.a += localRandom.nextInt(this.t);
      while (true)
      {
        locala.b = (int)(this.o / this.p * i1);
        localArrayList.add(locala);
        break;
//        parama.getUrl -= localRandom.nextInt(this.t);
      }
    }
    return localArrayList;
  }

  private void a(float paramFloat, List<Flower> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      ((Flower)localIterator.next()).setValue(paramFloat);
  }

  private void a(int paramInt1, List<Flower> paramList, int paramInt2)
  {
//    (int)(3 * this.n / 4.0F);
    int i1 = this.n / paramInt1;
    int i2 = this.o / 2 / paramInt1;
    int i3 = (int)(3 * this.o / 4.0F);
    Random localRandom = new Random();
    for (int i4 = 0; i4 < paramInt1; i4++)
    {
      int i5 = i1 * (1 + localRandom.nextInt(this.r));
      Path localPath = new Path();
      int i6 = paramInt2 + (i2 + localRandom.nextInt(i3) % (1 + (this.o - i2))) / 2;
      float f1 = i5;
      if (i6 > i3 / 2)
        i6 = i3 / 2 - i4 * 4;
      a(localPath, a(new a(f1, -i6)));
      Flower localFlower = new Flower();
      localFlower.setPath(localPath);
      localFlower.setResId(c(this.a[localRandom.nextInt(3)], (2 + localRandom.nextInt(3)) * this.m));
      paramList.add(localFlower);
    }
  }

  private void a(Context paramContext)
  {
//    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
//    this.n = localWindowManager.getDefaultDisplay().getWidth();
//    this.o = (int)(2.0F * localWindowManager.getDefaultDisplay().getHeight());
    this.u = new Paint();
    this.u.setAntiAlias(true);
    this.v = new PathMeasure();
    a(this.r, this.h, 0);
    a(this.s - this.r, this.i, this.o / 4);
    a(this.s - this.r, this.j, this.o / 4);
  }

  private void a(Canvas paramCanvas, List<Flower> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Flower localFlower = (Flower)localIterator.next();
      float[] arrayOfFloat = new float[2];
      this.v.setPath(localFlower.getPath(), false);
      this.v.getPosTan(this.o * localFlower.getValue(), arrayOfFloat, null);
      paramCanvas.drawBitmap(localFlower.getResId(), arrayOfFloat[0], arrayOfFloat[1] - this.w, null);
    }
  }

  private void a(Path paramPath, List<a> paramList)
  {
    if (paramList.size() > 1)
    {
      int i1 = 0;
      if (i1 < paramList.size())
      {
        a locala1 = (a)paramList.get(i1);
        if (i1 == 0)
        {
          a locala6 = (a)paramList.get(i1 + 1);
          locala1.c = ((locala6.a - locala1.a) * this.q);
          locala1.d = ((locala6.b - locala1.b) * this.q);
          label94: if (i1 != 0)
//            break label262;
          paramPath.moveTo(locala1.a, locala1.b);
        }
        while (true)
        {
          i1++;
          break;
//          if (i1 == -1 + paramList.size())
//          {
//            getUrl locala5 = (getUrl)paramList.get(i1 - 1);
//            locala1.c = ((locala1.getUrl - locala5.getUrl) * this.q);
//            locala1.d = ((locala1.b - locala5.b) * this.q);
//            break label94;
//          }
//          getUrl locala2 = (getUrl)paramList.get(i1 + 1);
//          getUrl locala3 = (getUrl)paramList.get(i1 - 1);
//          locala1.c = ((locala2.getUrl - locala3.getUrl) * this.q);
//          locala1.d = ((locala2.b - locala3.b) * this.q);
//          break label94;
//          label262: getUrl locala4 = (getUrl)paramList.get(i1 - 1);
//          paramPath.cubicTo(locala4.getUrl + locala4.c, locala4.b + locala4.d, locala1.getUrl - locala1.c, locala1.b - locala1.d, locala1.getUrl, locala1.b);
        }
      }
    }
  }

  public static Bitmap b(int paramInt1, int paramInt2)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramInt2, paramInt2, Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    y.setColor(paramInt1);
    Path localPath = new Path();
    localPath.moveTo(0.0F, 0.0F);
    localPath.lineTo(paramInt2, 0.0F);
    localPath.lineTo(paramInt2, paramInt2);
    localPath.lineTo(0.0F, paramInt2);
    localPath.close();
    localCanvas.drawPath(localPath, y);
    return localBitmap;
  }

  public static Bitmap c(int paramInt1, int paramInt2)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramInt2, paramInt2, Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    y.setColor(paramInt1);
    Random localRandom = new Random();
    Path localPath = new Path();
    float f1 = (float)Math.tan(3.141592653589793D * ((10 + localRandom.nextInt(20)) / 180.0F)) * paramInt2;
    localPath.moveTo(0.0F, 0.0F);
    localPath.lineTo(paramInt2, f1);
    localPath.lineTo(f1, paramInt2);
    localPath.close();
    localCanvas.drawPath(localPath, y);
    return localBitmap;
  }

  public void a()
  {
    if ((this.b != null) && (this.b.isRunning()))
      this.b.cancel();
    this.b = ObjectAnimator.ofFloat(this, "phase1", new float[] { 0.0F, 1.0F });
    this.b.setDuration(3 * this.k / 2);
    this.b.addUpdateListener(this);
    this.b.setInterpolator(new AccelerateInterpolator());
    this.b.start();
    if ((this.c != null) && (this.c.isRunning()))
      this.c.cancel();
    this.c = ObjectAnimator.ofFloat(this, "phase2", new float[] { 0.0F, 1.0F });
    this.c.setDuration(2 * (this.k / 3));
    this.c.addUpdateListener(this);
    this.c.setStartDelay(2 * this.l);
    this.c.start();
    this.c.setInterpolator(new AccelerateDecelerateInterpolator());
    if ((this.d != null) && (this.d.isRunning()))
      this.d.cancel();
    this.d = ObjectAnimator.ofFloat(this, "phase3", new float[] { 0.0F, 1.0F });
    this.d.setDuration(this.k / 3);
    this.d.addUpdateListener(this);
    this.d.setInterpolator(new AccelerateInterpolator(2.0F));
    this.d.setStartDelay(4 * this.l);
    this.d.start();
  }

  public float getPhase1()
  {
    return this.e;
  }

  public float getPhase2()
  {
    return this.f;
  }

  public float getPhase3()
  {
    return this.g;
  }

  public void onAnimationUpdate(ValueAnimator paramValueAnimator)
  {
    a(getPhase1(), this.h);
    if ((this.c != null) && (this.c.isRunning()))
      a(getPhase2(), this.i);
    if ((this.d != null) && (this.d.isRunning()))
      a(getPhase3(), this.j);
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    a(paramCanvas, this.h);
    if ((this.c != null) && (this.c.isRunning()))
      a(paramCanvas, this.i);
    if ((this.d != null) && (this.d.isRunning()))
      a(paramCanvas, this.j);
  }

  public void setPhase1(float paramFloat)
  {
    this.e = paramFloat;
  }

  public void setPhase2(float paramFloat)
  {
    this.f = paramFloat;
  }

  public void setPhase3(float paramFloat)
  {
    this.g = paramFloat;
  }

  private class a
  {
    public float a = 0.0F;
    public float b = 0.0F;
    public float c = 0.0F;
    public float d = 0.0F;

    public a(float paramFloat1, float arg3)
    {
      this.a = paramFloat1;
      this.b = arg3;
    }
  }
}
