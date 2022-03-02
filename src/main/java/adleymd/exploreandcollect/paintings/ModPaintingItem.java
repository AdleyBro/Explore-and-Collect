package adleymd.exploreandcollect.paintings;

import adleymd.exploreandcollect.Exploreandcollect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DecorationItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ModPaintingItem extends DecorationItem {

    public ModPaintingItem(EntityType<PaintingEntity> type, Settings settings) {
        super(type, settings);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PaintingEntity paintingEntity;
        BlockPos blockPos = context.getBlockPos();
        Direction direction = context.getSide();
        BlockPos blockPos2 = blockPos.offset(direction);
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        if (playerEntity != null && !this.canPlaceOn(playerEntity, direction, itemStack, blockPos2)) {
            return ActionResult.FAIL;
        }
        World world = context.getWorld();
        paintingEntity = new PaintingEntity(world, blockPos2, direction, Exploreandcollect.STARRY_NIGHT_MOTIVE); // <=======(XD)
        NbtCompound nbtCompound = itemStack.getNbt();
        if (nbtCompound != null) {
            EntityType.loadFromEntityNbt(world, playerEntity, paintingEntity, nbtCompound);
        }
        if (paintingEntity.canStayAttached()) {
            if (!world.isClient) {
                paintingEntity.onPlace();
                world.emitGameEvent(playerEntity, GameEvent.ENTITY_PLACE, blockPos);
                world.spawnEntity(paintingEntity);
            }
            itemStack.decrement(1);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.CONSUME;
    }
}
