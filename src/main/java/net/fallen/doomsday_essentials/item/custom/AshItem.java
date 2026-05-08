package net.fallen.doomsday_essentials.item.custom;

import net.fallen.doomsday_essentials.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;

import java.util.HashMap;
import java.util.Map;

public class AshItem extends Item {
    private static final ResourceLocation BOP_DRIED_SALT_ID = ResourceLocation.fromNamespaceAndPath("biomesoplenty", "dried_salt");
    private static final Map<GlobalPos, Integer> REQUIRED_ATTEMPTS = new HashMap<>();
    private static final Map<GlobalPos, Integer> CURRENT_ATTEMPTS = new HashMap<>();

    public AshItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);

        if (BuiltInRegistries.BLOCK.getKey(clickedState.getBlock()).equals(BOP_DRIED_SALT_ID)) {
            if (!level.isClientSide) {
                GlobalPos globalPos = GlobalPos.of(level.dimension(), clickedPos.immutable());
                int requiredAttempts = REQUIRED_ATTEMPTS.computeIfAbsent(globalPos, ignored -> 2 + level.random.nextInt(5));
                int currentAttempts = CURRENT_ATTEMPTS.getOrDefault(globalPos, 0) + 1;

                CURRENT_ATTEMPTS.put(globalPos, currentAttempts);

                if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                }

                level.playSound(
                        null,
                        clickedPos,
                        SoundEvents.SAND_PLACE,
                        SoundSource.BLOCKS,
                        0.8F,
                        0.9F + level.random.nextFloat() * 0.2F
                );
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                            new BlockParticleOption(ParticleTypes.BLOCK, clickedState),
                            clickedPos.getX() + 0.5D,
                            clickedPos.getY() + 0.5D,
                            clickedPos.getZ() + 0.5D,
                            12,
                            0.25D,
                            0.25D,
                            0.25D,
                            0.02D
                    );
                }

                if (currentAttempts >= requiredAttempts) {
                    level.setBlock(clickedPos, ModBlocks.SALT_CRUSTED_DIRT.get().defaultBlockState(), 11);
                    REQUIRED_ATTEMPTS.remove(globalPos);
                    CURRENT_ATTEMPTS.remove(globalPos);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }
}
