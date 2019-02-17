package singulariteam.eternalsingularity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import singulariteam.eternalsingularity.proxy.CommonProxy;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wanion.lib.common.CraftTweakerHelper;

import javax.annotation.Nonnull;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;

import java.util.Iterator;
import java.util.List;

@ZenClass("mods.eternalsingularity")
public final class EternalRecipeTweaker {
	private static final List<Ingredient> eternalSingularityRecipe = CommonProxy.singularityIngredients;

	private EternalRecipeTweaker() {
	}

	public static void init() {
		CraftTweakerAPI.registerClass(EternalRecipeTweaker.class);
	}

	@ZenMethod
	public static void add(@Nonnull final IItemStack input) {
		final ItemStack itemStack = CraftTweakerHelper.toStack(input);
		if (itemStack != null && itemStack.getItem() != null)
			CraftTweakerAPI.apply(new Add(itemStack));
	}

	@ZenMethod
	public static void remove(final IItemStack input) {
		final ItemStack itemStack = CraftTweakerHelper.toStack(input);
		if (itemStack != null && itemStack.getItem() != null)
			CraftTweakerAPI.apply(new Remove(itemStack));
	}

	private static class Add implements IAction {
		private final ItemStack itemStack;

		public Add(@Nonnull final ItemStack itemStack) {
			this.itemStack = itemStack;
		}

		@Override
		public void apply() {
			eternalSingularityRecipe.add(Ingredient.fromStacks(itemStack));
		}

		@Override
		public String describe() {
			return "Adding " + itemStack.getDisplayName() + " into Eternal Singularity Recipe.";
		}
	}

	private static class Remove implements IAction {
		private final Ingredient itemStack;

		private Remove(@Nonnull final ItemStack itemStackToRemove) {
			Ingredient itemStack = null;
			for (final Iterator<Ingredient> eternalSingularityRecipeIterator = eternalSingularityRecipe
					.iterator(); eternalSingularityRecipeIterator.hasNext() && itemStack == null;) {
				final Object input = eternalSingularityRecipeIterator.next();
				if (input instanceof Ingredient && ((Ingredient) input).test(itemStackToRemove))
					itemStack = (Ingredient) input;
			}
			this.itemStack = itemStack;
		}

		@Override
		public void apply() {
			eternalSingularityRecipe.remove(itemStack);
		}

		@Override
		public String describe() {
			return "Removing " + itemStack.toString() + " from Eternal Singularity Recipe.";
		}
	}
}