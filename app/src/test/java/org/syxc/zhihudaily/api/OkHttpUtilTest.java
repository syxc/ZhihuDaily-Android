package org.syxc.zhihudaily.api;

import com.squareup.okhttp.OkHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the OkHttpUtil client instance
 */
@RunWith(MockitoJUnitRunner.class) public class OkHttpUtilTest {

  @Test public void okHttpUtil_CheckInstance_ReturnsTrue() {
    for (int i = 0; i <= 10; i++) {
      OkHttpClient client1 = OkHttpUtil.getClient();
      OkHttpClient client2 = OkHttpUtil.getClient();
      assertThat("same instance", client1.equals(client2));
    }
  }
}
