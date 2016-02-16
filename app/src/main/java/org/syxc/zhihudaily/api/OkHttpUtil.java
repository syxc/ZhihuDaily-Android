package org.syxc.zhihudaily.api;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import java.util.concurrent.TimeUnit;
import org.syxc.zhihudaily.DailyConfig;
import timber.log.Timber;

/**
 * Created by syxc on 1/6/16.
 */
public final class OkHttpUtil {

  private static final OkHttpClient client;

  static {
    client = new OkHttpClient();
    if (DailyConfig.INSTANCE.getDEBUG()) {
      // HttpLoggingInterceptor
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      client.interceptors().add(logging);
    }
  }

  public static OkHttpClient getClient() {
    Timber.i("OkHttpClient instance: %s", client);
    return client;
  }

  /**
   * Sets the underlying read timeout in milliseconds.
   * A value of 0 specifies an infinite timeout.
   *
   * @see OkHttpClient#setReadTimeout(long, TimeUnit)
   */
  public static void setReadTimeout(int readTimeout) {
    client.setReadTimeout(readTimeout, TimeUnit.MILLISECONDS);
  }

  /**
   * Sets the underlying write timeout in milliseconds.
   * A value of 0 specifies an infinite timeout.
   *
   * @see OkHttpClient#setWriteTimeout(long, TimeUnit)
   */
  public static void setWriteTimeout(int writeTimeout) {
    client.setWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS);
  }

  /**
   * Sets the underlying connect timeout in milliseconds.
   * A value of 0 specifies an infinite timeout.
   *
   * @see OkHttpClient#setConnectTimeout(long, TimeUnit)
   */
  public static void setConnectTimeout(int connectTimeout) {
    client.setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
  }

  public static Request createRequest(String url) {
    return createRequestInternal(url);
  }

  private static Request createRequestInternal(String url) {
    return new Request.Builder().url(url).build();
  }

  /**
   * OkHttpClient shutdown
   *
   * @throws Exception
   */
  public static void destroy() throws Exception {
    // Clean up the client if we created it in the constructor
    if (client.getCache() != null) {
      client.getCache().close();
    }
    client.getDispatcher().getExecutorService().shutdown();
  }
}
