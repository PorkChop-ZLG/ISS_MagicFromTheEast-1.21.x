package net.warphan.iss_magicfromtheeast.entity.spells.jade_judgement;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class JadeDaoRenderer extends GeoEntityRenderer<JadeDao> {
    public JadeDaoRenderer(EntityRendererProvider.Context context) {
        super(context, new JadeDaoModel());
        this.shadowRadius = 2.0f;
    }

    @Override
    public RenderType getRenderType(JadeDao animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.energySwirl(texture, 0, 0);
    }
}
