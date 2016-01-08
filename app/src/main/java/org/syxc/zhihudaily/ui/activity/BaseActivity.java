package org.syxc.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import dagger.ObjectGraph;
import java.util.Collections;
import java.util.List;
import org.syxc.zhihudaily.ActivityModule;
import org.syxc.zhihudaily.DailyApplication;

/**
 * Created by syxc on 16/1/7.
 */
public abstract class BaseActivity extends AppCompatActivity {
  private ObjectGraph activityGraph;

  private static boolean fullScreen = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    if (fullScreen) {
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
      fullScreen = false;
    }
    super.onCreate(savedInstanceState);
    // Create the activity graph by .plus-ing our modules onto the application graph.
    DailyApplication application = (DailyApplication) getApplication();
    activityGraph = application.getApplicationGraph().plus(getModules().toArray());

    // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
    activityGraph.inject(this);
  }

  @Override protected void onDestroy() {
    // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
    // soon as possible.
    activityGraph = null;
    super.onDestroy();
  }

  /**
   * A list of modules to use for the individual activity graph. Subclasses can override this
   * method to provide additional modules provided they call and include the modules returned by
   * calling {@code super.getModules()}.
   */
  protected List<Object> getModules() {
    return Collections.singletonList(new ActivityModule(this));
  }

  /** Inject the supplied {@code object} using the activity-specific graph. */
  public void inject(Object object) {
    activityGraph.inject(object);
  }

  /**
   * Wants fullScreen
   *
   * @return fullScreen
   */
  boolean wantsFullScreen() {
    return fullScreen = true;
  }

  /**
   * startActivity
   *
   * @param cls Target Activity class
   */
  void startActivity(Class<?> cls) {
    Intent intent = new Intent(this, cls);
    startActivity(intent);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }

  /** -------------------- abstract methods -------------------- */

  abstract void loadData();
}
