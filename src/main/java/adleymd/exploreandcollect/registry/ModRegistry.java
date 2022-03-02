package adleymd.exploreandcollect.registry;

import adleymd.exploreandcollect.Exploreandcollect;
import adleymd.exploreandcollect.paintings.ModPaintingItem;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.impl.client.texture.SpriteRegistryCallbackHolder;
import net.minecraft.client.render.model.SpriteAtlasManager;
import net.minecraft.client.texture.SpriteAtlasHolder;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.HashMap;
import java.util.HashSet;

public class ModRegistry {

    public static final SimpleRegistry<PaintingMotive> registryModMotives =
            FabricRegistryBuilder.createSimple(PaintingMotive.class, new Identifier(Exploreandcollect.MODID, "mod_motives")).
                    attribute(RegistryAttribute.SYNCED).buildAndRegister();

    private static final HashMap<PaintingMotive, ModPaintingItem> modPaintingMotives = new HashMap<>();

    public static PaintingMotive registerPaintingMotive(String id, int width, int height, ModPaintingItem paintingItem) {
        PaintingMotive motive = new PaintingMotive(width, height);
        modPaintingMotives.put(motive, paintingItem);
        return registryModMotives.add(RegistryKey.of(Registry.MOTIVE_KEY, new Identifier("exploreandcollect", id)),
                motive, Lifecycle.stable());
        //return Registry.register(Registry.PAINTING_MOTIVE, new Identifier(Exploreandcollect.MODID, id), motive);
    }

    public static boolean isModPaintingMotive(PaintingMotive motive) {
        return modPaintingMotives.containsKey(motive);
    }

    public static ModPaintingItem tryGetModPaintingItem(PaintingMotive motive) {
        return modPaintingMotives.get(motive);
    }
}
