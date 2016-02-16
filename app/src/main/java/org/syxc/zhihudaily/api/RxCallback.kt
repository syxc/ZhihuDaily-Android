package org.syxc.zhihudaily.api

import com.squareup.okhttp.Response

/**
 * RxCallback
 * Created by syxc on 16/1/30.
 */
interface RxCallback {
  /**
   * Success http response

   * @param response com.squareup.okhttp.Response
   */
  fun onSuccess(response: Response)

  fun onCompleted()

  /**
   * Fail http response

   * @param e java.lang.Throwable
   */
  fun onError(e: Throwable)
}
