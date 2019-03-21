package chatbot.api.skillHub.services;

import chatbot.api.common.domain.ResponseDto;
import chatbot.api.mappers.HubMapper;
import chatbot.api.skillHub.domain.HubInfoVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EditHub {

    private HubMapper hubMapper;



    public ResponseDto editer(HubInfoVo hubInfoVo) {

        System.out.println("test2");
        // 매퍼 작성...
        try {
            System.out.println("test3");

            hubMapper.editHubAboutIpAndPort(hubInfoVo.getHubSequence(),
                                            hubInfoVo.getExternalIp(),
                                            hubInfoVo.getInternalIp(),
                                            hubInfoVo.getExternalPort(),
                                            hubInfoVo.getInternalPort());

            System.out.println("test4");

        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println("test5");

        return ResponseDto.builder().build();
    }
}
