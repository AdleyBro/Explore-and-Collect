package adleymd.exploreandcollect.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static io.netty.util.ReferenceCountUtil.release;
import static io.netty.util.ReferenceCountUtil.retain;

@Environment(EnvType.CLIENT)
public class ExploreandcollectClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

    }
}
