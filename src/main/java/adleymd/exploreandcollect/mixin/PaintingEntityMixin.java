package adleymd.exploreandcollect.mixin;

import adleymd.exploreandcollect.paintings.ModPaintingItem;
import adleymd.exploreandcollect.registry.ModRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PaintingEntity.class)
public abstract class PaintingEntityMixin {

    /*
    @Redirect(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private boolean constructor(List instance, Object e) {
        if (ModRegistry.isModPaintingMotive((PaintingMotive) e))
            return false;
        else
            return instance.add(e);
    }*/

    @Inject(method = "onBreak", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/decoration/painting/PaintingEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;"),
            cancellable = true)
    private void dropPaintingItemMod(Entity entity, CallbackInfo ci) {
        ModPaintingItem item = ModRegistry.tryGetModPaintingItem(((PaintingEntity)(Object)this).motive);
        if (item != null) {
            ((PaintingEntity) (Object) this).dropItem(item);
            ci.cancel();
        }
    }

    @Inject(method = "getPickBlockStack", at = @At(value = "HEAD"), cancellable = true)
    private void getPickBlockStackMod(CallbackInfoReturnable<ItemStack> cir) {
        ModPaintingItem item = ModRegistry.tryGetModPaintingItem(((PaintingEntity)(Object)this).motive);
        if (item != null)
            cir.setReturnValue(new ItemStack(item));
    }

    @Redirect(method = "writeCustomDataToNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putString(Ljava/lang/String;Ljava/lang/String;)V"))
    private void writeNbtPutString(NbtCompound nbt, String key, String value) {
        PaintingMotive motive = ((PaintingEntity)(Object)this).motive;
        if (ModRegistry.isModPaintingMotive(motive))
            nbt.putString("Motive", ModRegistry.registryModMotives.getId(motive).toString());
        else
            nbt.putString("Motive", Registry.PAINTING_MOTIVE.getId(motive).toString());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/DefaultedRegistry;get(Lnet/minecraft/util/Identifier;)Ljava/lang/Object;", shift = At.Shift.AFTER))
    private void readNbtGetMotive(NbtCompound nbt, CallbackInfo ci) {
        if (ModRegistry.isModPaintingMotive(((PaintingEntity)(Object)this).motive))
            ((PaintingEntity)(Object)this).motive = ModRegistry.registryModMotives.get(Identifier.tryParse(nbt.getString("Motive")));
    }
}
