// Coded by Flann

package mods.leetsword.src;

import java.io.File;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator {

	public Properties props;
	public int oreX, oreY, oreZ;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		//System.out.println();
		
		//dir = new File(DimensionManager.getCurrentSaveRootDirectory() + "\\flann");
		//props = new Properties(dir + "\\leetsword.cfg");
		
		//System.out.println(DimensionManager.getCurrentSaveRootDirectory());
		//System.out.println(DimensionManager.getCurrentSaveRootDirectory().getParent());
		//System.out.println(new File(DimensionManager.getCurrentSaveRootDirectory().getParent()).getParent());
		
		String dir = new File((new StringBuilder()).append(DimensionManager.getCurrentSaveRootDirectory()).append("/flann/").toString()).getPath();
		String propsFile = dir + "/leetsword.cfg";
		if (!new File(dir).exists()){
			new File(dir).mkdirs();
		}
		props = new Properties(propsFile);
		
		oreX = props.getInt("posX", 0);
		oreY = props.getInt("posY", 0);
		oreZ = props.getInt("posZ", 0);
		props.save();
		
		

		if (oreX == 0 && oreY == 0 && oreZ == 0) {

			int intX, intY, intZ;
			boolean done = false;
			do {
				intX = random.nextInt(2000) - 1000;
				intY = random.nextInt(27) + 3;
				intZ = random.nextInt(2000) - 1000;

				int b0 = world.getBlockId(intX, intY, intZ);
				int bUp = world.getBlockId(intX, intY + 1, intZ);
				int bDown = world.getBlockId(intX, intY - 1, intZ);
				int bX = world.getBlockId(intX + 1, intY, intZ);
				int bx = world.getBlockId(intX - 1, intY, intZ);
				int bZ = world.getBlockId(intX, intY, intZ + 1);
				int bz = world.getBlockId(intX, intY, intZ - 1);

				int ids = Block.stone.blockID;
				int idd = Block.dirt.blockID;
				int idb = Block.bedrock.blockID;

				if ((b0 == ids || b0 == idd)
						&& (bUp == ids || bUp == idd || bUp == idb)
						&& (bDown == ids || bDown == idd || bDown == idb)
						&& (bX == ids || bX == idd || bX == idb)
						&& (bx == ids || bx == idd || bx == idb)
						&& (bZ == ids || bZ == idd || bZ == idb)
						&& (bz == ids || bz == idd || bz == idb)) {
					done = true;
					props.setInt("posX", intX);
					props.setInt("posY", intY);
					props.setInt("posZ", intZ);
					oreX = intX;
					oreY = intY;
					oreZ = intZ;
					props.save();
					//System.out.println("Leetore generated: " + oreX + " - " + oreY + " - " + oreZ);
				}
			} while (done != true);
			world.setBlock(intX, intY, intZ, LeetswordCore.oreLeet.blockID);
		} else {
			if (world
					.getBlockId(oreX+1, oreY, oreZ+1) == LeetswordCore.oreLeet.blockID) {
				//System.out.println("No generation! Leetore already generated! "+ oreX + " - " + oreY+ " - " + oreZ);
			} else if (world.getBlockId(oreX, oreY,
					oreZ) == 0) {
				//System.out.println("Leetore already mined out.");
			} else {
				//System.out.println("Error while generating leetOre at "+ oreX + oreY + oreZ);
			}
		}
	}
}
