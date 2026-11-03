package io.github.jason13official.more_beautiful_torches_bop.platform;

import io.github.jason13official.more_beautiful_torches_bop.Constants;
import io.github.jason13official.more_beautiful_torches_bop.platform.services.IPlatformHelper;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class Services {

  public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

  private static <T> T load(Class<T> clazz) {

    boolean inDevHelperLoaded = PLATFORM != null && PLATFORM.isDevelopmentEnvironment();

    if (inDevHelperLoaded) {
      Constants.LOG.info("Loading service {} on {}", clazz.getName(), PLATFORM.getPlatformName());
    }

    for (T helper : ServiceLoader.load(clazz)) {
      try {

        if (PLATFORM == null && (helper instanceof IPlatformHelper platform && platform.isDevelopmentEnvironment())) {
          Constants.LOG.info("Trying helper {} for service {} on {}", helper, clazz.getName(), platform.getPlatformName());
        }

        return helper;
      } catch (NoClassDefFoundError | ServiceConfigurationError e) {

        String s = e instanceof NoClassDefFoundError ? "helper definition not found; ignore this warning for merged mod files." : "helper service has malformed configuration.";
        Constants.LOG.info("Skipping {}, {}", helper.getClass().getName(), s);
      }
    }

    throw new IllegalStateException("Failed to load service for " + clazz.getName());
  }
}