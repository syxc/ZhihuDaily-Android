package org.syxc.zhihudaily.api;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import org.syxc.zhihudaily.DailyConfig;
import org.syxc.zhihudaily.model.LatestNews;
import org.syxc.zhihudaily.model.Splash;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Http request layer
 * Created by syxc on 15/12/15.
 */
public final class ApiClient implements DailyApi {

  private static ApiClient instance = null;

  public static ApiClient instance() {
    if (instance == null) {
      synchronized (ApiClient.class) {
        if (instance == null) {
          instance = new ApiClient();
        }
      }
    }
    return instance;
  }

  OkHttpClient getClient() {
    return OkHttpUtil.getClient();
  }

  Request getRequest(String url) {
    return OkHttpUtil.createRequest(url);
  }

  public void destroy() {
    try {
      OkHttpUtil.destroy();
    } catch (Exception e) {
      if (DailyConfig.DEBUG) {
        e.printStackTrace();
      }
    }
  }

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
  @Override public void fetchSplashScreen(String resolution, Callback<Splash> callback)
    throws Exception {
    String url;
    if (resolution == null) {
      url = String.format(Api.SplashScreen.url(), "1080*1776");
    } else {
      url = String.format(Api.SplashScreen.url(), resolution);
    }

    RxOkHttp.request(getClient(), getRequest(url))
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<Response>() {
        @Override public void onCompleted() {
          callback.onCompleted();
        }

        @Override public void onError(Throwable e) {
          Timber.e(e.getMessage());
          callback.onError(e.getLocalizedMessage());
        }

        @Override public void onNext(Response response) {
          try {
            String data = response.body().string();
            Timber.i("fetchSplashScreen: %s", data);
            Splash splash = JSON.parseObject(data, Splash.class);
            callback.onSuccess(splash);
          } catch (IOException e) {
            e.printStackTrace();
          } finally {
            try {
              response.body().close();
            } catch (IOException e) {
              // ignore
            }
          }
        }
      });
  }

  @Override public void fetchLatestNews(Callback<LatestNews> callback) throws Exception {
    RxOkHttp.request(getClient(), getRequest(Api.LatestNews.url()))
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<Response>() {
        @Override public void onCompleted() {
          callback.onCompleted();
        }

        @Override public void onError(Throwable e) {
          Timber.e(e.getMessage());
          callback.onError(e.getLocalizedMessage());
        }

        @Override public void onNext(Response response) {
          try {
            String data = response.body().string();
            Timber.i("fetchLatestNews: %s", data);
            LatestNews latestNews = JSON.parseObject(data, LatestNews.class);
            callback.onSuccess(latestNews);
          } catch (IOException e) {
            e.printStackTrace();
          } finally {
            try {
              response.body().close();
            } catch (IOException e) {
              // ignore
            }
          }
        }
      });
  }
}
