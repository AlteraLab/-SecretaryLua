package chatbot.api.common.services;

import org.springframework.stereotype.Service;

@Service
public class DeviceCommonServiceImpl {
	
	public boolean lock(String id) {
		return false;
	}
	
	public boolean isLock(String id) {
		return false;
	}
	
	public boolean isExist(String id) {
		return false;
	}
	
	public boolean isOn(String id) {
		return false;
	}
}
