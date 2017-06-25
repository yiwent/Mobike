package com.yiwen.mobike.views.fllowerAnimation;

import android.graphics.Bitmap;
import android.graphics.Path;
import java.io.Serializable;

public class Flower
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Bitmap image;
  private Path path;
  private float value;
  private float x;
  private float y;

  public Path getPath()
  {
    return this.path;
  }

  public Bitmap getResId()
  {
    return this.image;
  }

  public float getValue()
  {
    return this.value;
  }

  public float getX()
  {
    return this.x;
  }

  public float getY()
  {
    return this.y;
  }

  public void setPath(Path paramPath)
  {
    this.path = paramPath;
  }

  public void setResId(Bitmap paramBitmap)
  {
    this.image = paramBitmap;
  }

  public void setValue(float paramFloat)
  {
    this.value = paramFloat;
  }

  public void setX(float paramFloat)
  {
    this.x = paramFloat;
  }

  public void setY(float paramFloat)
  {
    this.y = paramFloat;
  }

  public String toString()
  {
    return "Fllower [ x=" + this.x + ", y=" + this.y + ", path=" + this.path + ", value=" + this.value + "]";
  }
}
