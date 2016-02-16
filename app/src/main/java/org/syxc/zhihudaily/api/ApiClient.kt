package org.syxc.zhihudaily.api

import com.alibaba.fastjson.JSON
import com.squareup.okhttp.*
import org.syxc.zhihudaily.DailyConfig
import org.syxc.zhihudaily.model.LatestNews
import org.syxc.zhihudaily.model.Splash
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.io.IOException
import java.util.*

/**
 * Http request layer
 * Created by syxc on 15/12/15.
 */
class ApiClient : DailyApi {

  internal val client: OkHttpClient
    get() = OkHttpUtil.getClient()

  internal fun getRequest(url: String): Request {
    return OkHttpUtil.createRequest(url)
  }

  fun destroy() {
    try {
      OkHttpUtil.destroy()
    } catch (e: Exception) {
      if (DailyConfig.DEBUG) {
        e.printStackTrace()
      }
    }

  }

  /* -------------------- HTTP Methods -------------------- */

  /**
   * HTTP GET

   * @param url url
   * *
   * @param param params
   * *
   * @param callback RxCallback
   */
  internal fun doGet(url: String, param: HashMap<String, String>?, callback: RxCallback) {
    var finalURL = url
    if (param != null && param.size > 0) {
      var strParam = "?"
      val len = param.size
      var index = 0
      for ((key, value) in param) {
        index++
        if (index == len) {
          strParam += key + "=" + value
        } else {
          strParam += "$key=$value&"
        }
      }
      finalURL += strParam
    }

    RxOkHttp.request(client, getRequest(finalURL)).subscribeOn(Schedulers.newThread()).observeOn(
      AndroidSchedulers.mainThread()).subscribe(object : Subscriber<Response>() {
      override fun onCompleted() {
        callback.onCompleted()
      }

      override fun onError(e: Throwable) {
        callback.onError(e)
      }

      override fun onNext(response: Response) {
        callback.onSuccess(response)
      }
    })
  }

  /**
   * HTTP POST

   * @param url url
   * *
   * @param param params
   * *
   * @param callback RxCallback
   */
  internal fun doPost(url: String, param: HashMap<String, String>?, callback: RxCallback) {
    val formBuilder = FormEncodingBuilder()
    var formBody: RequestBody? = null
    if (param != null && param.size > 0) {
      val len = param.size
      var index = 0
      for ((key, value) in param) {
        index++
        if (index == len) {
          formBody = formBuilder.add(key, value).build()
        } else {
          formBuilder.add(key, value)
        }
      }
    } else {
      formBody = formBuilder.build()
    }

    val request = Request.Builder().url(url).post(formBody).build()

    RxOkHttp.request(client, request).subscribeOn(Schedulers.newThread()).observeOn(
      AndroidSchedulers.mainThread()).subscribe(object : Subscriber<Response>() {
      override fun onCompleted() {
        callback.onCompleted()
      }

      override fun onError(e: Throwable) {
        callback.onError(e)
      }

      override fun onNext(response: Response) {
        callback.onSuccess(response)
      }
    })
  }

  /* -------------------- API methods -------------------- */

  /**
   * 获取启动图片信息

   * 图像分辨率接受如下格式：
   * - 320*432
   * - 480*728
   * - 720*1184
   * - 1080*1776

   * @param resolution 图像分辨率
   * *
   * @param callback Callback
   * *
   * @throws Exception
   */
  @Throws(Exception::class)
  override fun fetchSplashScreen(resolution: String?, callback: Callback<Splash>) {
    val url: String
    if (resolution == null) {
      url = String.format(Api.SplashScreen.url(), "1080*1776")
    } else {
      url = String.format(Api.SplashScreen.url(), resolution)
    }

    doGet(url, null, object : RxCallback {
      override fun onSuccess(response: Response) {
        try {
          val data = response.body().string()
          Timber.i("fetchSplashScreen: %s", data)
          val splash = JSON.parseObject(data, Splash::class.java)
          callback.onSuccess(splash)
        } catch (e: IOException) {
          e.printStackTrace()
        } finally {
          try {
            response.body().close()
          } catch (e: IOException) {
            // ignore
          }

        }
      }

      override fun onCompleted() {
        callback.onCompleted()
      }

      override fun onError(e: Throwable) {
        Timber.e(e.message)
        callback.onError(e.message)
      }
    })
  }

  @Throws(Exception::class)
  override fun fetchLatestNews(callback: Callback<LatestNews>) {
    doGet(Api.LatestNews.url(), null, object : RxCallback {
      override fun onSuccess(response: Response) {
        try {
          val data = response.body().string()
          Timber.i("fetchLatestNews: %s", data)
          val latestNews = JSON.parseObject(data, LatestNews::class.java)
          callback.onSuccess(latestNews)
        } catch (e: IOException) {
          e.printStackTrace()
        } finally {
          try {
            response.body().close()
          } catch (e: IOException) {
            // ignore
          }

        }
      }

      override fun onCompleted() {
        callback.onCompleted()
      }

      override fun onError(e: Throwable) {
        Timber.e(e.message)
        callback.onError(e.message)
      }
    })
  }

  @Throws(Exception::class)
  override fun fetchTestPost(search: String, callback: Callback<String>) {
    val url = "https://en.wikipedia.org/w/index.php"
    val param = HashMap<String, String>()
    param.put("search", search)
    doPost(url, param, object : RxCallback {
      override fun onSuccess(response: Response) {
        try {
          val data = response.body().string()
          Timber.i(data)
          callback.onSuccess(data)
        } catch (e: IOException) {
          e.printStackTrace()
        } finally {
          try {
            response.body().close()
          } catch (e: IOException) {
            // ignore
          }

        }
      }

      override fun onCompleted() {
        callback.onCompleted()
      }

      override fun onError(e: Throwable) {
        Timber.e(e.message)
        callback.onError(e.message)
      }
    })
  }

  companion object {

    private var instance: ApiClient? = null

    fun instance(): ApiClient {
      if (instance == null) {
        synchronized (ApiClient::class.java) {
          if (instance == null) {
            instance = ApiClient()
          }
        }
      }
      return instance!!
    }
  }
}
