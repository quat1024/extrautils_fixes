package quaternary.exutilfix;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

@Mod.EventBusSubscriber(modid = ExUtilFix.MODID, value = Side.CLIENT)
public class ClientEvents {
	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent e) {
		if(ExUtilFix.suppressErrors) {
			Map<ResourceLocation, Exception> errors = ReflectionHelper.getPrivateValue(ModelLoader.class, e.getModelLoader(), "loadingExceptions");
			errors.entrySet().removeIf(entry -> entry.getKey().getNamespace().equals("extrautils2"));
			
			ExUtilFix.LOGGER.warn("Suppressed a huge pile of (harmless) model loading errors from extra utilities using its special snowflake model system, if you want them, you can turn this off in the config");
		}
	}
}
