package org.syxc.zhihudaily.api;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

  /* -------------------- HTTP Methods -------------------- */

  /**
   * HTTP GET
   *
   * @param url url
   * @param param params
   * @param callback RxCallback
   */
  void doGet(String url, HashMap<String, String> param, RxCallback callback) {
    String finalURL = url;
    if (param != null && param.size() > 0) {
      String strParam = "?";
      final int len = param.size();
      int index = 0;
      for (Map.Entry<String, String> entry : param.entrySet()) {
        index++;
        String key = entry.getKey();
        String value = entry.getValue();
        if (index == len) {
          strParam += key + "=" + value;
        } else {
          strParam += key + "=" + value + "&";
        }
      }
      finalURL += strParam;
    }

    RxOkHttp.request(getClient(), getRequest(finalURL))
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<Response>() {
        @Override public void onCompleted() {
          callback.onCompleted();
        }

        @Override public void onError(Throwable e) {
          callback.onError(e);
        }

        @Override public void onNext(Response response) {
          callback.onSuccess(response);
        }
      });
  }

  /**
   * HTTP POST
   *
   * @param url url
   * @param param params
   * @param callback RxCallback
   */
  void doPost(String url, HashMap<String, String> param, RxCallback callback) {
    FormEncodingBuilder formBuilder = new FormEncodingBuilder();
    RequestBody formBody = null;
    if (param != null && param.size() > 0) {
      final int len = param.size();
      int index = 0;
      for (Map.Entry<String, String> entry : param.entrySet()) {
        index++;
        String key = entry.getKey();
        String value = entry.getValue();
        if (index == len) {
          formBody = formBuilder.add(key, value).build();
        } else {
          formBuilder.add(key, value);
        }
      }
    } else {
      formBody = formBuilder.build();
    }

    Request request = new Request.Builder().url(url).post(formBody).build();

    RxOkHttp.request(getClient(), request)
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<Response>() {
        @Override public void onCompleted() {
          callback.onCompleted();
        }

        @Override public void onError(Throwable e) {
          callback.onError(e);
        }

        @Override public void onNext(Response response) {
          callback.onSuccess(response);
        }
      });
  }

  /* -------------------- API methods -------------------- */

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

    doGet(url, null, new RxCallback() {
      @Override public void onSuccess(Response response) {
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

      @Override public void onCompleted() {
        callback.onCompleted();
      }

      @Override public void onError(Throwable e) {
        Timber.e(e.getMessage());
        callback.onError(e.getLocalizedMessage());
      }
    });
  }

  @Override public void fetchLatestNews(Callback<LatestNews> callback) throws Exception {
    doGet(Api.LatestNews.url(), null, new RxCallback() {
      @Override public void onSuccess(Response response) {
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

      @Override public void onCompleted() {
        callback.onCompleted();
      }

      @Override public void onError(Throwable e) {
        Timber.e(e.getMessage());
        callback.onError(e.getLocalizedMessage());
      }
    });
  }

  @Override public void fetchTestPost(String search, final Callback<String> callback)
    throws Exception {
    final String url = "https://en.wikipedia.org/w/index.php";
    HashMap<String, String> param = new HashMap<>();
    param.put("search", search);
    doPost(url, param, new RxCallback() {
      @Override public void onSuccess(Response response) {
        try {
          String data = response.body().string();
          Timber.i(data);
          callback.onSuccess(data);
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

      @Override public void onCompleted() {
        callback.onCompleted();
      }

      @Override public void onError(Throwable e) {
        Timber.e(e.getMessage());
        callback.onError(e.getLocalizedMessage());
      }
    });
  }
}
