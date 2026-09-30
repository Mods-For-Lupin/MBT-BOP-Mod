package io.github.jason13official.more_beautiful_torches_bop.datagen.server;

import io.github.jason13official.more_beautiful_torches_bop.impl.common.registry.ModBlocks;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;

public class MBTBlockLootProvider extends BlockLootSubProvider {

  public MBTBlockLootProvider(HolderLookup.Provider registries) {
    super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
  }

  @Override
  protected void generate() {
    for (ModBlocks.TorchEntry entry : ModBlocks.TORCHES) {
      this.dropSelf(entry.standing());
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      this.dropSelf(entry.standing());
    }
    for (ModBlocks.TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      this.dropSelf(entry.standing());
    }
  }

  @Override
  public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
    this.generate();
    this.map.forEach(output);
  }
}
