package org.syxc.zhihudaily.api;

/**
 * Json transform layer
 * Created by syxc on 1/6/16.
 */
public interface Jsoner<T> {
  T fromJson(T t, String jsonRaw);
}
