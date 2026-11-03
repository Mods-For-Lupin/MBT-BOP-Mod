package io.github.jason13official.more_beautiful_torches_bop;

import io.github.jason13official.more_beautiful_torches_bop.impl.common.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class MoreBeautifulTorchesBOPClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreBeautifulTorchesBOPClient.init();

    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      BlockRenderLayerMap.INSTANCE.putBlock(entry.standing(), RenderType.cutout());
      BlockRenderLayerMap.INSTANCE.putBlock(entry.wall(), RenderType.cutout());
    }

    for (ModBlocks.TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      BlockRenderLayerMap.INSTANCE.putBlock(entry.standing(), RenderType.cutout());
      BlockRenderLayerMap.INSTANCE.putBlock(entry.wall(), RenderType.cutout());
    }

    for (ModBlocks.TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      BlockRenderLayerMap.INSTANCE.putBlock(entry.standing(), RenderType.cutout());
      BlockRenderLayerMap.INSTANCE.putBlock(entry.wall(), RenderType.cutout());
    }
  }
}
