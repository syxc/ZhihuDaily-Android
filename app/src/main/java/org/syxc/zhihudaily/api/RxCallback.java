package org.syxc.zhihudaily.api;

import com.squareup.okhttp.Response;

/**
 * RxCallback
 * Created by syxc on 16/1/30.
 */
public interface RxCallback {
  /**
   * Success http response
   *
   * @param response com.squareup.okhttp.Response
   */
  void onSuccess(Response response);

  void onCompleted();

  /**
   * Fail http response
   *
   * @param e java.lang.Throwable
   */
  void onError(Throwable e);
}
