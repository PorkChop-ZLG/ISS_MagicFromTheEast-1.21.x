package net.warphan.iss_magicfromtheeast.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.warphan.iss_magicfromtheeast.ISS_MagicFromTheEast;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ISS_MagicFromTheEast.MOD_ID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput =  generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        var provider = event.getLookupProvider();

        DatapackBuiltinEntriesProvider datapackProvider = new MFTEDataPackProvider(packOutput, provider);
        CompletableFuture<HolderLookup.Provider> lookupProvider =  datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), datapackProvider);
        generator.addProvider(event.includeServer(), new MFTEDamageTypeTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new MFTERecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(MFTELootTableProvider.Block::new, LootContextParamSets.BLOCK)),
                provider
        ));
        generator.addProvider(event.includeServer(), new MFTEBlockStatesProvider(packOutput, existingFileHelper));
    }
}
