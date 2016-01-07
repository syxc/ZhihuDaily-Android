package org.syxc.zhihudaily.api;

/**
 * Created by syxc on 15/12/15.
 */
public interface Callback<T> {

  /**
   * Success http response
   * @param t
   */
  void success(T t);

  /**
   * Fail http response
   * @param error
   */
  void failure(String error);

}
