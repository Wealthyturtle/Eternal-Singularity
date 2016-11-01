package singulariteam.eternalsingularity.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import singulariteam.eternalsingularity.EternalSingularityItem;
import singulariteam.eternalsingularity.render.EternalItemRenderer;

public final class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		MinecraftForgeClient.registerItemRenderer(EternalSingularityItem.instance, new EternalItemRenderer());
	}
}
