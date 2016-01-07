package org.syxc.zhihudaily.model;

/**
 * 启动界面图像
 * Created by syxc on 16/1/7.
 */
public final class Splash {

  public Splash() {
  }

  // 供显示的图片版权信息
  public String text;

  // 图像的 URL
  public String img;

  public Splash(String text, String img) {
    this.text = text;
    this.img = img;
  }

  @Override public String toString() {
    return String.format("text=%s,img=%s", text, img);
  }
}
