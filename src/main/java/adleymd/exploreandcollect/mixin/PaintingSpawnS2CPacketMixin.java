package adleymd.exploreandcollect.mixin;

import adleymd.exploreandcollect.registry.ModRegistry;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.network.packet.s2c.play.PaintingSpawnS2CPacket;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PaintingSpawnS2CPacket.class)
public abstract class PaintingSpawnS2CPacketMixin {

    @Redirect(method = "<init>(Lnet/minecraft/entity/decoration/painting/PaintingEntity;)V",
                at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/registry/DefaultedRegistry;getRawId(Ljava/lang/Object;)I"))
    private int setModMotiveId(DefaultedRegistry<PaintingMotive> instance, Object motive) {
        if (ModRegistry.isModPaintingMotive((PaintingMotive) motive)) {
            System.out.println("Set ID: " + (ModRegistry.registryModMotives.getRawId((PaintingMotive) motive) - ModRegistry.registryModMotives.size()));
            return ModRegistry.registryModMotives.getRawId((PaintingMotive) motive) - ModRegistry.registryModMotives.size();
        }
        else
            return Registry.PAINTING_MOTIVE.getRawId((PaintingMotive) motive);
    }

    @Redirect(method = "getMotive",
                at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/registry/DefaultedRegistry;get(I)Ljava/lang/Object;"))
    private Object getModMotive(DefaultedRegistry<PaintingMotive> instance, int motiveId) {
        if (motiveId < 0) {
            System.out.println("Get ID: " + motiveId + " ; Get Real ID: " + (motiveId + ModRegistry.registryModMotives.size()));
            System.out.println("Get Motive: " + ModRegistry.registryModMotives.get(motiveId + ModRegistry.registryModMotives.size()));
            return ModRegistry.registryModMotives.get(motiveId + ModRegistry.registryModMotives.size());
        }
        else
            return Registry.PAINTING_MOTIVE.get(motiveId);
    }
}
