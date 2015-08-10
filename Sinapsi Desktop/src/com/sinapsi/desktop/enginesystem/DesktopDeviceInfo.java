package com.sinapsi.desktop.enginesystem;

import com.sinapsi.desktop.DesktopConsts;
import com.sinapsi.desktop.controller.RootAccess;
import com.sinapsi.engine.SinapsiPlatforms;
import com.sinapsi.engine.annotations.AdapterImplementation;
import com.sinapsi.engine.annotations.Component;
import com.sinapsi.engine.annotations.InitializationNeededObjects;
import com.sinapsi.engine.modules.common.DeviceInfoAdapter;
import com.sinapsi.engine.system.PlatformDependantObjectProvider;
import com.sinapsi.model.module.ModuleMember;

@ModuleMember(DefaultLinuxModules.ANTARES_LINUX_DESKTOP_MODULE_NAME)
@AdapterImplementation(
		value = DeviceInfoAdapter.ADAPTER_DEVICE_INFO,
		platform = SinapsiPlatforms.PLATFORM_LINUX_DESKTOP
)
@InitializationNeededObjects({
	PlatformDependantObjectProvider.ObjectKey.LINUX_ROOT_PERMISSIONS
})
public class DesktopDeviceInfo implements DeviceInfoAdapter {

	private String rootPsw;

	@Override
	public void init(Object... requiredPlatformDependantObjects) {
		String rootPsw = (String) requiredPlatformDependantObjects[0];
		this.rootPsw = rootPsw;
	}

	@Override
	public String getDeviceName() {
		// execute command with root privilege
		String out = null;
		try {
			Process p = RootAccess.runFromRoot("hdparm -I /dev/sd? | grep 'Serial\\ Number'", rootPsw);
			out = RootAccess.streamToString(p.getInputStream());
			out = out.substring(out.indexOf(":") + 1, out.indexOf("\n"));
		} catch(Exception e) {
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public String getDeviceModel() {
		// execute command with root privilege
		String out = null;
		try {
			Process p = RootAccess.runFromRoot("dmidecode | grep \"Manufacturer\"", rootPsw);
			out = RootAccess.streamToString(p.getInputStream());
			out = out.substring(out.indexOf(":") + 1, out.indexOf("\n"));
		} catch(Exception e) {
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public String getDeviceType() {
		return "PC Linux";
	}

	public int getVersion() {
		return DesktopConsts.CLIENT_VERSION;
	}


}