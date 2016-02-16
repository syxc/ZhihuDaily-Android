package org.syxc.zhihudaily.api

/**
 * Callback
 * Created by syxc on 15/12/15.
 */
interface Callback<T> {

  /**
   * Success http response
   */
  fun onSuccess(t: T)

  fun onCompleted()

  /**
   * Fail http response
   */
  fun onError(error: String?)
}
