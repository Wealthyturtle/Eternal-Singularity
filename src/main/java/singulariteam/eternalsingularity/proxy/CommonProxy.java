package singulariteam.eternalsingularity.proxy;

import fox.spiteful.avaritia.crafting.CompressorManager;
import fox.spiteful.avaritia.crafting.Grinder;
import fox.spiteful.avaritia.render.CosmicItemRenderer;
import fox.spiteful.avaritia.render.FancyHaloRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import singulariteam.eternalsingularity.EternalSingularityItem;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.registry.GameRegistry;

import java.io.File;
import java.util.*;

import static fox.spiteful.avaritia.Config.craftingOnly;
import static java.io.File.separatorChar;

public class CommonProxy
{
	protected final EternalSingularityItem eternalSingularity = new EternalSingularityItem();

	public CommonProxy()
	{
	}

	public final void preInit()
	{
		GameRegistry.registerItem(eternalSingularity, "eternal_singularity");
	}

	public void init() {
		
	}

	public final void postInit()
	{
		if (!craftingOnly)
			Grinder.catalyst.getInput().add(new ItemStack(eternalSingularity, 1));
	}
}
