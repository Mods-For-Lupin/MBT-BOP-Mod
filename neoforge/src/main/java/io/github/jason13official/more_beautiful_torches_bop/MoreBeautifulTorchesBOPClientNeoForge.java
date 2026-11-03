package io.github.jason13official.more_beautiful_torches_bop;

import java.util.function.Consumer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class MoreBeautifulTorchesBOPClientNeoForge {

  public MoreBeautifulTorchesBOPClientNeoForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> MoreBeautifulTorchesBOPClient.init());
  }
}
