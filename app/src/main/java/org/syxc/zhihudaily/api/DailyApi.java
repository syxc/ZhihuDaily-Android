package org.syxc.zhihudaily.api;

import org.syxc.zhihudaily.model.LatestNews;
import org.syxc.zhihudaily.model.Splash;

/**
 * DailyApi
 * Created by syxc on 15/12/15.
 */
public interface DailyApi {

  /**
   * 获取启动图片信息
   *
   * 图像分辨率接受如下格式：
   * - 320*432
   * - 480*728
   * - 720*1184
   * - 1080*1776
   *
   * @param resolution 图像分辨率
   * @param callback Callback
   * @throws Exception
   */
  void fetchSplashScreen(String resolution, Callback<Splash> callback) throws Exception;

  /**
   * 获取最新消息
   *
   * @param callback Callback
   * @throws Exception
   */
  void fetchLatestNews(Callback<LatestNews> callback) throws Exception;
}
