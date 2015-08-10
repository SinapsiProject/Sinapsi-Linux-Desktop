package com.sinapsi.desktop.service;

import java.util.List;

import com.sinapsi.client.SyncManager.ConflictResolutionCallback;
import com.sinapsi.client.background.SinapsiDaemonThread;
import com.sinapsi.client.background.SinapsiDaemonThread.DBManagerProvider;
import com.sinapsi.client.persistence.DiffDBManager;
import com.sinapsi.client.persistence.LocalDBManager;
import com.sinapsi.client.persistence.UserSettingsFacade;
import com.sinapsi.client.persistence.syncmodel.MacroSyncConflict;
import com.sinapsi.client.web.OnlineStatusProvider;
import com.sinapsi.client.web.SinapsiWebServiceFacade;
import com.sinapsi.client.websocket.WSClient;
import com.sinapsi.desktop.enginesystem.DefaultLinuxModules;
import com.sinapsi.desktop.enginesystem.DesktopActivationManager;
import com.sinapsi.desktop.enginesystem.DesktopDeviceInfo;
import com.sinapsi.desktop.log.DesktopClientLog;
import com.sinapsi.desktop.persistence.DesktopDiffDBManager;
import com.sinapsi.desktop.persistence.DesktopLocalDBManager;
import com.sinapsi.desktop.persistence.LinuxUserSettingsFacade;
import com.sinapsi.engine.MacroEngine;
import com.sinapsi.engine.execution.RemoteExecutionDescriptor;
import com.sinapsi.engine.log.LogMessage;
import com.sinapsi.engine.log.SinapsiLog;
import com.sinapsi.engine.log.SystemLogInterface;
import com.sinapsi.engine.modules.DefaultCoreModules;
import com.sinapsi.engine.requirements.DefaultRequirementResolver;

import com.sinapsi.engine.system.PlatformDependantObjectProvider;
import com.sinapsi.engine.system.SystemFacade;
import com.sinapsi.model.DeviceInterface;
import com.sinapsi.model.UserInterface;

public class BackgroundService implements Runnable, OnlineStatusProvider, SinapsiDaemonThread.DaemonCallbacks  {

	private SinapsiDaemonThread daemon;
	private UserSettingsFacade userSettings;
	private SinapsiLog sinapsiLog;
	private String rootPassw;
	private DesktopDeviceInfo deviceInfo;

	public BackgroundService(String rootPassw){
		this.rootPassw = rootPassw;
		this.deviceInfo = new DesktopDeviceInfo();
		deviceInfo.init(rootPassw);

		userSettings = new LinuxUserSettingsFacade();

		sinapsiLog = new SinapsiLog();
		sinapsiLog.addLogInterface(new SystemLogInterface() {

			@Override
			public void printMessage(LogMessage lm) {
				System.out.println(lm.getTag() + ": " + lm.getMessage());
			}
		});

		daemon = new SinapsiDaemonThread(
				userSettings,
				sinapsiLog,
				new DesktopClientLog(),
				null,
				null,
				new DesktopActivationManager(),
				new DefaultRequirementResolver() {
					@Override
					public void resolveRequirements(SystemFacade sf) {
						sf.setRequirementSpec(DefaultCoreModules.REQUIREMENT_RESTARTABLE_MACRO_ENGINE, true);
						sf.setRequirementSpec(DefaultCoreModules.REQUIREMENT_SIMPLE_NOTIFICATIONS, true);
					}
				},
				new PlatformDependantObjectProvider() {
					@Override
					public Object getObject(ObjectKey key) throws ObjectNotAvailableException {
						switch (key) {
						case LINUX_ROOT_PERMISSIONS:
							return rootPassw; //TODO: pass an object which, with more security, handles root task requests, instead of password.
						case LOGGED_USER:
							return daemon.getUserSettings();
						default:
							throw new ObjectNotAvailableException(key.name());
						}
					}
				},
				new DBManagerProvider() {

					@Override
					public LocalDBManager openLocalDBManager(String fileName,
							com.sinapsi.engine.component.ComponentFactory componentFactory) {
						return new DesktopLocalDBManager();
					}

					@Override
					public DiffDBManager openDiffDBManager(String fileName) {
						return new DesktopDiffDBManager();
					}
				},
				this,
				this,
				DefaultCoreModules.ANTARES_CORE_MODULE,
				DefaultCoreModules.ANTARES_COMMON_COMPONENTS_MODULE,
				DefaultLinuxModules.ANTARES_LINUX_DESKTOP_MODULE);
	}

	@Override
	public boolean isOnline() {
		return false;
	}

	@Override
	public void run() {
		daemon.run();
	}

	@Override
	public void onEngineStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnginePaused() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserLogin(UserInterface user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserLogout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSyncConflicts(List<MacroSyncConflict> conflicts, ConflictResolutionCallback callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSyncFailure(Throwable e, boolean showError) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWebSocketError(Exception ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWebSocketClose(int code, String reason, boolean remote) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWebSocketOpen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWebSocketMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWebSocketUpdatedNotificationReceived() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWebSocketNewConnectionNotificationReceived() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWebSocketConnectionLostNotificationReceived() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onWebSocketRemoteExecutionDescriptorReceived(RemoteExecutionDescriptor red) {
		// TODO Auto-generated method stub
		return false;
	}

	public SinapsiWebServiceFacade getWeb() {
		return daemon.getWeb();
	}

	public void setDevice(DeviceInterface device) {
		daemon.setDevice(device);
	}

	public DesktopDeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void initEngine() {
		daemon.initEngine();
	}

	public WSClient getWSClient() {
		return daemon.getWSClient();
	}

	public MacroEngine getEngine() {
		return daemon.getEngine();
	}

}
