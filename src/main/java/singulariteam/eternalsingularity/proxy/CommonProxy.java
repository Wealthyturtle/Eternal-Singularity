package singulariteam.eternalsingularity.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.crafting.Grinder;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import singulariteam.eternalsingularity.EternalSingularityItem;
import singulariteam.eternalsingularity.EternalSingularityMod;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fox.spiteful.avaritia.Config.craftingOnly;

public class CommonProxy
{
	final List<Class> classList = new ArrayList<>();

	public CommonProxy() {}

	public final void preInit(final File file)
	{
		final Configuration config = new Configuration(file);
		final List<String> classNameList = Arrays.asList(config.getStringList("classNameList", Configuration.CATEGORY_GENERAL, new String[]{
				"com.rcx.aobdsingularities.item.AOBDItemSingularity",
				"fox.spiteful.avaritia.items",
				"wanion.thermsingul.ThermalSingularityItem",
				"wealthyturtle.uiesingularities.UniversalSingularityItem"
		}, "here is the absolute class name of the Item Classes that must be removed from Infinity Catalyst recipe and inserted into Eternal Singularity."));
		for (final String className : classNameList) {
			try {
				classList.add(Class.forName(className));
			} catch (ClassNotFoundException e) {
				EternalSingularityMod.logger.warn("Couldn't find " + className);
			}
		}
		GameRegistry.registerItem(EternalSingularityItem.instance, "eternal_singularity");
	}

	public void init() {}

	public final void postInit()
	{
		if (craftingOnly)
			return;
		Grinder.catalyst.getInput().add(new ItemStack(EternalSingularityItem.instance, 1));
	}
}