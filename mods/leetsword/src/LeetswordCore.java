// Coded by Flann

package mods.leetsword.src;

import java.io.File;

import mods.leetsword.src.WorldGenerator;
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
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.EnumHelper;

@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@Mod(modid = LeetswordCore.modid, name = "Leetsword Mod", version = "#1")
public class LeetswordCore {
	public static final String modid = "leetsword";

	public static File configFile;

	public static int BlockID = 1000;
	public static int ItemID = 14144;

	public static int idSword, idIngot, idOre;
	public static Item leetsword, ingotLeet;
	public static Block oreLeet;

	public static EnumToolMaterial leet = EnumHelper.addToolMaterial("leet", 0,
			1, 1.0F, 80000, 0);

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		configFile = event.getSuggestedConfigurationFile();
		Configuration config = new Configuration(configFile);
		config.load();

		idSword = config.get("items", "leetsword", ItemID + 0).getInt();
		idIngot = config.get("items", "ingotLeet", ItemID + 1).getInt();

		idOre = config.get("blocks", "oreLeet", BlockID + 0).getInt();

		config.save();
	}

	@Init
	public void load(FMLInitializationEvent event) {

		leetsword = new ItemLeetsword(idSword, leet, "leetsword")
				.setUnlocalizedName("leetsword");
		ingotLeet = new ItemIngotLeet(idIngot, "ingotLeet")
				.setUnlocalizedName("ingotLeet");

		oreLeet = new BlockOreLeet(idOre, Material.rock).setHardness(6.0F)
				.setResistance(100.0F).setStepSound(Block.soundStoneFootstep)
				.setUnlocalizedName("oreLeet");

		GameRegistry.registerBlock(oreLeet);
		MinecraftForge.setBlockHarvestLevel(oreLeet, "pickaxe", 3);

		LanguageRegistry.addName(leetsword, "1337sword Of H4x0rness");
		LanguageRegistry.addName(ingotLeet, "1337 Ingot");
		LanguageRegistry.addName(oreLeet, "1337 Ore");

		GameRegistry.addRecipe(new ItemStack(leetsword, 1), " I ", " I ",
				" S ", 'I', ingotLeet, 'S', Item.stick);
		GameRegistry.addSmelting(oreLeet.blockID, new ItemStack(ingotLeet, 2),
				20);

		GameRegistry.registerWorldGenerator(new WorldGenerator());

	}
}
