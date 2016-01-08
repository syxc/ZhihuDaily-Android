package org.syxc.zhihudaily.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 启动界面图像
 * Created by syxc on 16/1/7.
 */
public final class Splash {

  // 供显示的图片版权信息
  public String text;

  // 图像的 URL
  @JSONField(name = "img")
  public String image;

  public Splash() {
  }

  @Override public String toString() {
    return String.format("text=%s,image=%s", text, image);
  }
}
