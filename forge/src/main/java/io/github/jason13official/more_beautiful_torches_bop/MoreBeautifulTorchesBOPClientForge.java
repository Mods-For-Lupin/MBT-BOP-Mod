package io.github.jason13official.more_beautiful_torches_bop;

import io.github.jason13official.more_beautiful_torches_bop.platform.Services;
import java.util.function.Consumer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class MoreBeautifulTorchesBOPClientForge {

  public MoreBeautifulTorchesBOPClientForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> {

      if (Services.PLATFORM.isDevelopmentEnvironment()) {
        Constants.LOG.info("FMLClientSetupEvent firing.");
      }
      MoreBeautifulTorchesBOPClient.init();
    });
  }
}
