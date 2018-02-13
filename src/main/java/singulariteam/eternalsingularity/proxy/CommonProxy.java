package singulariteam.eternalsingularity.proxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import singulariteam.eternalsingularity.EternalRecipeTweaker;
import singulariteam.eternalsingularity.EternalSingularityMod;
import singulariteam.eternalsingularity.item.CompoundSingularityItem;
import singulariteam.eternalsingularity.item.EternalSingularityItem;

import java.io.File;
import java.util.*;

import morph.avaritia.recipe.AvaritiaRecipeManager;
import morph.avaritia.recipe.extreme.ExtremeCraftingManager;
import morph.avaritia.recipe.extreme.ExtremeShapelessRecipe;
import morph.avaritia.recipe.extreme.IExtremeRecipe;
//import morph.avaritia.recipe.extreme.ExtremeShapelessOreRecipe;
import morph.avaritia.handler.ConfigHandler;
import morph.avaritia.init.ModItems;
//import morph.avaritia.init.Recipes;

public class CommonProxy {
	static NonNullList<Ingredient> singularityIngredients = NonNullList.create();
	public static final IExtremeRecipe eternalSingularityRecipe = AvaritiaRecipeManager.EXTREME_RECIPES.put(EternalSingularityItem.instance.getRegistryName(), new ExtremeShapelessRecipe(singularityIngredients, new ItemStack(EternalSingularityItem.instance)));
	private static final Set<Class> classSet = new HashSet<Class>();
	protected CompoundSingularityItem compoundSingularityItem = null;
	private File configFile;

	public CommonProxy() {
	}

	public void preInit(final File file) {
		final Configuration config = new Configuration(configFile = file);
		final String[] classNameList = config.getStringList("classNameList", Configuration.CATEGORY_GENERAL,
				new String[] {
					"morph.avaritia.item.ItemSingularity",
					"shadows.singularity.item.ItemSingularity",
					"thelm.jaopca.singularities.ItemSingularityBase",
					"wanion.thermsingul.ThermalSingularityItem",
					"wealthyturtle.uiesingularities.UniversalSingularityItem" },
					"here is the absolute class name of the Item Classes that must be removed from Infinity Catalyst recipe and inserted into Eternal Singularity.");
		if (config.hasChanged())
			config.save();
		for (final String className : classNameList) {
			try {
				classSet.add(Class.forName(className));
			} catch (ClassNotFoundException e) {
				EternalSingularityMod.logger.warn("Couldn't find " + className);
			}
		}
		ForgeRegistries.ITEMS.register(EternalSingularityItem.instance);
		ForgeRegistries.ITEMS.register(compoundSingularityItem = new CompoundSingularityItem(16));
		if (Loader.isModLoaded("MineTweaker3"))
			EternalRecipeTweaker.init();
	}

	public void init() {}

	@SuppressWarnings("unchecked")
	public void postInit() {
		if (classSet.isEmpty())// || !AvaritiaRecipeManager.EXTREME_RECIPES.containsKey("avaritia:resource"))
			return;
		
		IExtremeRecipe catalystRecipe = null;
		for (IExtremeRecipe recipe : AvaritiaRecipeManager.EXTREME_RECIPES.values()) {
			if (recipe.getRecipeOutput().isItemEqual(ModItems.infinity_catalyst)) {
				catalystRecipe = recipe;
				break;
			}
		}

		for (final Iterator<Ingredient> catalystRecipeIterator = catalystRecipe.getIngredients().iterator(); catalystRecipeIterator.hasNext();) {
			Ingredient inputIngredient = catalystRecipeIterator.next();
			final ItemStack[] input = inputIngredient.getMatchingStacks();
			for (ItemStack stack : input) {
				if (stack.getItem() != null && classSet.contains(stack.getItem().getClass())) {
					singularityIngredients.add(inputIngredient);
					break;
				}
			}
		}
		catalystRecipe.getIngredients().removeAll(singularityIngredients);
		final Configuration config = new Configuration(configFile);
		final int singularityCount = singularityIngredients.size();
		final boolean aboveTheLimit = singularityCount > 81;
		final boolean useCompoundSingularities = config.getBoolean("useCompoundSingularities", Configuration.CATEGORY_GENERAL, aboveTheLimit, "When useCompoundSingularities is Enabled, Basic Singularities will Need to be Crafted into Compound Singularities First.\n[If there are > 81 Basic Singularities, this Config Option will be Set to True Automatically]") || aboveTheLimit;
		final boolean easyMode = config.getBoolean("easyMode", Configuration.CATEGORY_GENERAL, false, "If this Config Option is Enabled, for Every 9 Singularities Used in the Eternal Singularity Recipe, You will Receive an Additional Eternal Singularity for the Recipe Output.");
		if (config.hasChanged())
			config.save();
		final int compoundMax = (int) Math.ceil((float) singularityCount / 9);
		compoundSingularityItem.max = 0;
		if (useCompoundSingularities) {
			compoundSingularityItem.max = compoundMax;
			final List<Ingredient> eternalSingularityRecipeInputs = eternalSingularityRecipe.getIngredients();
			for (int i = 0; i < compoundMax; i++) {
				final IRecipe compoundRecipe = new ShapelessRecipes(compoundSingularityItem.getRegistryName().toString() + i, new ItemStack(compoundSingularityItem, 1, MathHelper.clamp(i, 0, 64)), NonNullList.create());
				//new ItemStack(compoundSingularityItem, 1, MathHelper.clamp(i, 0, 64))
				for (int s = 0; s < 9; s++) {
					final int pos = 9 * i + s;
					if (pos > singularityCount - 1)
						break;
					final Object input = eternalSingularityRecipeInputs.get(pos);
					if (!(input instanceof ItemStack))
						continue;
					compoundRecipe.getIngredients().add(Ingredient.fromStacks(((ItemStack) input).copy()));
				}
				if (compoundRecipe.getIngredients().size() > 0)
					ForgeRegistries.RECIPES.register(compoundRecipe);
			}
			eternalSingularityRecipeInputs.clear();
			for (int i = 0; i < compoundMax; i++)
				eternalSingularityRecipeInputs.add(Ingredient.fromStacks(new ItemStack(compoundSingularityItem, 1, i)));
		}
		if (config.hasChanged())
			config.save();
		catalystRecipe.getIngredients().add(Ingredient.fromStacks(new ItemStack(EternalSingularityItem.instance, easyMode ? MathHelper.clamp(compoundMax, 1, 64) : 1)));
	}
}