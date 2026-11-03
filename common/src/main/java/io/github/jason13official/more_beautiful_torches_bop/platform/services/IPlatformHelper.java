package io.github.jason13official.more_beautiful_torches_bop.platform.services;

import java.nio.file.Path;
import net.minecraft.world.item.CreativeModeTab;

public interface IPlatformHelper {

    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {

    return isDevelopmentEnvironment() ? "development" : "production";
  }

  Path getGameDirectory();

  default Path getConfigDirectory() {

    return getGameDirectory().resolve("config");
  }

  CreativeModeTab.Builder tabBuilder();
}