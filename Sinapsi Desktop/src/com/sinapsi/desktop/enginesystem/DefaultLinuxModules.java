package com.sinapsi.desktop.enginesystem;

import com.sinapsi.engine.SinapsiPlatforms;
import com.sinapsi.engine.SinapsiVersions;
import com.sinapsi.engine.modules.DefaultCoreModules;
import com.sinapsi.model.impl.FactoryModel;
import com.sinapsi.model.module.SinapsiModule;

public class DefaultLinuxModules {

	public static final String ANTARES_LINUX_DESKTOP_MODULE_NAME = "ANTARES_LINUX_DESKTOP_MODULE";

	public static final SinapsiModule ANTARES_LINUX_DESKTOP_MODULE = new FactoryModel().newModule(
			SinapsiVersions.ANTARES.ordinal(),
			SinapsiVersions.ANTARES.ordinal(),
			ANTARES_LINUX_DESKTOP_MODULE_NAME,
			DefaultCoreModules.SINAPSI_TEAM_DEVELOPER_ID,
			SinapsiPlatforms.PLATFORM_LINUX_DESKTOP,
			null,
			null,
			new String[]{DefaultCoreModules.ROLE_ANTARES_COMMON_ADAPTERS},
			null,

			DesktopNotificationAdapter.class);

	private DefaultLinuxModules(){} //Don't instantiate pls
}
