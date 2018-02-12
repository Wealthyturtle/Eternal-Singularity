package singulariteam.eternalsingularity.proxy;

import java.io.File;

import codechicken.lib.texture.TextureUtils;
import morph.avaritia.init.AvaritiaTextures;
import net.minecraftforge.client.MinecraftForgeClient;
import singulariteam.eternalsingularity.item.EternalSingularityItem;
import singulariteam.eternalsingularity.render.EternalTextures;

public final class ClientProxy extends CommonProxy {

	@Override
	public void preInit(final File file) {
		super.preInit(file);
		TextureUtils.addIconRegister(new EternalTextures());
		EternalSingularityItem.instance.registerModels();
		if (compoundSingularityItem != null)
			compoundSingularityItem.registerModels();
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void postInit() {
		super.postInit();
	}
}
