package quaternary.exutilfix;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import net.minecraftforge.fml.common.eventhandler.ListenerList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

@Mod(
				modid = ExUtilFix.MODID,
				name = ExUtilFix.NAME,
				version = ExUtilFix.VERSION,
				dependencies = "required-before:extrautils2"
)
public class ExUtilFix {
	public static final String MODID = "extrautils_fixes";
	public static final String NAME = "Extra Utilities Fixes";
	public static final String VERSION = "GRADLE:VERSION";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	public static boolean oofSlimeEvent = true;
	public static boolean suppressErrors = true;
	
	public static Logger fmlLog;
	public static Field logField;
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		Configuration c = new Configuration(e.getSuggestedConfigurationFile(), "0");
		
		oofSlimeEvent = c.get("general", "doIt", true, "Should the extra utilities superflat slime canceller be the big delet").setRequiresMcRestart(true).getBoolean();
		
		suppressErrors = c.get("general", "hackyErrorSuppressionStuff", true, "Should some hacky stuff be done to prevent a bunch of harmless extra utilities error log spam").getBoolean();
		
		if(c.hasChanged()) c.save();
		
		///
		
		if(suppressErrors) {
			LOGGER.error("Performing really hacky ExUtil log error suppressing stuff - YOU CAN DISABLE THIS!!");
			logField = ReflectionHelper.findField(FMLLog.class, "log");
			fmlLog = FMLLog.log;
			try {
				EnumHelper.setFailsafeFieldValue(logField, null, new
								ExtraUtilitiesErrorSuppressingLog((org.apache.logging.log4j.core.Logger) FMLLog.log));
			} catch (Exception eeeee) {
				LOGGER.error("It didn't work!!!!", eeeee);
			}
		}
	}
	
	@Mod.EventHandler
	public static void postinit(FMLPostInitializationEvent e) {
		if(suppressErrors) {
			LOGGER.info("Undoing the hacky log stuff since it's not needed anymore");
			try {
				EnumHelper.setFailsafeFieldValue(logField, null, fmlLog);
			} catch (Exception eeeee) {
				LOGGER.error("It didn't work!!!!", eeeee);
			}
		}
		
		if(!oofSlimeEvent) return;
		
		ListenerList listenerList = new LivingSpawnEvent.CheckSpawn(null, null, 0, 0, 0, null).getListenerList();
		IEventListener[] handlers = listenerList.getListeners(0);
		
		for(int i = 0; i < handlers.length; i++) {
			//Hey look, calling toString on a class :D
			String s = handlers[i].toString();
			if(s.contains("com.rwtema.extrautils2.eventhandlers.SlimeSpawnHandler")) {
				LOGGER.info("Unregistering event handler " + s);
				listenerList.unregister(0, handlers[i]);
				break;
			}
		}
	}
}
