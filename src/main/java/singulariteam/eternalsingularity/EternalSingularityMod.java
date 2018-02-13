package singulariteam.eternalsingularity;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Logger;
import singulariteam.eternalsingularity.item.EternalSingularityItem;
import singulariteam.eternalsingularity.proxy.CommonProxy;

import static singulariteam.eternalsingularity.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, acceptedMinecraftVersions = MC_VERSION, dependencies = "required-after:avaritia;required-after:wanionlib")
public class EternalSingularityMod {
	@Mod.Instance(MOD_ID)
	public static EternalSingularityMod instance;

	public static Logger logger;

	@SidedProxy(serverSide = COMMON_PROXY, clientSide = CLIENT_PROXY)
	public static CommonProxy proxy;

	public static final CreativeTabs creativeTabs = new CreativeTabs(MOD_ID) {
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(EternalSingularityItem.instance);
		}
	};

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event.getSuggestedConfigurationFile());
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent event) {
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
		proxy.postInit();
	}
}