package io.github.jason13official.more_beautiful_torches_bop.datagen.client;

import io.github.jason13official.more_beautiful_torches_bop.Constants;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.registry.ModBlocks;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.registry.ModBlocks.TorchEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MBTLanguageProvider extends LanguageProvider {

  public MBTLanguageProvider(PackOutput output) {
    super(output, Constants.MOD_ID, "en_us");
  }

  @Override
  protected void addTranslations() {
    // add("itemGroup.moreBeautifulTorchesBOP", Constants.MOD_NAME);
    add("itemGroup.moreBeautifulTorchesBOP", "More Beautiful Torches: BOP");

    for (TorchEntry entry : ModBlocks.TORCHES) {
      addTorch(entry, "Torch", "Wall Torch");
    }
    for (TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      addTorch(entry, "Soul Torch", "Soul Wall Torch");
    }
    for (TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      addTorch(entry, "Redstone Torch", "Redstone Wall Torch");
    }
  }

  private void addTorch(TorchEntry entry, String standingSuffix, String wallSuffix) {
    String material = displayName(entry.material().path());
    String standingName = material + " " + standingSuffix;

    add(entry.standing(), standingName);

    add(blockKey(entry.wall()), material + " " + wallSuffix);

    add(itemKey(entry.name()), standingName);
  }

  private static String blockKey(Block block) {
    return "block." + Constants.MOD_ID + "." + BuiltInRegistries.BLOCK.getKey(block).getPath();
  }

  private static String itemKey(String name) {
    return "item." + Constants.MOD_ID + "." + name;
  }

  private static String displayName(String path) {
    StringBuilder result = new StringBuilder();
    for (String word : path.split("_")) {
      if (result.length() > 0) {
        result.append(' ');
      }
      result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
    }
    return result.toString();
  }
}
