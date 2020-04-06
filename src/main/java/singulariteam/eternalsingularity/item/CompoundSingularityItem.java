package singulariteam.eternalsingularity.item;

import java.util.List;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.util.TransformUtils;
import morph.avaritia.api.ICosmicRenderItem;
import morph.avaritia.api.IHaloRenderItem;
import morph.avaritia.api.registration.IModelRegister;
import morph.avaritia.init.AvaritiaTextures;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import singulariteam.eternalsingularity.EternalSingularityMod;
import singulariteam.eternalsingularity.render.EternalItemRender;
import singulariteam.eternalsingularity.render.EternalTextures;

public class CompoundSingularityItem extends Item implements IHaloRenderItem, ICosmicRenderItem, IModelRegister {
	public int max;

	public CompoundSingularityItem(final int max) {
		super();
		setHasSubtypes(true);
		this.max = max;
		setRegistryName("combined_singularity");
		setCreativeTab(EternalSingularityMod.creativeTabs);
	}

	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return "item.combined.singularity." + getDamage(stack);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (isInCreativeTab(tab)) {
			for (int j = 0; j < max; j++)
				list.add(new ItemStack(this, 1, j));
		}
	}

	@Override
	public float getMaskOpacity(ItemStack arg0, EntityLivingBase arg1) {
		return 1.0f;
	}

	@Override
	public TextureAtlasSprite getMaskTexture(ItemStack arg0, EntityLivingBase arg1) {
		return EternalTextures.COMBINED_SINGULARITY_MASK;
	}

	@Override
	public int getHaloColour(ItemStack arg0) {
		return 0xFF000000;
	}

	@Override
	public int getHaloSize(ItemStack arg0) {
		return 4;
	}

	@Override
	public TextureAtlasSprite getHaloTexture(ItemStack arg0) {
		return AvaritiaTextures.HALO;
	}

	@Override
	public boolean shouldDrawHalo(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean shouldDrawPulse(ItemStack arg0) {
		return false;
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void registerModels() {
		for (int j = 0; j < max; j++) {
			final ModelResourceLocation location = new ModelResourceLocation("eternalsingularity:singularity_compound", "type=singularity" + j);
			ModelLoader.registerItemVariants(this, location);
			IBakedModel wrappedModel = new EternalItemRender(TransformUtils.DEFAULT_ITEM, modelRegistry -> modelRegistry.getObject(location));
			ModelRegistryHelper.register(location, wrappedModel);
			ModelLoader.setCustomMeshDefinition(this, stack -> location);
			ModelLoader.setCustomModelResourceLocation(this, j, location);
		}
	}
}