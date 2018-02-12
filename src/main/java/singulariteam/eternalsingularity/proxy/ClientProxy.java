package singulariteam.eternalsingularity.proxy;

import java.io.File;

import codechicken.lib.texture.TextureUtils;
import morph.avaritia.init.AvaritiaTextures;
import net.minecraftforge.client.MinecraftForgeClient;
import singulariteam.eternalsingularity.item.EternalSingularityItem;
import singulariteam.eternalsingularity.render.EternalTextures;

public final class ClientProxy extends CommonProxy {
	//EternalItemRenderer fancies = new EternalItemRenderer();

	@Override
	public void preInit(final File file) {
		super.preInit(file);
		TextureUtils.addIconRegister(new EternalTextures());
		EternalSingularityItem.instance.registerModels();
		compoundSingularityItem.registerModels();
	}
	
	@Override
	public void init() {
		super.init();
		// MinecraftForgeClient.registerItemRenderer(EternalSingularityItem.instance, fancies);
	}

	@Override
	public void postInit() {
		super.postInit();
		// if (compoundSingularityItem != null)
		// MinecraftForgeClient.registerItemRenderer(compoundSingularityItem, fancies);
		
	}
}
