package mods.leetsword.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Flann_ItemIngotLeet extends Item {
	
	public String t, name;
	
	public Flann_ItemIngotLeet(int par1, String displayName, String tex) {
		super(par1);
		setCreativeTab(CreativeTabs.tabMaterials);
		t = tex;
		name = displayName;
	}
	
	@Override
	public String getItemDisplayName(ItemStack is){
		return name;
	}
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void registerIcons(IconRegister IR){
		this.itemIcon = IR.registerIcon(t);
	}
}
