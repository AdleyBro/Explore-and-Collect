package adleymd.exploreandcollect;

import adleymd.exploreandcollect.paintings.ModPaintingItem;
import adleymd.exploreandcollect.registry.ModRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Exploreandcollect implements ModInitializer {

    public static final String MODID = "exploreandcollect";

    public static final ModPaintingItem STARRY_NIGHT = new ModPaintingItem(EntityType.PAINTING, new FabricItemSettings().group(ItemGroup.DECORATIONS).maxCount(1).rarity(Rarity.EPIC).fireproof());
    public static final PaintingMotive STARRY_NIGHT_MOTIVE = ModRegistry.registerPaintingMotive("starry_night", 32, 32, STARRY_NIGHT);

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MODID, "starry_night"), STARRY_NIGHT);
    }
}
