package singulariteam.eternalsingularity.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import singulariteam.eternalsingularity.item.EternalSingularityItem;
import singulariteam.eternalsingularity.render.EternalItemRenderer;
import singulariteam.eternalsingularity.render.ShaderHelper;

public final class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		EternalItemRenderer fancies = new EternalItemRenderer();
		MinecraftForgeClient.registerItemRenderer(EternalSingularityItem.instance, fancies);
		if (compoundSingularityItem != null)
			MinecraftForgeClient.registerItemRenderer(compoundSingularityItem, fancies);
		ShaderHelper.initShaders();
	}
}
