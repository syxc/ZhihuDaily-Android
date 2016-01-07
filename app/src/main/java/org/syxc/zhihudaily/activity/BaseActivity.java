package org.syxc.zhihudaily.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import org.syxc.zhihudaily.DailyApplication;

/**
 * Created by syxc on 16/1/7.
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((DailyApplication) getApplication()).inject(this);
  }
}
