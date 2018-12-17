package chatbot.api.ac;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import chatbot.api.ac.utils.AcConstants;
import chatbot.api.common.RequestDto;
import chatbot.api.common.ResponseDto;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AcController {
	
	@PostMapping(value="/module/ac/{command}/v1")
	public ResponseDto message(@PathVariable String command, @RequestBody RequestDto requestDto){
		System.out.println("commanded:"+requestDto.getUserRequest().getUser().getId());
		System.out.println("utterance:"+requestDto.getUserRequest().getUtterance());
		
		return ResponseDto.builder()
				.msg(AcConstants.AC_ON_SUCCESS)
				.status(HttpStatus.OK)
				.dataset(null)
				.build();
	}
}
