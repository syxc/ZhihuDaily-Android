package org.syxc.zhihudaily;

import android.app.Application;
import android.util.Log;
import dagger.ObjectGraph;
import java.util.Collections;
import java.util.List;
import org.syxc.zhihudaily.api.ApiClient;
import timber.log.Timber;

/**
 * Created by syxc on 1/6/16.
 */
public final class DailyApplication extends Application {

  private ObjectGraph applicationGraph;

  @Override public void onCreate() {
    super.onCreate();

    applicationGraph = ObjectGraph.create(getModules().toArray());

    if (DailyConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      Timber.plant(new CrashReportingTree());
    }
  }

  @Override public void onTerminate() {
    super.onTerminate();
    ApiClient.instance().destroy();
  }

  /**
   * A list of modules to use for the application graph. Subclasses can override this method to
   * provide additional modules provided they call {@code super.getModules()}.
   */
  protected List<Object> getModules() {
    return Collections.singletonList(new AndroidModule(this));
  }

  public ObjectGraph getApplicationGraph() {
    return applicationGraph;
  }

  /**
   * CrashReportingTree
   * A tree which logs important information for crash reporting.
   */
  private static class CrashReportingTree extends Timber.Tree {
    @Override protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
        return;
      }

      FakeCrashLibrary.log(priority, tag, message);

      if (t != null) {
        if (priority == Log.ERROR) {
          FakeCrashLibrary.logError(t);
        } else if (priority == Log.WARN) {
          FakeCrashLibrary.logWarning(t);
        }
      }
    }
  }
}
