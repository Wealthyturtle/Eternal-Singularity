package singulariteam.eternalsingularity.item;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.util.TransformUtils;
import morph.avaritia.api.ICosmicRenderItem;
import morph.avaritia.api.IHaloRenderItem;
import morph.avaritia.api.registration.IModelRegister;
import morph.avaritia.entity.EntityImmortalItem;
import morph.avaritia.init.AvaritiaTextures;
import morph.avaritia.init.ModItems;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import singulariteam.eternalsingularity.EternalSingularityMod;
import singulariteam.eternalsingularity.render.EternalItemRender;

public class EternalSingularityItem extends Item implements IHaloRenderItem, ICosmicRenderItem, IModelRegister {
	public static final EternalSingularityItem instance = new EternalSingularityItem();

	private EternalSingularityItem() {
		super();
		setRegistryName("eternal_singularity");
		setCreativeTab(EternalSingularityMod.creativeTabs);
	}

	public EnumRarity getRarity(ItemStack stack) {
		return ModItems.COSMIC_RARITY;
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return "item.eternal.singularity";
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return new EntityImmortalItem(world, location, itemstack);
	}

	@Override
	public float getMaskOpacity(ItemStack arg0, EntityLivingBase arg1) {
		return 1.0f;
	}

	@Override
	public TextureAtlasSprite getMaskTexture(ItemStack arg0, EntityLivingBase arg1) {
		// TODO Auto-generated method stub
		return null;
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
        ModelResourceLocation location = new ModelResourceLocation("avaritia:singularity", "type=singularity");
        ModelLoader.registerItemVariants(this, location);
        IBakedModel wrappedModel = new EternalItemRender(TransformUtils.DEFAULT_ITEM, modelRegistry -> modelRegistry.getObject(location));
        ModelRegistryHelper.register(location, wrappedModel);
        ModelLoader.setCustomMeshDefinition(this, stack -> location);
    }
	/*@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir) {
		super.registerIcons(ir);
		this.cosmicMask = ir.registerIcon("eternalsingularity:eternal_singularity_mask");
		this.foregroundIcon = ir.registerIcon("eternalsingularity:eternal_singularity");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return pass == 1 ? foregroundIcon : super.getIcon(stack, pass);
	}*/
}