package chatbot.api.ac;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.domain.kakao.openbuilder.RequestDto;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController
@AllArgsConstructor
public class AcController {

	private Environment env;

	@PostMapping(value = "/module/ac/{command}/v1")
	public ResponseDto message(@PathVariable String command, @RequestBody RequestDto requestDto) {
		String kakaoUserId = requestDto.getUserRequest().getUser().getId();
		String msg = null;
		System.out.println("commanded:" + kakaoUserId);
		System.out.println("botUserProperties:" + requestDto.getUserRequest().getUser().getProperties().getAppUserId());

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

	@GetMapping("/profile")
	public String getProfile(){
		return Arrays.stream(env.getActiveProfiles()).findFirst().orElse("");
	}
}
