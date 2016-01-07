package org.syxc.zhihudaily;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.syxc.zhihudaily.ui.ActivityTitleController;
import org.syxc.zhihudaily.ui.activity.BaseActivity;
import org.syxc.zhihudaily.ui.activity.MainActivity;
import org.syxc.zhihudaily.ui.activity.SplashActivity;

@Module(
    injects = {
        SplashActivity.class,
        MainActivity.class,
    },
    addsTo = AndroidModule.class,
    library = true
)
public class ActivityModule {
  private final BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  /**
   * Allow the activity context to be injected but require that it be annotated with
   * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
   */
  @Provides @Singleton @ForActivity Context provideActivityContext() {
    return activity;
  }

  @Provides @Singleton ActivityTitleController provideTitleController() {
    return new ActivityTitleController(activity);
  }
}
