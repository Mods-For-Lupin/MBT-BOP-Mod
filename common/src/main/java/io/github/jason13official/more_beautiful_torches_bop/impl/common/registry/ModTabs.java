package io.github.jason13official.more_beautiful_torches_bop.impl.common.registry;

import io.github.jason13official.monolib.platform.Services;
import io.github.jason13official.more_beautiful_torches_bop.Constants;
import io.github.jason13official.more_beautiful_torches_bop.MoreBeautifulTorchesBOP;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {

  public static CreativeModeTab MORE_BEAUTIFUL_TORCHES;

  public static void register(BiConsumer<CreativeModeTab, ResourceLocation> consumer) {

    MORE_BEAUTIFUL_TORCHES = Services.registry().tabBuilder()
        .icon(() -> new ItemStack(ModBlocks.TORCHES.get(0).standing()))
        .title(Component.translatable("itemGroup.moreBeautifulTorchesBOP"))
        .displayItems(((itemDisplayParameters, output) -> {

          ModBlocks.TORCHES.forEach(torch -> output.accept(torch.standing()));
          ModBlocks.SOUL_TORCHES.forEach(torch -> output.accept(torch.standing()));
          ModBlocks.REDSTONE_TORCHES.forEach(torch -> output.accept(torch.standing()));
        })).build();

    consumer.accept(MORE_BEAUTIFUL_TORCHES, MoreBeautifulTorchesBOP.identifier(Constants.MOD_ID));
  }
}
