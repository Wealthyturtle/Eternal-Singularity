package singulariteam.eternalsingularity.render;

import codechicken.lib.texture.TextureUtils.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class EternalTextures implements IIconRegister {

	public static TextureAtlasSprite ETERNAL_SINGULARITY_MASK;
	public static TextureAtlasSprite COMBINED_SINGULARITY_MASK;

	@Override
	public void registerIcons(TextureMap map) {
		ETERNAL_SINGULARITY_MASK = map.registerSprite(new ResourceLocation("eternalsingularity:items/eternal_singularity_mask"));
		COMBINED_SINGULARITY_MASK = map.registerSprite(new ResourceLocation("eternalsingularity:items/combined_singularity_mask"));
	}
}
