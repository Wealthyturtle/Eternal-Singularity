package singulariteam.eternalsingularity.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import singulariteam.eternalsingularity.item.EternalSingularityItem;

public final class ClientProxy extends CommonProxy {
	//EternalItemRenderer fancies = new EternalItemRenderer();

	@Override
	public void init() {
		super.init();
		// MinecraftForgeClient.registerItemRenderer(EternalSingularityItem.instance, fancies);
	}

	@Override
	public void postInit() {
		super.postInit();
		// if (compoundSingularityItem != null)
		// MinecraftForgeClient.registerItemRenderer(compoundSingularityItem,
		// fancies);
		EternalSingularityItem.instance.registerModels();
	}
}
