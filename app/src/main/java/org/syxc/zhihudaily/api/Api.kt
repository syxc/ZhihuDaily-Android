package org.syxc.zhihudaily.api

import org.syxc.zhihudaily.DailyConfig
import timber.log.Timber

/**
 * Created by syxc on 15/12/15.
 */
enum class Api internal constructor(private val text: String) {

  /**
   * 启动界面图像获取
   */
  SplashScreen("/start-image/%s"),

  /**
   * 最新消息
   */
  LatestNews("/news/latest");

  fun url(): String {
    var HOST = DailyConfig.HOST
    if (DailyConfig.DEBUG) {
      HOST = DailyConfig.DEV_HOST
    }

    Timber.i("Api url: %s", HOST + text)

    return HOST + text
  }

  override fun toString(): String {
    return text
  }
}
