package net.warphan.iss_magicfromtheeast.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.StreamCodec;
import net.warphan.iss_magicfromtheeast.registries.MFTEParticleRegistries;

import javax.annotation.Nonnull;

public class JadeSlashParticleOptions implements ParticleOptions {
    public final float scale;
    public final float xf;
    public final float yf;
    public final float zf;
    public final float xu;
    public final float yu;
    public final float zu;

    public JadeSlashParticleOptions(float xf, float yf, float zf, float xu, float yu, float zu, float scale) {
        this.scale = scale;
        this.xf = xf;
        this.yf = yf;
        this.zf = zf;
        this.xu = xu;
        this.yu = yu;
        this.zu = zu;
    }

    public static StreamCodec<? super ByteBuf, JadeSlashParticleOptions> STREAM_CODEC = StreamCodec.of(
            (buf, option) -> {
                buf.writeFloat(option.xf);
                buf.writeFloat(option.yf);
                buf.writeFloat(option.zf);
                buf.writeFloat(option.xu);
                buf.writeFloat(option.yu);
                buf.writeFloat(option.zu);
                buf.writeFloat(option.scale);
            },
            (buf) -> new JadeSlashParticleOptions(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat())
    );

    public static MapCodec<JadeSlashParticleOptions> MAP_CODEC = RecordCodecBuilder.mapCodec(object ->
            object.group(
                    Codec.FLOAT.fieldOf("xf").forGetter(p -> ((JadeSlashParticleOptions) p).xf),
                    Codec.FLOAT.fieldOf("yf").forGetter(p -> ((JadeSlashParticleOptions) p).yf),
                    Codec.FLOAT.fieldOf("zf").forGetter(p -> ((JadeSlashParticleOptions) p).zf),
                    Codec.FLOAT.fieldOf("xu").forGetter(p -> ((JadeSlashParticleOptions) p).xu),
                    Codec.FLOAT.fieldOf("yu").forGetter(p -> ((JadeSlashParticleOptions) p).yu),
                    Codec.FLOAT.fieldOf("zu").forGetter(p -> ((JadeSlashParticleOptions) p).zu),
                    Codec.FLOAT.fieldOf("scale").forGetter(p -> ((JadeSlashParticleOptions) p).scale)
            ).apply(object, JadeSlashParticleOptions::new
            ));

    public @Nonnull ParticleType<JadeSlashParticleOptions> getType() {
        return MFTEParticleRegistries.JADE_SLASH_PARTICLE.get();
    }
}
