// Coded by Flann

package mods.leetsword.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;

public class BlockOreLeet extends net.minecraft.block.Block{
	
	public BlockOreLeet(int i, Material par2) {
		super(i,par2);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister IR){
		this.blockIcon = IR.registerIcon(LeetswordCore.modid + ":" + this.getUnlocalizedName2());
	}
	
}
