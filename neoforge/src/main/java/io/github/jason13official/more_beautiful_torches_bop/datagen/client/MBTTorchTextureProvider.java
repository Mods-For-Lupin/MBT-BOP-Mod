package io.github.jason13official.more_beautiful_torches_bop.datagen.client;

import com.google.common.hash.Hashing;
import io.github.jason13official.more_beautiful_torches_bop.Constants;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.registry.ModBlocks;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.registry.ModBlocks.TorchEntry;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MBTTorchTextureProvider implements DataProvider {

  private static final ResourceLocation TORCH = ResourceLocation.withDefaultNamespace("block/torch");
  private static final ResourceLocation SOUL_TORCH = ResourceLocation.withDefaultNamespace("block/soul_torch");
  private static final ResourceLocation REDSTONE_TORCH = ResourceLocation.withDefaultNamespace("block/redstone_torch");
  private static final ResourceLocation REDSTONE_TORCH_OFF = ResourceLocation.withDefaultNamespace("block/redstone_torch_off");

  private final PackOutput.PathProvider textures;
  private final ExistingFileHelper existingFileHelper;

  public MBTTorchTextureProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    this.textures = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "textures/block");
    this.existingFileHelper = existingFileHelper;
  }

  @Override
  public CompletableFuture<?> run(CachedOutput output) {
    for (TorchEntry entry : ModBlocks.TORCHES) {
      writeTexture(output, entry.name(), TORCH, 8, entry.material().textureLocation());
    }
    for (TorchEntry entry : ModBlocks.SOUL_TORCHES) {
      writeTexture(output, entry.name(), SOUL_TORCH, 8, entry.material().textureLocation());
    }
    for (TorchEntry entry : ModBlocks.REDSTONE_TORCHES) {
      writeTexture(output, entry.name(), REDSTONE_TORCH, 9, entry.material().textureLocation());
      writeTexture(output, entry.name() + "_off", REDSTONE_TORCH_OFF, 8, entry.material().textureLocation());
    }
    return CompletableFuture.completedFuture(null);
  }

  private void writeTexture(CachedOutput output, String textureName, ResourceLocation mask, int stickRowStart, ResourceLocation sourceTexture) {
    try {
      BufferedImage maskImage = readTexture(mask);
      BufferedImage sourceImage = readTexture(sourceTexture);
      byte[] png = toPng(composite(maskImage, sourceImage, stickRowStart));

      ResourceLocation textureLocation = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, textureName);
      output.writeIfNeeded(textures.file(textureLocation, "png"), png, Hashing.sha1().hashBytes(png));

      existingFileHelper.trackGenerated(textureLocation.withPrefix("block/"), PackType.CLIENT_RESOURCES, ".png", "textures");
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static BufferedImage composite(BufferedImage mask, BufferedImage source, int stickRowStart) {
    BufferedImage result = new BufferedImage(mask.getWidth(), mask.getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < mask.getHeight(); y++) {
      for (int x = 0; x < mask.getWidth(); x++) {
        int maskPixel = mask.getRGB(x, y);
        boolean isStick = (maskPixel >>> 24) != 0 && y >= stickRowStart;
        result.setRGB(x, y, isStick ? source.getRGB(x, y) : maskPixel);
      }
    }
    return result;
  }

  private BufferedImage readTexture(ResourceLocation blockTexture) throws IOException {
    Resource resource = existingFileHelper.getResource(blockTexture, PackType.CLIENT_RESOURCES, ".png", "textures");
    try (InputStream stream = resource.open()) {
      return ImageIO.read(stream);
    } catch (FileNotFoundException e) {
      throw new IOException("Could not find texture " + blockTexture, e);
    }
  }

  private static byte[] toPng(BufferedImage image) throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ImageIO.write(image, "png", bytes);
    return bytes.toByteArray();
  }

  @Override
  public String getName() {
    return "More Beautiful Torches: Biomes O' Plenty Textures";
  }
}
