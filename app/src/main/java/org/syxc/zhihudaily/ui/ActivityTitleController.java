package org.syxc.zhihudaily.ui;

import android.app.Activity;

/**
 * Created by syxc on 16/1/7.
 */
public class ActivityTitleController {
  private final Activity activity;

  public ActivityTitleController(Activity activity) {
    this.activity = activity;
  }

  public void setTitle(CharSequence title) {
    activity.setTitle(title);
  }
}
