package net.warphan.iss_magicfromtheeast.item;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.warphan.iss_magicfromtheeast.registries.MFTEDataComponentRegistries;
import org.apache.commons.lang3.math.Fraction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public final class LoadableWeaponContents implements TooltipComponent {
    public static final LoadableWeaponContents EMPTY = new LoadableWeaponContents(List.of());
    public static final Codec<LoadableWeaponContents> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, LoadableWeaponContents> STREAM_CODEC;
    private static final Fraction PROJECTILE_IN_STORAGE_WEIGHT;

    final List<ItemStack> items;
    final Fraction weight;

    LoadableWeaponContents(List<ItemStack> items, Fraction weight) {
        this.items = items;
        this.weight = weight;
    }

    public static LoadableWeaponContents of(List<ItemStack> stacks) {
        return new LoadableWeaponContents(List.copyOf(Lists.transform(stacks, ItemStack::copy)));
    }

    public LoadableWeaponContents(List<ItemStack> stacks) {
        this(stacks, computeContentWeight(stacks));
    }

    private static Fraction computeContentWeight(List<ItemStack> stacks) {
        Fraction fraction = Fraction.ZERO;

        ItemStack itemStack;
        for (Iterator var2 = stacks.iterator(); var2.hasNext(); fraction = fraction.add(getWeight(itemStack).multiplyBy(Fraction.getFraction(itemStack.getCount(), 1)))) {
            itemStack = (ItemStack) var2.next();
        }

        return fraction;
    }

    static Fraction getWeight(ItemStack itemStack) {
        LoadableWeaponContents contents = (LoadableWeaponContents) itemStack.get(MFTEDataComponentRegistries.LOADABLE_WEAPON_CONTENTS);
        if (contents != null)
            return PROJECTILE_IN_STORAGE_WEIGHT.add(contents.weight);
        return Fraction.ONE;
    }

    public ItemStack getItemUnsafe(int p_330802_) {
        return (ItemStack)this.items.get(p_330802_);
    }

    public Stream<ItemStack> itemCopyStream() {
        return this.items.stream().map(ItemStack::copy);
    }

    public Iterable<ItemStack> items() {
        return this.items;
    }

    public Iterable<ItemStack> itemsCopy() {
        return Lists.transform(this.items, ItemStack::copy);
    }

    public int size() {
        return this.items.size();
    }

    public Fraction weight() {
        return this.weight;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else {
            boolean var10000;
            if (object instanceof LoadableWeaponContents) {
                LoadableWeaponContents loadableWeaponContents = (LoadableWeaponContents) object;
                var10000 = this.weight.equals(loadableWeaponContents.weight) && ItemStack.listMatches(this.items, loadableWeaponContents.items);
            } else {
                var10000 = false;
            }

            return var10000;
        }
    }

    public int hashCode() {
        return ItemStack.hashStackList(this.items);
    }

    public String toString() {
        return "LoadableWeaponContents" + String.valueOf(this.items);
    }

    static {
        CODEC = ItemStack.CODEC.listOf().xmap(LoadableWeaponContents::new, (component) -> {
            return component.items;
        });
        STREAM_CODEC = ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()).map(LoadableWeaponContents::new, (function) -> {
            return function.items;
        });
        PROJECTILE_IN_STORAGE_WEIGHT = Fraction.getFraction(1, 16);
    }

    public static class Mutable {
        private final List<ItemStack> items;
        private Fraction weight;

        public Mutable(LoadableWeaponContents contents) {
            this.items = new ArrayList(contents.items);
            this.weight = contents.weight;
        }

        public List<ItemStack> getItems() {
            return Lists.transform(this.items, ItemStack::copy);
        }

        public int tryInsert(ItemStack itemStack) {
            if (!itemStack.isEmpty() && itemStack.getItem().canFitInsideContainerItems()) {
                int i = 1;
                this.weight = this.weight.add(LoadableWeaponContents.getWeight(itemStack).multiplyBy(Fraction.getFraction(i, 1)));
                this.items.add(0, itemStack.split(i));
                return i;
            } else {
                return 0;
            }
        }

        @Nullable
        public ItemStack removeOne() {
            if (this.items.isEmpty()) {
                return null;
            } else {
                ItemStack itemstack = ((ItemStack)this.items.remove(0)).copy();
                this.weight = this.weight.subtract(LoadableWeaponContents.getWeight(itemstack).multiplyBy(Fraction.getFraction(itemstack.getCount(), 1)));
                return itemstack;
            }
        }

        public Fraction weight() {
            return this.weight;
        }

        public LoadableWeaponContents toImmutable() {
            return new LoadableWeaponContents(List.copyOf(this.items), this.weight);
        }
    }
}
