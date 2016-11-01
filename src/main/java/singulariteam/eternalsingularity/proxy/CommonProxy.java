package singulariteam.eternalsingularity.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import fox.spiteful.avaritia.crafting.Grinder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import singulariteam.eternalsingularity.EternalSingularityItem;
import singulariteam.eternalsingularity.EternalSingularityMod;

import java.io.File;
import java.util.*;

import static fox.spiteful.avaritia.Config.craftingOnly;

public class CommonProxy
{
	public static final ShapelessOreRecipe eternalSingularityRecipe = new ShapelessOreRecipe(EternalSingularityItem.instance);
	private static final Set<Class> classSet = new HashSet<>();

	public CommonProxy() {}

	public final void preInit(final File file)
	{
		final Configuration config = new Configuration(file);
		final List<String> classNameList = Arrays.asList(config.getStringList("classNameList", Configuration.CATEGORY_GENERAL, new String[]{
				"com.rcx.aobdsingularities.item.AOBDItemSingularity",
				"fox.spiteful.avaritia.items.ItemSingularity",
				"wanion.thermsingul.ThermalSingularityItem",
				"wealthyturtle.uiesingularities.UniversalSingularityItem"
		}, "here is the absolute class name of the Item Classes that must be removed from Infinity Catalyst recipe and inserted into Eternal Singularity."));
		for (final String className : classNameList) {
			try {
				classSet.add(Class.forName(className));
			} catch (ClassNotFoundException e) {
				EternalSingularityMod.logger.warn("Couldn't find " + className);
			}
		}
		GameRegistry.registerItem(EternalSingularityItem.instance, "eternal_singularity");
	}

	public void init() {}

	@SuppressWarnings("unchecked")
	public final void postInit()
	{
		if (craftingOnly)
			return;
		ExtremeCraftingManager.getInstance().getRecipeList().add(eternalSingularityRecipe);
		for (final Iterator<Object> catalystRecipeIterator = Grinder.catalyst.getInput().iterator(); catalystRecipeIterator.hasNext();) {
			final Object input = catalystRecipeIterator.next();
			if (!(input instanceof ItemStack))
				continue;
			final Item item = ((ItemStack) input).getItem();
			if (item != null && classSet.contains(item.getClass())) {
				catalystRecipeIterator.remove();
				eternalSingularityRecipe.getInput().add(((ItemStack) input).copy());
			}
		}
		Grinder.catalyst.getInput().add(new ItemStack(EternalSingularityItem.instance));
	}
}