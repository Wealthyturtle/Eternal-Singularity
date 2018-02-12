package singulariteam.eternalsingularity.item;

import java.util.List;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.util.TransformUtils;
import morph.avaritia.api.ICosmicRenderItem;
import morph.avaritia.api.IHaloRenderItem;
import morph.avaritia.api.registration.IModelRegister;
import morph.avaritia.init.AvaritiaTextures;
import morph.avaritia.init.ModItems;
import morph.avaritia.item.ItemResource;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import singulariteam.eternalsingularity.EternalSingularityMod;
import singulariteam.eternalsingularity.render.EternalItemRender;
import singulariteam.eternalsingularity.render.EternalTextures;

public class CompoundSingularityItem extends Item implements IHaloRenderItem, ICosmicRenderItem, IModelRegister {
	public int max;

	public CompoundSingularityItem(final int max)
	{
		super();
		setHasSubtypes(true);
		this.max = max;
		setRegistryName("combined_singularity");
		setCreativeTab(EternalSingularityMod.creativeTabs);
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.UNCOMMON;
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack)
	{
		return "item.combined.singularity." + MathHelper.clamp_int(getDamage(stack), 0, max);
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int j = 0; j < max; j++)
			list.add(new ItemStack(item, 1, j));
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
		/*ModelResourceLocation location = new ModelResourceLocation("eternalsingularity:singularity_compound", "type=singularity");
		ModelLoader.registerItemVariants(this, location);
		IBakedModel wrappedModel = new EternalItemRender(TransformUtils.DEFAULT_ITEM, modelRegistry -> modelRegistry.getObject(location));
		ModelRegistryHelper.register(location, wrappedModel);
		ModelLoader.setCustomMeshDefinition(this, stack -> location);*/
		
		//ModItems.resource.registerModelVariants();
        //Set<Integer> toRegister = Sets.newHashSet(2, 3, 4, 5, 6);

        for (int j = 0; j < max; j++) {
            final ModelResourceLocation location = new ModelResourceLocation("eternalsingularity:singularity_compound", "type=singularity" + j);
            ModelLoader.registerItemVariants(this, location);
            IBakedModel wrappedModel = new EternalItemRender(TransformUtils.DEFAULT_ITEM, modelRegistry -> modelRegistry.getObject(location));
            ModelRegistryHelper.register(location, wrappedModel);
            ModelLoader.setCustomMeshDefinition(this, stack -> location);
            ModelLoader.setCustomModelResourceLocation(this, j, location);
        }
	}

	/*@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir)
	{
		super.registerIcons(ir);
		foregroundIcons = new IIcon[max];
		backgroundIcons = new IIcon[max];

		for (int x = 0; x < max; x++) {
			foregroundIcons[x] = ir.registerIcon("eternalsingularity:combined_singularity_" + x + "_overlay");
			backgroundIcons[x] = ir.registerIcon("eternalsingularity:combined_singularity_" + x);
		}
		this.cosmicMask = ir.registerIcon("eternalsingularity:combined_singularity_mask");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return pass == 1 ? this.foregroundIcons[stack.getItemDamage() % foregroundIcons.length] : this.backgroundIcons[stack.getItemDamage() % backgroundIcons.length];
	}*/
}