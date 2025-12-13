package net.warphan.iss_magicfromtheeast.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.warphan.iss_magicfromtheeast.registries.MFTEEntityRegistries;

import java.util.function.UnaryOperator;

public class MFTEUtils {
    public static EnchantmentEffectComponents createEnchantmentEffectComponent(String modID) {
        return new EnchantmentEffectComponents(modID);
    }

    public static class EnchantmentEffectComponents extends DeferredRegister<DataComponentType<?>> {
        protected EnchantmentEffectComponents(String name) {
            super(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, name);
        }

        public <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> registerComponentType(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
            return this.register(name, () -> ((DataComponentType.Builder)builder.apply(DataComponentType.builder())).build());
        }
    }

    public static boolean checkMonsterSpawnRules(ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return !pLevel.getBiome(pPos).is(Tags.Biomes.NO_DEFAULT_MONSTERS) && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(pLevel, pPos, pRandom) && Monster.checkMobSpawnRules(MFTEEntityRegistries.JIANGSHI.get(), pLevel, pSpawnType, pPos, pRandom);
    }
}
