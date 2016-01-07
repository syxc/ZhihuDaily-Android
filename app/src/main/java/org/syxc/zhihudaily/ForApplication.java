package org.syxc.zhihudaily;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by syxc on 16/1/7.
 */
@Qualifier @Retention(RUNTIME)
public @interface ForApplication {
}
