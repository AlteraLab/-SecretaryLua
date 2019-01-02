package chatbot.api.ac;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import chatbot.api.ac.utils.AcConstants;
import chatbot.api.common.RequestDto;
import chatbot.api.common.ResponseDto;
import chatbot.api.common.services.DeviceCommonServiceImpl;
import chatbot.api.common.utils.CommonConstants;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AcController {

	DeviceCommonServiceImpl deviceCommonService;

	@PostMapping(value = "/module/ac/{command}/v1")
	public ResponseDto message(@PathVariable String command, @RequestBody RequestDto requestDto) {
		String kakaoUserId = requestDto.getUserRequest().getUser().getId();
		String msg = null;
		System.out.println("commanded:" + kakaoUserId);

		/*
		if (deviceCommonService.isExist(kakaoUserId)) { // 해당 사용자에게 등록된 장비가 존재하는지
			if (deviceCommonService.isOn(kakaoUserId)) { // 해당 사용자에게 등록된 장비가 켜져있는지
				if (deviceCommonService.isLock(kakaoUserId)) {// 해당 사용자에 등록된 장비가 이미 작업을 수행중인지
					// 명령 전송
					msg = AcConstants.AC_ON_SUCCESS;
				} 
				else {
					msg = CommonConstants.DEVICE_BLOCK;
				}
			}
			else {
				
			}
		} 
		else {
			msg = CommonConstants.DEVICE_NOT_EXIST;
		}
		*/

		return ResponseDto.builder().msg(msg).status(HttpStatus.OK).data(null).build();
	}
}
