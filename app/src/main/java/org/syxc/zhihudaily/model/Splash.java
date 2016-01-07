package org.syxc.zhihudaily.model;

/**
 * 启动界面图像
 * Created by syxc on 16/1/7.
 */
public final class Splash {

  // 供显示的图片版权信息
  public final String text;

  // 图像的 URL
  public final String image;

  public Splash(String text, String image) {
    this.text = text;
    this.image = image;
  }

  @Override public String toString() {
    return String.format("text=%s,image=%s", text, image);
  }
}
