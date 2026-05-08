package net.fallen.doomsday_essentials.item.custom;

import net.fallen.doomsday_essentials.worldgen.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public class CreativeTerraformerItem extends Item {
    private static final int COOLDOWN_TICKS = 200;

    public CreativeTerraformerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, net.minecraft.world.entity.player.Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (level.isClientSide()) {
            return InteractionResultHolder.success(stack);
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResultHolder.pass(stack);
        }

        boolean isInRestoredEarth = level.dimension().equals(ModDimensions.RESTORED_EARTH_LEVEL);
        var targetKey = isInRestoredEarth ? ModDimensions.WASTELAND_EARTH_LEVEL : ModDimensions.RESTORED_EARTH_LEVEL;
        ServerLevel targetLevel = level.getServer().getLevel(targetKey);
        if (targetLevel == null) {
            serverPlayer.displayClientMessage(Component.translatable("message.doomsday_essentials.restored_earth_unavailable"), true);
            return InteractionResultHolder.fail(stack);
        }

        BlockPos spawnPos = targetLevel.getSharedSpawnPos();
        double x = spawnPos.getX() + 0.5D;
        double y = spawnPos.getY() + 1.0D;
        double z = spawnPos.getZ() + 0.5D;

        serverPlayer.changeDimension(new DimensionTransition(targetLevel, new Vec3(x, y, z), serverPlayer.getDeltaMovement(), serverPlayer.getYRot(), serverPlayer.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)));
        serverPlayer.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        return InteractionResultHolder.consume(stack);
    }
}
