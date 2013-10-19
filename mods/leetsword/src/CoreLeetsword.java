// Coded by Flann

package mods.leetsword.src;

import static net.minecraftforge.common.Property.Type.BOOLEAN;

import java.io.File;

import mods.leetsword.src.Flann_WorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@Mod(modid = "Leetsword", name = "Leetsword Mod", version = "#6")
public class CoreLeetsword {
	public static final String texLoc = "leetsword:";
	
	public static File configFile;
	
	public static int BlockID = 820; // 1 block
	public static int ItemID = 14010; // 2 items
	
	public static boolean enableCoreSteel			= false;
	public static boolean enableCoreStickSteel		= false;
	public static boolean enableCoreIngotRedstone	= false;
	public static boolean commonConfig;
	
	public static int idSword, idIngot, idBow, idOre;
	public static Item leetsword, ingotLeet, leetbow;
	public static Block oreLeet;
	
	public static EnumToolMaterial leet = EnumHelper.addToolMaterial("leet", 0, 1, 1.0F, 8000000-4, 14);
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		String categoryBlock, categoryItem, categoryGeneral, categoryEnable, categoryGeneralCore;
		
		Configuration coreConfig = new Configuration(new File(event.getSuggestedConfigurationFile().getPath().replace("Leetsword", "FlannCore")));
		coreConfig.load();
			commonConfig = coreConfig.get("core", "combineConfigs", false).getBoolean(false);
			if(commonConfig){
				String modname = "leetsword";
				categoryBlock = modname+"_Block";
				categoryItem = modname+"_Item";
				categoryGeneral = modname+"_General";
				categoryEnable = modname+"_Enable";
				categoryGeneralCore = "core_General";
			}else{
				categoryBlock = "block";
				categoryItem = "item";
				categoryGeneral = "general";
				categoryEnable = "enable";
				categoryGeneralCore = "general";
			}
			if(enableCoreSteel)
				set(coreConfig, categoryGeneralCore, "enableSteel", true);
			if(enableCoreStickSteel)
				set(coreConfig, categoryGeneralCore, "enableStickSteel", true);
			if(enableCoreIngotRedstone)
				set(coreConfig, categoryGeneralCore, "enableIngotRedstone", true);
		coreConfig.save();
		
		Configuration config;
		if(commonConfig == false){
			config = new Configuration(event.getSuggestedConfigurationFile());
		}else{
			config = coreConfig;
		}
		
		config.load();
			idSword = config.get(categoryItem, "leetsword", ItemID + 0).getInt();
			idIngot = config.get(categoryItem, "ingotLeet", ItemID + 1).getInt();
			idBow 	= config.get(categoryItem, "leetbow"	, ItemID + 2).getInt();
			
			idOre 	= config.get(categoryBlock, "oreLeet", BlockID + 0).getInt();
		config.save();
		
		init_pre();
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		init();
	}
	
	public void init_pre(){
		leetsword = new Flann_ItemLeetsword(idSword, "1337sword Of H4x0rness", texLoc+"leetsword", leet).setUnlocalizedName("leetsword");
		ingotLeet = new Flann_ItemIngotLeet(idIngot, "1337 Ingot", texLoc+"ingotLeet").setUnlocalizedName("ingotLeet");
		leetbow	  = new Flann_ItemLeetbow(idBow, "1337 Bow").setUnlocalizedName("leetbow").setTextureName(texLoc+"leetbow");
		
		oreLeet = new Flann_BlockOreLeet(idOre, texLoc+"oreLeet", Material.rock).setHardness(6.0F).setResistance(100.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreLeet");
		
		GameRegistry.registerBlock(oreLeet);
		
		LanguageRegistry.addName(oreLeet, "1337 Ore");
		
		GameRegistry.registerWorldGenerator(new Flann_WorldGenerator());
	}
	public void init(){
		MinecraftForge.setBlockHarvestLevel(oreLeet, "pickaxe", 3);
		
		OreDictionary.registerOre("swordLeet", leetsword);
		OreDictionary.registerOre("ingotLeet", ingotLeet);
		OreDictionary.registerOre("bowLeet", leetbow);
		OreDictionary.registerOre("oreLeet", oreLeet);
		
		GameRegistry.addRecipe(new ItemStack(leetsword, 1), " I ", " I ", " S ", 'I', ingotLeet, 'S', Item.stick);
		GameRegistry.addSmelting(oreLeet.blockID, new ItemStack(ingotLeet, 2),20);
		GameRegistry.addRecipe(new ItemStack(leetbow, 1), " #@", "$ @", " #@", '@', Item.silk, '$', Item.stick, '#', ingotLeet);
	}
	
	public Property set(Configuration config, String category, String key, boolean defaultValue)
    {
		return set(config, category, key, defaultValue, null);
    }
	public Property set(Configuration config, String category, String key, boolean defaultValue, String comment)
    {
        Property prop = config.get(category, key, Boolean.toString(defaultValue), comment, BOOLEAN);
        prop.set(defaultValue);
        return prop;
    }
}
