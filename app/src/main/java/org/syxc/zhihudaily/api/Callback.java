package org.syxc.zhihudaily.api;

/**
 * Created by syxc on 15/12/15.
 */
public interface Callback<T> {

  /**
   * Success http response
   */
  void onSuccess(T t);

  void onCompleted();

  /**
   * Fail http response
   */
  void onError(String error);
}
