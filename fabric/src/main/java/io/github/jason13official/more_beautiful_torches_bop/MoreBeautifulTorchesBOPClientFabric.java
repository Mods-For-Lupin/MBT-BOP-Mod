package io.github.jason13official.more_beautiful_torches_bop;

import net.fabricmc.api.ClientModInitializer;

public class MoreBeautifulTorchesBOPClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreBeautifulTorchesBOPClient.init();
  }
}
