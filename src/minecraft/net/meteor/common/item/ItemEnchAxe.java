package net.meteor.common.item;

import net.meteor.common.LangLocalization;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemEnchAxe extends ItemAxe
{
	protected Enchantment enchantment;
	protected int level;

	public ItemEnchAxe(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, par2EnumToolMaterial);
	}

	public Item setEnch(Enchantment ench, int lvl) {
		this.enchantment = ench;
		this.level = lvl;
		return this;
	}
	
	@Override
	public int getDamage(ItemStack stack) {
		if (!stack.isItemEnchanted() && !isRestricted(stack)) {
			stack.addEnchantment(this.enchantment, this.level);
			NBTTagCompound tag = stack.getTagCompound();
			tag.setBoolean("enchant-set", true);
			stack.setTagCompound(tag);
		}
		return super.getDamage(stack);
	}

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		return LangLocalization.get(this.getUnlocalizedName(par1ItemStack) + ".name").trim();
	}
	
	private boolean isRestricted(ItemStack item) {
		if (item.hasTagCompound()) {
			NBTTagCompound tag = item.getTagCompound();
			if (tag.hasKey("enchant-set")) {
				return tag.getBoolean("enchant-set");
			} else {
				tag.setBoolean("enchant-set", false);
				item.setTagCompound(tag);
			}
		}
		return false;
	}
}