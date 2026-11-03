package io.github.jason13official.more_beautiful_torches_bop.impl.common.registry;

import io.github.jason13official.more_beautiful_torches_bop.Constants;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.block.RedstoneTorchBlockBase;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.block.RedstoneWallTorchBlockBase;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.block.TorchBlockBase;
import io.github.jason13official.more_beautiful_torches_bop.impl.common.block.WallTorchBlockBase;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

  public static final String BOP = "biomesoplenty";

  public enum TorchKind {NORMAL, REDSTONE, SOUL, COPPER}

  public record SourceMaterial(String path, String texture) {

    public Identifier id() {
      return Identifier.fromNamespaceAndPath(BOP, path);
    }

    public Identifier textureLocation() {
      return Identifier.fromNamespaceAndPath(BOP, "block/" + texture);
    }
  }

  public record TorchEntry(String name, TorchKind kind, Block standing, Block wall, SourceMaterial material) {

    public Block source() {
      return BuiltInRegistries.BLOCK.getValue(material.id());
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
      material("empyreal_log"), material("stripped_empyreal_log"), material("empyreal_planks"),
      material("origin_oak_log"), material("stripped_origin_oak_log"), material("origin_oak_planks")
  );

  public static final List<TorchEntry> TORCHES = MATERIALS.stream().map(ModBlocks::torch).toList();
  public static final List<TorchEntry> REDSTONE_TORCHES = MATERIALS.stream().map(ModBlocks::redstoneTorch).toList();
  public static final List<TorchEntry> SOUL_TORCHES = MATERIALS.stream().map(ModBlocks::soulTorch).toList();
  public static final List<TorchEntry> COPPER_TORCHES = MATERIALS.stream().map(ModBlocks::copperTorch).toList();

  private static SourceMaterial material(String path) {
    return new SourceMaterial(path, path);
  }

  private static SourceMaterial material(String path, String texture) {
    return new SourceMaterial(path, texture);
  }

  private static TorchEntry torch(SourceMaterial material) {
    String name = material.path() + "_torch";
    Block standing = new TorchBlockBase(ParticleTypes.FLAME, torchProperties(14).setId(blockKey(name)));
    Block wall = new WallTorchBlockBase(ParticleTypes.FLAME,
        torchProperties(14).overrideLootTable(standing.getLootTable()).setId(blockKey(name + "_wall")));
    return new TorchEntry(name, TorchKind.NORMAL, standing, wall, material);
  }

  private static TorchEntry soulTorch(SourceMaterial material) {
    String name = material.path() + "_soul_torch";
    Block standing = new TorchBlockBase(ParticleTypes.SOUL_FIRE_FLAME, torchProperties(10).setId(blockKey(name)));
    Block wall = new WallTorchBlockBase(ParticleTypes.SOUL_FIRE_FLAME,
        torchProperties(10).overrideLootTable(standing.getLootTable()).setId(blockKey(name + "_wall")));
    return new TorchEntry(name, TorchKind.SOUL, standing, wall, material);
  }

  private static TorchEntry copperTorch(SourceMaterial material) {
    String name = material.path() + "_copper_torch";
    Block standing = new TorchBlockBase(ParticleTypes.COPPER_FIRE_FLAME, torchProperties(14).setId(blockKey(name)));
    Block wall = new WallTorchBlockBase(ParticleTypes.COPPER_FIRE_FLAME,
        torchProperties(14).overrideLootTable(standing.getLootTable()).setId(blockKey(name + "_wall")));
    return new TorchEntry(name, TorchKind.COPPER, standing, wall, material);
  }

  private static TorchEntry redstoneTorch(SourceMaterial material) {
    String name = material.path() + "_redstone_torch";
    Block standing = new RedstoneTorchBlockBase(redstoneTorchProperties().setId(blockKey(name)));
    Block wall = new RedstoneWallTorchBlockBase(
        redstoneTorchProperties().overrideLootTable(standing.getLootTable()).setId(blockKey(name + "_wall")));
    return new TorchEntry(name, TorchKind.REDSTONE, standing, wall, material);
  }

  private static ResourceKey<Block> blockKey(String name) {
    return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
  }

  private static BlockBehaviour.Properties torchProperties(int lightLevel) {
    return BlockBehaviour.Properties.of()
        .noCollision()
        .instabreak()
        .lightLevel(state -> lightLevel)
        .sound(SoundType.WOOD);
  }

  private static BlockBehaviour.Properties redstoneTorchProperties() {
    return BlockBehaviour.Properties.of()
        .noCollision()
        .instabreak()
        .lightLevel(state -> state.getValue(RedstoneTorchBlock.LIT) ? 7 : 0)
        .sound(SoundType.WOOD);
  }

  public static void register(BiConsumer<Block, Identifier> consumer) {
    registerAll(TORCHES, consumer);
    registerAll(REDSTONE_TORCHES, consumer);
    registerAll(SOUL_TORCHES, consumer);
    registerAll(COPPER_TORCHES, consumer);
  }

  private static void registerAll(List<TorchEntry> entries, BiConsumer<Block, Identifier> consumer) {
    for (TorchEntry entry : entries) {
      consumer.accept(entry.standing(), Identifier.fromNamespaceAndPath(Constants.MOD_ID, entry.name()));
      consumer.accept(entry.wall(), Identifier.fromNamespaceAndPath(Constants.MOD_ID, entry.name() + "_wall"));
    }
  }
}
