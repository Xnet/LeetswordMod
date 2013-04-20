package mods.leetsword.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemIngotLeet extends Item {
	
	public String tex = "";
	
	public ItemIngotLeet(int par1, String t) {
		super(par1);
		setCreativeTab(CreativeTabs.tabMaterials);
		tex = t;
	}
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void updateIcons(IconRegister IR){
		this.iconIndex = IR.registerIcon(LeetswordCore.modid + ":" + tex);
	}
}
