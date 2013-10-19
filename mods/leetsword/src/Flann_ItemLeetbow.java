// Coded by Flann

package mods.leetsword.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class Flann_ItemLeetbow extends ItemBow {

	public static final String[] bowPullIconNameArray = new String[] {"_standby", "_pulling_0", "_pulling_1", "_pulling_2"};
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
	public String name;
	
	public Flann_ItemLeetbow(int id, String displayName) {
		super(id);
        this.maxStackSize = 1;
        this.setMaxDamage(2);
		setCreativeTab(CreativeTabs.tabCombat);
		name = displayName;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
	
	@Override
	/**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
		this.shootArrow(par1ItemStack, par2World, par3EntityPlayer, par4);
    }
	
	/**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void shootArrow(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        int j = this.getMaxItemUseDuration(par1ItemStack) - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        if (flag || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID))
        {
            float f = (float)j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;
			
            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);

            entityarrow.setIsCritical(true);
            entityarrow.setDamage(8000000);
            
            int k;
            if(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, par1ItemStack) >= 5 && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) >= 5){
            	k = 0;
            }else if(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, par1ItemStack) == 3 && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) == 1){
            	k = 1;
            }else{
            	k = 2;
            }
            switch(k){
            case 0:
            	break;
            case 1:
            	par1ItemStack.damageItem(par1ItemStack.getMaxDamage(), par3EntityPlayer);
            	break;
            case 2:
            	par1ItemStack.damageItem(par1ItemStack.getMaxDamage()+1, par3EntityPlayer);
            	break;
            }
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            
            if(!flag){
            	entityarrow.canBePickedUp = 1;
            	par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
            }else{
            	entityarrow.canBePickedUp = 2;
            }
            
            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(entityarrow);
            }
        }
    }
    
    public void shootArrow(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        if (flag || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID))
        {
            float f = 1.0F;
            
            EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);

            entityarrow.setIsCritical(true);
            entityarrow.setDamage(8000000);
            
            int k;
            if(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, par1ItemStack) >= 5 && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) >= 5){
            	k = 0;
            }else if(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, par1ItemStack) == 3 && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) == 1){
            	k = 1;
            }else{
            	k = 2;
            }
            switch(k){
            case 0:
            	break;
            case 1:
            	par1ItemStack.damageItem(par1ItemStack.getMaxDamage(), par3EntityPlayer);
            	break;
            case 2:
            	par1ItemStack.damageItem(par1ItemStack.getMaxDamage()+1, par3EntityPlayer);
            	break;
            }
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            
            if(!flag){
            	entityarrow.canBePickedUp = 1;
            	par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
            }else{
            	entityarrow.canBePickedUp = 2;
            }
            
            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(entityarrow);
            }
        }
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    @Override
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    @Override
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    @Override
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	/*
    	shootArrow(par1ItemStack, par2World, par3EntityPlayer);
    	return par1ItemStack;
    	//*/
    	//*
        ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return event.result;
        }

        if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }
        
        return par1ItemStack;//*/
    }

    @Override
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 1;
    }
    
    /*
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(this.func_111208_A() + "_standby");
        this.iconArray = new Icon[bowPullIconNameArray.length];

        for (int i = 0; i < this.iconArray.length; i++)
        {
            this.iconArray[i] = par1IconRegister.registerIcon(this.func_111208_A() + bowPullIconNameArray[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * used to cycle through icons based on their used duration, i.e. for the bow
     */
    /*
    public Icon getItemIconForUseDuration(int par1)
    {
        return this.iconArray[par1];
    }//*/
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister iconRegister) {
    	this.itemIcon = iconRegister.registerIcon(this.getIconString()+this.bowPullIconNameArray[0]);
        this.iconArray = new Icon[bowPullIconNameArray.length];
    	for (int i = 0; i < bowPullIconNameArray.length; i++) {
    		this.iconArray[i] = iconRegister.registerIcon(this.getIconString() + bowPullIconNameArray[i]);
    	}
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
    	if(player.getItemInUse() == null)
    		return itemIcon;
    	
    	int Pulling = stack.getMaxItemUseDuration() - useRemaining;
    	if (Pulling >= 18) {
    		return iconArray[3];
    	} else if (Pulling > 13) {
    		return iconArray[2];
    	} else if (Pulling > 0) {
    		return iconArray[1];
    	} else {
    		return iconArray[0];
    	}
    }
    
	@Override
	public String getItemDisplayName(ItemStack is){
		return name;
	}
}
