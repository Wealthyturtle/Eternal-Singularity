package singulariteam.eternalsingularity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.entity.EntityImmortalItem;
import fox.spiteful.avaritia.items.ItemResource;
import fox.spiteful.avaritia.items.ItemSingularity;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.ICosmicRenderItem;
import fox.spiteful.avaritia.render.IHaloRenderItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class EternalSingularityItem extends Item implements IHaloRenderItem, ICosmicRenderItem
{
	private IIcon cosmicMask;
	private IIcon foregroundIcon;
	private IIcon haloIcon;

	public EternalSingularityItem()
	{
		super();
		setCreativeTab(Avaritia.tab);
		setTextureName("eternalsingularity:eternal_singularity2");
		setUnlocalizedName(Reference.MOD_ID + ".eternal.singularity");
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return LudicrousItems.cosmic;
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack)
	{
		return "item.eternal_singularity";
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack)
	{
		return true;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getMaskTexture(ItemStack stack, EntityPlayer player) {
        return cosmicMask;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
	public float getMaskMultiplier(ItemStack stack, EntityPlayer player) {
		return 1.0f;
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister ir) {
        super.registerIcons(ir);

        this.cosmicMask = ir.registerIcon("eternalsingularity:eternal_singularity_mask");
        this.foregroundIcon = ir.registerIcon("eternalsingularity:eternal_singularity");
        this.haloIcon = ir.registerIcon("eternalsingularity:halofire");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
    	if (pass == 1) { return this.foregroundIcon; }

    	return super.getIcon(stack, pass);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		return new EntityImmortalItem(world, location, itemstack);
	}

	@SideOnly(Side.CLIENT)
	public boolean drawHalo(ItemStack paramItemStack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getHaloTexture(ItemStack paramItemStack) {
		return haloIcon;
	}

	@SideOnly(Side.CLIENT)
	public int getHaloSize(ItemStack paramItemStack) {
		return 20;
	}

	@SideOnly(Side.CLIENT)
	public boolean drawPulseEffect(ItemStack paramItemStack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public int getHaloColour(ItemStack paramItemStack) {
		return 0xFFFFFFFF;
	}
}