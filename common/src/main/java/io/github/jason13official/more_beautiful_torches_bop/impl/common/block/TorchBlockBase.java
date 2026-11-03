package io.github.jason13official.more_beautiful_torches_bop.impl.common.block;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TorchBlockBase extends TorchBlock {

  public TorchBlockBase(BlockBehaviour.Properties properties, ParticleOptions flameParticle) {
    super(properties, flameParticle);
  }
}
