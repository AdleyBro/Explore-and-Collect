package adleymd.exploreandcollect.mixin;

import adleymd.exploreandcollect.registry.ModRegistry;
import net.minecraft.client.texture.PaintingManager;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(PaintingManager.class)
public abstract class PaintingManagerMixin {

    @Redirect(method = "getPaintingSprite",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/registry/DefaultedRegistry;getId(Ljava/lang/Object;)Lnet/minecraft/util/Identifier;"))
    private Identifier getModMotive(DefaultedRegistry<PaintingMotive> vanilla_registry, Object motive) {
        if (ModRegistry.isModPaintingMotive((PaintingMotive) motive))
            return ModRegistry.registryModMotives.getId((PaintingMotive) motive);
        else
            return vanilla_registry.getId((PaintingMotive) motive);
    }

    @Inject(method = "getSprites", at = @At("RETURN"), cancellable = true)
    private void getSprites(CallbackInfoReturnable<Stream<Identifier>> cir) {
        Stream<Identifier> vanilla_ids = Stream.concat(Registry.PAINTING_MOTIVE.getIds().stream(), Stream.of(new Identifier("back")));
        cir.setReturnValue(Stream.concat(vanilla_ids, ModRegistry.registryModMotives.getIds().stream()));
    }
}
