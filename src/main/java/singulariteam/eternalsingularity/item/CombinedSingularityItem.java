package singulariteam.eternalsingularity.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.items.ItemResource;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.ICosmicRenderItem;
import fox.spiteful.avaritia.render.IHaloRenderItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class CombinedSingularityItem extends Item implements IHaloRenderItem, ICosmicRenderItem
{
	public static final CombinedSingularityItem instance = new CombinedSingularityItem();
	
	public static final int[] types = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 , 11, 12, 13, 14, 15};

	private IIcon cosmicMask;
	private IIcon foregroundIcon;

	private CombinedSingularityItem()
	{
		super();
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(Avaritia.tab);
		setTextureName("eternalsingularity:combined_singularity2");
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.uncommon;
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack)
	{
		int i = MathHelper.clamp_int(stack.getItemDamage(), 0, types.length);
		return "item.combined.singularity." + types[i];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int j = 0; j < types.length; j++) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getMaskTexture(ItemStack stack, EntityPlayer player)
	{
		return cosmicMask;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaskMultiplier(ItemStack stack, EntityPlayer player)
	{
		return 1.0f;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir)
	{
		super.registerIcons(ir);
		this.cosmicMask = ir.registerIcon("eternalsingularity:combined_singularity_mask");
		this.foregroundIcon = ir.registerIcon("eternalsingularity:combined_singularity");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return pass == 1 ? foregroundIcon : super.getIcon(stack, pass);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public boolean drawHalo(ItemStack paramItemStack)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getHaloTexture(ItemStack paramItemStack)
	{
		return ((ItemResource) LudicrousItems.resource).halo[0];
	}

	@SideOnly(Side.CLIENT)
	public int getHaloSize(ItemStack paramItemStack)
	{
		return 4;
	}

	@SideOnly(Side.CLIENT)
	public boolean drawPulseEffect(ItemStack paramItemStack)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public int getHaloColour(ItemStack paramItemStack)
	{
		return -16777216;
	}
}