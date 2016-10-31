package singulariteam.eternalsingularity.proxy;

import org.lwjgl.opengl.GL11;

import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.CosmicItemRenderer;
import fox.spiteful.avaritia.render.FancyHaloRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import singulariteam.eternalsingularity.EternalSingularityItem;
import singulariteam.eternalsingularity.render.EternalItemRenderer;

public final class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		MinecraftForgeClient.registerItemRenderer(eternalSingularity, new EternalItemRenderer());
	}
}
