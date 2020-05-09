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
import morph.avaritia.recipe.extreme.ExtremeShapelessRecipe;
import morph.avaritia.recipe.extreme.IExtremeRecipe;
import morph.avaritia.handler.ConfigHandler;
import morph.avaritia.init.ModItems;

public class CommonProxy {
	public static NonNullList<Ingredient> singularityIngredients = NonNullList.create();
	public static final IExtremeRecipe eternalSingularityRecipe = AvaritiaRecipeManager.EXTREME_RECIPES.put(EternalSingularityItem.instance.getRegistryName(), new ExtremeShapelessRecipe(singularityIngredients, new ItemStack(EternalSingularityItem.instance)).setRegistryName(EternalSingularityItem.instance.getRegistryName()));
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
		ForgeRegistries.ITEMS.register(compoundSingularityItem = new CompoundSingularityItem(81));
		if (Loader.isModLoaded("MineTweaker3"))
			EternalRecipeTweaker.init();
	}

	public void init() {}

	@SuppressWarnings("unchecked")
	public void postInit() {
		if (classSet.isEmpty())
			return;

		IExtremeRecipe catalystRecipe = null;
		for (IExtremeRecipe recipe : AvaritiaRecipeManager.EXTREME_RECIPES.values()) {
			if (recipe.getRecipeOutput().isItemEqual(ModItems.infinity_catalyst)) {
				catalystRecipe = recipe;
				break;
			}
		}

		final Configuration config = new Configuration(configFile);
		boolean useCompoundSingularities = config.getBoolean("useCompoundSingularities", Configuration.CATEGORY_GENERAL, false, "When useCompoundSingularities is Enabled, Basic Singularities will Need to be Crafted into Compound Singularities First.\n[If there are > 81 Basic Singularities, this Config Option will be Set to True Automatically]");
		final boolean loadAllCompoundSingularities = useCompoundSingularities && config.getBoolean("loadAllCompoundSingularities", Configuration.CATEGORY_GENERAL, false, "Enables all compound singularities instead of only the ones needed.");
		final boolean easyMode = config.getBoolean("easyMode", Configuration.CATEGORY_GENERAL, false, "If this Config Option is Enabled, for Every 9 Singularities Used in the Eternal Singularity Recipe, You will Receive an Additional Eternal Singularity for the Recipe Output.");
		if (config.hasChanged())
			config.save();

		if (catalystRecipe != null && !catalystRecipe.isShapedRecipe()) {
			for (Ingredient inputIngredient : catalystRecipe.getIngredients()) {
				final ItemStack[] input = inputIngredient.getMatchingStacks();
				if (inputIngredient.equals(inputIngredient.EMPTY) || input.length == 0) {
					catalystRecipe.getIngredients().remove(inputIngredient);
					continue;
				}
				for (ItemStack stack : input) {
					if (stack.getItem() != null && classSet.contains(stack.getItem().getClass())) {
						singularityIngredients.add(inputIngredient);
						break;
					}
				}
			}
			final int singularityCount = singularityIngredients.size();
			if (singularityCount == 0 || catalystRecipe.isShapedRecipe()) {
				AvaritiaRecipeManager.EXTREME_RECIPES.remove(EternalSingularityItem.instance.getRegistryName());
				if (!useCompoundSingularities) {
					compoundSingularityItem.max = 0;
				}
				return;
			}
			catalystRecipe.getIngredients().removeAll(singularityIngredients);
			final boolean aboveTheLimit = singularityCount > 81;
			useCompoundSingularities = useCompoundSingularities || aboveTheLimit;
			final int compoundMax = loadAllCompoundSingularities ? 81 : (int) Math.ceil((float) singularityCount / 9);
			if (useCompoundSingularities) {
				compoundSingularityItem.max = compoundMax;
				final List<Ingredient> eternalSingularityRecipeInputs = singularityIngredients;
				for (int i = 0; i < compoundMax; i++) {
					NonNullList<Ingredient> compoundIngredients = NonNullList.create();
					final ShapelessRecipes compoundRecipe = new ShapelessRecipes(compoundSingularityItem.getRegistryName().toString() + i, new ItemStack(compoundSingularityItem, 1, MathHelper.clamp(i, 0, 81)), compoundIngredients);
					for (int s = 0; s < 9; s++) {
						final int pos = 9 * i + s;
						if (pos > singularityCount - 1)
							break;
						final Ingredient input = eternalSingularityRecipeInputs.get(pos);
						compoundIngredients.add(input);
					}
					compoundRecipe.setRegistryName(compoundSingularityItem.getRegistryName().toString() + i);
					if (compoundIngredients.size() > 0)
						ForgeRegistries.RECIPES.register(compoundRecipe);
				}
				eternalSingularityRecipeInputs.clear();
				for (int i = 0; i < compoundMax; i++)
					eternalSingularityRecipeInputs.add(Ingredient.fromStacks(new ItemStack(compoundSingularityItem, 1, i)));
			} else {
				compoundSingularityItem.max = 0;
			}
			catalystRecipe.getIngredients().add(Ingredient.fromStacks(new ItemStack(EternalSingularityItem.instance, easyMode ? MathHelper.clamp(compoundMax, 1, 64) : 1)));
		} else if (!useCompoundSingularities) {
			compoundSingularityItem.max = 0;
		}
	}
}