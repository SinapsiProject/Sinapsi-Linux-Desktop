package com.sinapsi.desktop.enginesystem;

import java.io.IOException;

import com.sinapsi.engine.SinapsiPlatforms;
import com.sinapsi.engine.annotations.AdapterImplementation;
import com.sinapsi.engine.annotations.InitializationNeededObjects;
import com.sinapsi.engine.modules.common.NotificationAdapter;
import com.sinapsi.model.module.ModuleMember;

@ModuleMember(DefaultLinuxModules.ANTARES_LINUX_DESKTOP_MODULE_NAME)
@AdapterImplementation(
		value = NotificationAdapter.ADAPTER_NOTIFICATION,
		platform = SinapsiPlatforms.PLATFORM_LINUX_DESKTOP)
@InitializationNeededObjects({})
public class DesktopNotificationAdapter implements NotificationAdapter {

	@Override
	public void init(Object... requiredPlatformDependantObjects) {
		//does nothing

	}

	@Override
	public void showSimpleNotification(String title, String message) {
		try {
		Process p = Runtime.getRuntime().exec(new String[] {"notify-send",title,message});
		p.waitFor();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}


}
