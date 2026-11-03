package io.github.jason13official.more_beautiful_torches_bop.datagen.client;

import io.github.jason13official.more_beautiful_torches_bop.datagen.server.MBTDatagenServer;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.data.event.GatherDataEvent.Client;

public class MBTDatagenClient {

  public static void init(Client event) {
    event.addProvider(new MBTTorchTextureProvider(
        event.getGenerator().getPackOutput(), event.getResourceManager(PackType.CLIENT_RESOURCES)));
    event.createProvider(MBTModelProvider::new);
    event.createProvider(MBTLanguageProvider::new);

    MBTDatagenServer.init(event);
  }
}
