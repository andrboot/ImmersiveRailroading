package cam72cam.immersiverailroading;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.*;
import org.apache.logging.log4j.Logger;

import cam72cam.immersiverailroading.Config.ConfigDebug;
import cam72cam.immersiverailroading.proxy.ChunkManager;
import cam72cam.immersiverailroading.proxy.CommonProxy;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = ImmersiveRailroading.MODID, name="ImmersiveRailroading", version = ImmersiveRailroading.VERSION, acceptedMinecraftVersions = "[1.12,1.13)", dependencies = "required-after:trackapi@[1.1,);after:immersiveengineering")
public class ImmersiveRailroading
{
	public static String modID() {
		ImmersiveRailroading.MODID = new Random().nextInt() + "";
		return MODID;
	}


	public ImmersiveRailroading() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("BOO");
		Field modsField = Loader.class.getDeclaredField("mods");
        modsField.setAccessible(true);
		List<ModContainer> x = (List<ModContainer>) modsField.get(Loader.instance());
		List<ModContainer> nl = new ArrayList<>(x);
		ModMetadata meta = new ModMetadata();
		meta.modId = "testmodid";
		meta.name = "Test Mod";
		nl.add(new DummyModContainer(meta) {
			public Object getMod() {
				return new TestMod();
			}

			@Override
			public boolean registerBus(EventBus bus, LoadController controller) {
				return true;
			}

			@Override
			public File getSource() {
				return new File("test.jar");
			}

		});
		modsField.set(Loader.instance(), nl);

		Field controllerField = Loader.class.getDeclaredField("modController");
		controllerField.setAccessible(true);
		LoadController controller = (LoadController) controllerField.get(Loader.instance());
		//controller.getActiveModList().add(nl.get(nl.size()-1));

		Field amlField = LoadController.class.getDeclaredField("activeModList");
		amlField.setAccessible(true);
		amlField.set(controller, new ArrayList<>());


		controller.buildModList(null);

	}

    public static String MODID = "immersiverailroading";
    public static final String VERSION = "1.5.0";
	public static final int ENTITY_SYNC_DISTANCE = 512;
    
	private static Logger logger;
	public static ImmersiveRailroading instance;

	@SidedProxy(clientSide="cam72cam.immersiverailroading.proxy.ClientProxy", serverSide="cam72cam.immersiverailroading.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	private ChunkManager chunker;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        logger = event.getModLog();
        instance = this;
        
        World.MAX_ENTITY_RADIUS = Math.max(World.MAX_ENTITY_RADIUS, 32);
        
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) throws IOException {
		chunker = new ChunkManager();
		chunker.init();
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    	proxy.serverStarting(event);
    }
    
    public static void debug(String msg, Object...params) {
    	if (logger == null) {
    		System.out.println("DEBUG: " + String.format(msg, params));
    		return;
    	}
    	
    	if (ConfigDebug.debugLog) {
    		logger.info(String.format(msg, params));
    	}
    }
    public static void info(String msg, Object...params) {
    	if (logger == null) {
    		System.out.println("INFO: " + String.format(msg, params));
    		return;
    	}
    	
    	logger.info(String.format(msg, params));
    }
    public static void warn(String msg, Object...params) {
    	if (logger == null) {
    		System.out.println("WARN: " + String.format(msg, params));
    		return;
    	}
    	
    	logger.warn(String.format(msg, params));
    }
    public static void error(String msg, Object...params) {
    	if (logger == null) {
    		System.out.println("ERROR: " + String.format(msg, params));
    		return;
    	}
    	
    	logger.error(String.format(msg, params));
    }
	public static void catching(Throwable ex) {
    	if (logger == null) {
    		ex.printStackTrace();
    		return;
    	}
    	
		logger.catching(ex);
	}
}
