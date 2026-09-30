package io.github.jason13official.more_beautiful_torches_bop.impl.common.registry;

import io.github.jason13official.more_beautiful_torches_bop.Constants;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.block.RedstoneTorchBlockBase;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.block.RedstoneWallTorchBlockBase;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.block.TorchBlockBase;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.block.WallTorchBlockBase;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

  public static final String BOP = "biomesoplenty";

  public enum TorchKind {NORMAL, REDSTONE, SOUL}

  public record SourceMaterial(String path, String texture) {

    public ResourceLocation id() {
      return ResourceLocation.fromNamespaceAndPath(BOP, path);
    }

    public ResourceLocation textureLocation() {
      return ResourceLocation.fromNamespaceAndPath(BOP, "block/" + texture);
    }
  }

  public static final class TorchEntry {

    private final String name;
    private final TorchKind kind;
    private final Block standing;
    private final SourceMaterial material;
    private final Function<Block, Block> wallFactory;
    private Block wall;

    private TorchEntry(String name, TorchKind kind, Block standing, SourceMaterial material, Function<Block, Block> wallFactory) {
      this.name = name;
      this.kind = kind;
      this.standing = standing;
      this.material = material;
      this.wallFactory = wallFactory;
    }

    public String name() {
      return name;
    }

    public TorchKind kind() {
      return kind;
    }

    public Block standing() {
      return standing;
    }

    public SourceMaterial material() {
      return material;
    }

    public Block source() {
      return BuiltInRegistries.BLOCK.get(material.id());
    }

    public Block wall() {
      if (wall == null) {
        wall = wallFactory.apply(standing);
      }
      return wall;
    }
  }

  private static final List<SourceMaterial> MATERIALS = List.of(
      material("white_sand"), material("white_sandstone"), material("smooth_white_sandstone", "white_sandstone_top"),
      material("cut_white_sandstone"), material("chiseled_white_sandstone"),
      material("orange_sand"), material("orange_sandstone"), material("smooth_orange_sandstone", "orange_sandstone_top"),
      material("cut_orange_sandstone"), material("chiseled_orange_sandstone"),
      material("black_sand"), material("mossy_black_sand", "mossy_black_sand_top"), material("black_sandstone"),
      material("smooth_black_sandstone", "black_sandstone_top"), material("cut_black_sandstone"), material("chiseled_black_sandstone"),
      material("thermal_calcite"), material("thermal_calcite_vent"), material("dried_salt"),
      material("flesh"), material("porous_flesh"),
      material("brimstone"), material("brimstone_bricks"), material("chiseled_brimstone_bricks"),
      material("rose_quartz_block"), material("wispjelly"),
      material("algal_end_stone"), material("null_block"), material("anomaly"),
      material("toadstool_block"), material("glowshroom_block"), material("glowing_moss_block"),
      material("origin_grass_block", "origin_grass_block_top"),

      material("fir_log"), material("stripped_fir_log"), material("fir_planks"),
      material("pine_log"), material("stripped_pine_log"), material("pine_planks"),
      material("maple_log"), material("stripped_maple_log"), material("maple_planks"),
      material("redwood_log"), material("stripped_redwood_log"), material("redwood_planks"),
      material("mahogany_log"), material("stripped_mahogany_log"), material("mahogany_planks"),
      material("jacaranda_log"), material("stripped_jacaranda_log"), material("jacaranda_planks"),
      material("palm_log"), material("stripped_palm_log"), material("palm_planks"),
      material("willow_log"), material("stripped_willow_log"), material("willow_planks"),
      material("dead_log"), material("stripped_dead_log"), material("dead_planks"),
      material("magic_log"), material("stripped_magic_log"), material("magic_planks"),
      material("umbran_log"), material("stripped_umbran_log"), material("umbran_planks"),
      material("hellbark_log"), material("stripped_hellbark_log"), material("hellbark_planks"),
      material("empyreal_log"), material("stripped_empyreal_log"), material("empyreal_planks")
  );

  public static final List<TorchEntry> TORCHES = MATERIALS.stream().map(ModBlocks::torch).toList();
  public static final List<TorchEntry> REDSTONE_TORCHES = MATERIALS.stream().map(ModBlocks::redstoneTorch).toList();
  public static final List<TorchEntry> SOUL_TORCHES = MATERIALS.stream().map(ModBlocks::soulTorch).toList();

  private static SourceMaterial material(String path) {
    return new SourceMaterial(path, path);
  }

  private static SourceMaterial material(String path, String texture) {
    return new SourceMaterial(path, texture);
  }

  private static TorchEntry torch(SourceMaterial material) {
    String name = material.path() + "_torch";
    Block standing = new TorchBlockBase(ParticleTypes.FLAME, torchProperties(14));
    return new TorchEntry(name, TorchKind.NORMAL, standing, material,
        s -> new WallTorchBlockBase(ParticleTypes.FLAME, torchProperties(14).dropsLike(s)));
  }

  private static TorchEntry soulTorch(SourceMaterial material) {
    String name = material.path() + "_soul_torch";
    Block standing = new TorchBlockBase(ParticleTypes.SOUL_FIRE_FLAME, torchProperties(10));
    return new TorchEntry(name, TorchKind.SOUL, standing, material,
        s -> new WallTorchBlockBase(ParticleTypes.SOUL_FIRE_FLAME, torchProperties(10).dropsLike(s)));
  }

  private static TorchEntry redstoneTorch(SourceMaterial material) {
    String name = material.path() + "_redstone_torch";
    Block standing = new RedstoneTorchBlockBase(redstoneTorchProperties());
    return new TorchEntry(name, TorchKind.REDSTONE, standing, material,
        s -> new RedstoneWallTorchBlockBase(redstoneTorchProperties().dropsLike(s)));
  }

  private static BlockBehaviour.Properties torchProperties(int lightLevel) {
    return BlockBehaviour.Properties.of()
        .noCollission()
        .instabreak()
        .lightLevel(state -> lightLevel)
        .sound(SoundType.WOOD);
  }

  private static BlockBehaviour.Properties redstoneTorchProperties() {
    return BlockBehaviour.Properties.of()
        .noCollission()
        .instabreak()
        .lightLevel(state -> state.getValue(RedstoneTorchBlock.LIT) ? 7 : 0)
        .sound(SoundType.WOOD);
  }

  public static void register(BiConsumer<Block, ResourceLocation> consumer) {
    registerAll(TORCHES, consumer);
    registerAll(REDSTONE_TORCHES, consumer);
    registerAll(SOUL_TORCHES, consumer);
  }

  private static void registerAll(List<TorchEntry> entries, BiConsumer<Block, ResourceLocation> consumer) {
    for (TorchEntry entry : entries) {
      consumer.accept(entry.standing(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, entry.name()));
      consumer.accept(entry.wall(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, entry.name() + "_wall"));
    }
  }
}
