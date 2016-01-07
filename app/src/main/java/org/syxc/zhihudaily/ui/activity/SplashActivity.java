package org.syxc.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import org.syxc.zhihudaily.R;
import org.syxc.zhihudaily.api.ApiClient;
import org.syxc.zhihudaily.api.Callback;
import org.syxc.zhihudaily.model.Splash;
import timber.log.Timber;

/**
 * SplashScreen
 * Created by syxc on 16/1/7.
 */
public class SplashActivity extends BaseActivity {

  ImageView mSplashImage;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    mSplashImage = (ImageView) findViewById(R.id.image_splash);
    loadData();
  }

  void loadData() {
    try {
      ApiClient.instance().fetchSplashScreen(null, new Callback<Splash>() {
        @Override public void onSuccess(final Splash splash) {
          Timber.i("data: %s", splash.toString());
          Glide.with(SplashActivity.this).load(splash.image).crossFade().into(mSplashImage);
        }

        @Override public void onCompleted() {
          final Handler handler = new Handler();
          handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
          }, 3000);
        }

        @Override public void onError(String error) {
          Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
