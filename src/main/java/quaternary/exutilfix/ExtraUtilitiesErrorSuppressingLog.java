package quaternary.exutilfix;

import org.apache.logging.log4j.core.Logger;

public class ExtraUtilitiesErrorSuppressingLog extends Logger { 
	public ExtraUtilitiesErrorSuppressingLog(Logger other) {
		super(other.getContext(), other.getName(), other.getMessageFactory());
	}
	
	private boolean warnedBrokenTileEntity = false;
	
	@Override
	public void warn(String message, Object a, Object b, Object c) {
		if(message.startsWith("Potentially Dangerous alternative prefix") && a.equals("xu2") && c.equals("extrautils2")) {
			if(!warnedBrokenTileEntity) {
				ExUtilFix.LOGGER.warn("Suppressed a huge pile of (harmless) warnings caused by extra utilities registering the wrong id for tile entities, if you want them, you can turn this off in the config");
				warnedBrokenTileEntity = true;
			}
			
			return;
		}
		
		super.warn(message, a, b, c);
	}
}
