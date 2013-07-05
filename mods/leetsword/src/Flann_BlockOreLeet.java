// Coded by Flann

package mods.leetsword.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;

public class Flann_BlockOreLeet extends net.minecraft.block.Block{
	
	public String t;
	
	public Flann_BlockOreLeet(int i, String tex, Material par2) {
		super(i,par2);
		setCreativeTab(CreativeTabs.tabBlock);
		t = tex;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister IR){
		this.blockIcon = IR.registerIcon(t);
	}
	
}
