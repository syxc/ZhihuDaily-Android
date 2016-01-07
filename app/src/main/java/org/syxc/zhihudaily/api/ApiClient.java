package org.syxc.zhihudaily.api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import org.syxc.zhihudaily.DailyConfig;
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

  // Moshi
  private static final Moshi moshi;

  static {
    moshi = new Moshi.Builder().build();
  }

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
            // do nothing
          }

          @Override public void onError(Throwable e) {
            Timber.e(e.getMessage());
            callback.failure(e.getLocalizedMessage());
          }

          @Override public void onNext(Response response) {
            try {
              String data = response.body().string();
              Timber.i(data);
              JsonAdapter<Splash> jsonAdapter = moshi.adapter(Splash.class);
              callback.success(jsonAdapter.fromJson(data));
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

  @Override public void fetchLatestNews() throws Exception {

  }
}
