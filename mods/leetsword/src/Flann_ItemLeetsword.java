// Coded by Flann

package mods.leetsword.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class Flann_ItemLeetsword extends ItemSword{
	
	public String t, name;
	
	public Flann_ItemLeetsword(int i, String displayName, String tex, EnumToolMaterial par2) {
		super(i, par2);
		setCreativeTab(CreativeTabs.tabCombat);
		t = tex;
		name = displayName;
	}
	
	@Override
	public String getItemDisplayName(ItemStack is){
		return name;
	}
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void registerIcons(IconRegister IR){
		this.itemIcon = IR.registerIcon(t);
	}
	
	/**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        par1ItemStack.damageItem(2, par3EntityLivingBase);
        return true;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
    {
        return false;
    }
}
