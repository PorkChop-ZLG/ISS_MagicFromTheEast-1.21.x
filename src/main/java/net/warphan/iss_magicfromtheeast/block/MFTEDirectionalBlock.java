package net.warphan.iss_magicfromtheeast.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class MFTEDirectionalBlock extends HorizontalDirectionalBlock {
    public MFTEDirectionalBlock(Properties p_54120_) {
        super(p_54120_);
    }

    public static final MapCodec<MFTEDirectionalBlock> CODEC = simpleCodec(MFTEDirectionalBlock::new);

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        Direction direction = p_49820_.getHorizontalDirection();
        return this.defaultBlockState()
                .setValue(FACING, direction.getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING);
    }
}
