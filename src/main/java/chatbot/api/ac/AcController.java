package chatbot.api.ac;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.common.domain.kakao.openbuilder.RequestDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import static chatbot.api.ac.utils.AcConstants.*;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController
@AllArgsConstructor
public class AcController {

	private Environment env;


	@PostMapping(value = "/module/ac/{command}/v1")
	public ResponseDto AcMessage(@PathVariable String command, @RequestBody RequestDto requestDto) {
		String kakaoUserId = requestDto.getUserRequest().getUser().getId();
		String utterance = requestDto.getUserRequest().getUtterance();       // 발화 내용 얻는 코드
		String kakaoUserType = requestDto.getUserRequest().getUser().getType();
		String msg = null;

		// 추후 추상화 예정

		// 사용자 아이디가 무엇인지에 따라서 DB에서 ip 주소를 긇어오는 코드 필요하나...
		// 지금은 데이터 송수신이 제대로 동작하는지 확인하는것이 목적이기 때문에
		// 일단은 고정 ip, Iot hub url

		String url = "http://203.250.32.29:3000/";
		String responseByHub;                                 // 허브로부터 응답받을 json 형식의 string
		RestTemplate restTemplate = new RestTemplate();       // 부트 코드 내에서 다른 REST API 호출하는 객체
		long dataByHub = 0;                                   // json 으로 받은 데이터중 id 값이 "data" 의 value를 저장할 변수


		// iot hub로 restful api 호출하는 코드 작성, {"data":4,"test":"hello"} json string 형태로 데이터를 받아옴
		responseByHub = restTemplate.postForObject(url, null, String.class);

		// hub로부터 받아온 데이터를 파싱하는 코드
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(responseByHub);
			dataByHub = (long) jsonObject.get("data");
			System.out.println("data >> " + dataByHub);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 얻은 데이터 값이 무엇인지에 따라서 알맞은 응답 제공
		int data = (int) dataByHub;
		if (command.equals("on")) {
			switch (data) {
				case 4:  // 4 : 켜기 성공
					System.out.println("켜기 성공");           msg = AC_ON_SUCCESS;
					break;
				case 3:  // 3 : 등록된 모듈이 없어요
					System.out.println("등록된 모듈이 없어요"); msg = AC_NONE_REGISTERED;
					break;
				case 2:  // 2 : 이미 켜져있어요
					System.out.println("이미 켜져있어요!");     msg = AC_ALREADY_ON;
					break;
				default:
					System.out.println("켜기 실패");           msg = AC_ON_FAIL;
					break;
			}
		} else if (command.equals("off")) {
			switch (data) {
				case 5:  // 4 : 끄기 성공
					System.out.println("끄기 성공");           msg = AC_OFF_SUCCESS;
					break;
				case 3:  // 3 : 등록된 모듈이 없어요
					System.out.println("등록된 모듈이 없어요"); msg = AC_NONE_REGISTERED;
					break;
				case 1:  // 1 : 이미 꺼져있어요
					System.out.println("이미 꺼져있어요!");     msg = AC_ALREADY_OFF;
					break;
				default:
					System.out.println("끄기 실패");           msg = AC_OFF_FAIL;
					break;
			}
		}

		return ResponseDto.builder().msg(msg).status(HttpStatus.OK).data(null).build();
	}


	@GetMapping("/profile")
	public String getProfile() {
		return Arrays.stream(env.getActiveProfiles()).findFirst().orElse("");
	}
}


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