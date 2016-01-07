package org.syxc.zhihudaily.api;

import org.syxc.zhihudaily.DailyConfig;
import timber.log.Timber;

/**
 * Created by syxc on 15/12/15.
 */
public enum Api {

  /**
   * 启动界面图像获取
   */
  SplashScreen("/start-image/%s"),

  /**
   * 最新消息
   */
  LatestNews("/news/latest");

  private final String text;

  Api(String text) {
    this.text = text;
  }

  public final String url() {
    String HOST = DailyConfig.HOST;
    if (DailyConfig.DEBUG) {
      HOST = DailyConfig.DEV_HOST;
    }

    Timber.i("Api url: %s", HOST + text);

    return HOST + text;
  }

  @Override public String toString() {
    return text;
  }
}
