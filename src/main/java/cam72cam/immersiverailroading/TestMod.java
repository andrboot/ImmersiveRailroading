package cam72cam.immersiverailroading;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;

public class TestMod {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        System.out.println("Hello World");
    }
}
