package chatbot.api.textbox.services;

import chatbot.api.textbox.domain.Build;
import chatbot.api.textbox.domain.textboxdata.BtnDTO;
import chatbot.api.textbox.repository.BuildRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static chatbot.api.textbox.utils.TextBoxConstants.BUTTON_TYPE_CONTROL;

@Slf4j
@Service
public class BuildCheckerService {

    @Autowired
    private BuildRepository buildRepository;



    public Boolean isControlTypeOf(BtnDTO curBtn) {
        log.info("================== isControlType 시작    ==================");
        Boolean returnBoolean = null;
        if(curBtn.getBtnType() == BUTTON_TYPE_CONTROL) {
            returnBoolean = true;
        } else {
            returnBoolean = false;
        }
        log.info("Return Boolean -> " + returnBoolean.toString());
        log.info("================== isControlType 끝    ==================");
        return returnBoolean;
    }


    // 하위 박스가 있는지 없는지 체크한다
    //      1. 하위 박스가 없다면 -> End Box 라는 의미이니까, 사용자에게 명령을 전달할거냐고 묻는다
    //      2. 하위 박스가 있다면 -> End Box 가 아니라는 의미이니까, Entry Box 때 했던것과 비슷한 작업을 수행한다
    public Boolean existLowerBox(String providerId) {
        log.info("================== exist lower box 시작    ==================");
        Boolean returnBoolean = null;
        Build reBuild = buildRepository.find(providerId);
        if(!(reBuild.getCurBox() == null)) {  // 하위 박스가 존재한다면
            log.info("하위 박스 존재 O");
            returnBoolean = true;
        } else { // 하위 박스가 존재하지 않는다면
            log.info("하위 박스 존재 X");
            returnBoolean = false;
        }
        log.info("================== exist lower box 종료    ==================");
        return returnBoolean;
    }



}
