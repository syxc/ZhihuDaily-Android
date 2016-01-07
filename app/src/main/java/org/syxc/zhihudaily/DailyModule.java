package org.syxc.zhihudaily;

import dagger.Module;
import org.syxc.zhihudaily.activity.MainActivity;

@Module(
    injects = MainActivity.class,
    complete = false
)

public class DailyModule {
  // TODO put your application-specific providers here!
}
