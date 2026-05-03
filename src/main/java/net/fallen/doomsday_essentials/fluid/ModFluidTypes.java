package net.fallen.doomsday_essentials.fluid;

import net.fallen.doomsday_essentials.DoomsdayEssentials;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.parse("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.parse("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = ResourceLocation.parse("block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, DoomsdayEssentials.MOD_ID);

    public static final Supplier<FluidType> SALT_WATER_FLUID_TYPE = registerFluidType("salt_water_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA1326B53,
                    new Vector3f(82f / 255, 143f / 255, 110f / 255),
                    FluidType.Properties.create()
                            .density(1000)
                            .temperature(300)
                            .viscosity(1100)
                            .lightLevel(0)
                            .canDrown(true)
                            .canSwim(true)
                            .canPushEntity(true)
                            .supportsBoating(true)
                            .canConvertToSource(true)
                            .fallDistanceModifier(0.0F)
                            .canHydrate(false)));

    private static Supplier<FluidType> registerFluidType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }


    public static void register(IEventBus eventbus) {
        FLUID_TYPES.register(eventbus);
    }
}
