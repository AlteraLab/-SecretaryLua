package chatbot.api.order.services;

import chatbot.api.common.domain.kakao.openbuilder.responseVer2.ResponseDtoVerTwo;
import chatbot.api.common.services.KakaoBasicCardService;
import chatbot.api.common.services.KakaoSimpleTextService;
import chatbot.api.mappers.HubMapper;
import chatbot.api.mappers.UserMapper;
import chatbot.api.order.domain.*;
import chatbot.api.order.domain.Response.ResponseDevInfo;
import chatbot.api.order.repository.MainOrderRepositoryImpl;
import chatbot.api.skillHub.domain.HubInfoDto;
import chatbot.api.user.domain.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static chatbot.api.order.utils.OrderConstans.*;

// allargusconstru
@Service
@Slf4j
public class OrderResponseService {

    private HubMapper hubmapper;

    private UserMapper userMapper;

    private KakaoBasicCardService kakaoBasicCardService;

    private KakaoSimpleTextService kakaoSimpleTextService;

    private MainOrderRepositoryImpl mainOrderRepository;

    private OrderSaveService orderSaveService;

    private RestTemplate restTemplate;

    // 가상 데이터 세팅
    //private DevOrder[] devs;
    private CmdOrder[] cmds;



    public OrderResponseService(HubMapper hubmapper, UserMapper userMapper, KakaoBasicCardService kakaoBasicCardService, KakaoSimpleTextService kakaoSimpleTextService, MainOrderRepositoryImpl mainOrderRepository, OrderSaveService orderSaveService, RestTemplate restTemplate) {
        this.hubmapper = hubmapper;
        this.userMapper = userMapper;
        this.kakaoBasicCardService = kakaoBasicCardService;
        this.kakaoSimpleTextService = kakaoSimpleTextService;
        this.mainOrderRepository = mainOrderRepository;
        this.orderSaveService = orderSaveService;
        this.restTemplate = restTemplate;

        //this.devs = new DevOrder[2];
        this.cmds = new CmdOrder[9];

        //this.devs[0] = DevOrder.builder().devIdentifier("AC 05").devMacAddr("12:12:12:12:12:12").build();
        //this.devs[1] = DevOrder.builder().devIdentifier("AC 04").devMacAddr("12:12:12:12:12:12").build();


        this.cmds[0] = CmdOrder.builder().cmdName("on").description("AC ON").cmdCode(1).parentCode(-1).blockId(BLOCK_ID_CODE_LIST).build();
        this.cmds[1] = CmdOrder.builder().cmdName("off").description("AC OFF").cmdCode(2).parentCode(-1).blockId(BLOCK_ID_CODE_LIST).build();

        this.cmds[2] = CmdOrder.builder().cmdName("time set").description("AC TIME SET").cmdCode(3).parentCode(1).blockId(BLOCK_ID_D_TIME_SET).build();
        this.cmds[3] = CmdOrder.builder().cmdName("off").description("AC OFF").cmdCode(4).parentCode(1).blockId(BLOCK_ID_CODE_LIST).build();

        this.cmds[4] = CmdOrder.builder().cmdName("Intensity control").description("INTENSITY CONTROL").cmdCode(5).parentCode(3).blockId(BLOCK_ID_D_BUTTON).btnList("강,중,약,자동").btnTitle("조절 버튼 목록입니다.").build();
        this.cmds[5] = CmdOrder.builder().cmdName("off").description("AC OFF").cmdCode(6).parentCode(3).blockId(BLOCK_ID_CODE_LIST).build();

        this.cmds[6] = CmdOrder.builder().cmdName("temp input").description("AC TEMP SET").cmdCode(7).parentCode(5).blockId(BLOCK_ID_D_DIRECT_INPUT).directTitle("온도를 입력하세요.").inputEx(26).build();
        this.cmds[7] = CmdOrder.builder().cmdName("off").description("AC OFF").cmdCode(8).parentCode(5).blockId(BLOCK_ID_CODE_LIST).build();

        this.cmds[8] = CmdOrder.builder().cmdName("off").description("AC OFF").cmdCode(9).parentCode(7).blockId(BLOCK_ID_CODE_LIST).build();
    }



    // 사용자가 이용할 수 있는 허브들 리턴
    public ResponseDtoVerTwo responserHubs(String providerId) {

        if (mainOrderRepository.find(providerId) != null) {
            mainOrderRepository.delete(providerId);
            log.info("이미 빌딩중인 명령을 삭제 완료 했습니다.");
        }

        if (providerId == null) {
            return kakaoBasicCardService.responserRequestPreSignUp();
        }


        UserInfoDto user = userMapper.getUserByProviderId(providerId).get();
/*        if (user == null) {
            return kakaoSimpleTextService.responserShortMsg("등록되지 않은 사용자 입니다.");
        }

        if (!(providerId.equals(user.getProviderId()))) {
            return kakaoSimpleTextService.responserShortMsg("동일한 사용자가 아닙니다.");
        }*/

        // 명령 시나리오대로 코드 작성

        // 데이터베이스에서 사용자가 사용할 수 있는 허브목록 Get
        ArrayList<HubInfoDto> hubs = hubmapper.getUserHubsByUserId(user.getUserId());
        if (hubs == null) {
            return kakaoSimpleTextService.responserShortMsg("사용 가능한 허브가 없습니다.");
        }

        orderSaveService.saverHubOrders(providerId, hubs);

        // 만약 사용가능한 허브가 1개 라면, 허브로 Resttemplate를 때린다.
        if (hubs.size() == 1) {
            String url = new String("http://" + hubs.get(0).getExternalIp() + ":" + hubs.get(0).getExternalPort() + "/dev");
            log.info("URL >> " + url);
            ResponseDevInfo responseDevInfo = restTemplate.getForObject(url, ResponseDevInfo.class);

            // devs를 redis에 저장하는 코드 작성.
            MainOrder reMainOrder = mainOrderRepository.find(providerId);
            reMainOrder.setSelectOrder(SelectOrder.builder()
                    .externalIp(hubs.get(0).getExternalIp())
                    .externalPort(hubs.get(0).getExternalPort())
                    .curIndex(-1)
                    .cmds(new SelectCmdOrder[10])
                    .build());
            log.info("HUBS >>>>>>>>>>> " + reMainOrder);

            //orderSaveService.saverDevOrders(providerId, devs);
            orderSaveService.saverDevOrders(providerId, responseDevInfo.getDevInfo());

            mainOrderRepository.update(reMainOrder);


            //if(devs.length == 1) {
            if(responseDevInfo.getDevInfo().length == 1) {
                // 여기서도 마찬가지로 devs[0].getDevMacAddr() 을 이용해서 DB에서 Cmds를 가져와야 한다.
                // ********************
                // CmdOrder[] cmds = null;

                //reMainOrder.getSelectOrder().setDevMacAddr(devs[0].getDevMacAddr());
                reMainOrder.getSelectOrder().setDevMacAddr(responseDevInfo.getDevInfo()[0].getDevMacAddr());
                mainOrderRepository.update(reMainOrder);
                orderSaveService.saverCmdOrders(providerId, cmds);

                return kakaoSimpleTextService.makerCmdsCard(providerId, -1);
            }

            //return kakaoSimpleTextService.makerDevsCard(devs);
            return kakaoSimpleTextService.makerDevsCard(responseDevInfo.getDevInfo());
        }

        return kakaoSimpleTextService.makerHubsCard(hubs);
    }



    // 사용자가 발화한 허브 번호를 파싱해서 매개변수로 받는다.
    public ResponseDtoVerTwo responserDevs(String providerId, int hubSeq) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);

        reMainOrder.setSelectOrder(SelectOrder.builder()
                .externalIp(reMainOrder.getHubOrderList()[hubSeq - 1].getExplicitIp())
                .externalPort(reMainOrder.getHubOrderList()[hubSeq - 1].getExplicitPort())
                .curIndex(-1)
                .cmds(new SelectCmdOrder[10])
                .build());

        mainOrderRepository.update(reMainOrder);

        String url = new String("http://" + reMainOrder.getSelectOrder().getExternalIp() + ":" + reMainOrder.getSelectOrder().getExternalPort() + "/dev");
        log.info("URL >> " + url);
        ResponseDevInfo responseDevInfo = restTemplate.getForObject(url, ResponseDevInfo.class);

        // devs를 redis에 저장하는 코드 작성.
        orderSaveService.saverDevOrders(providerId, responseDevInfo.getDevInfo());

        //if(devs.length == 1) {
        if(responseDevInfo.getDevInfo().length == 1) {
            // 여기서도 마찬가지로 devs[0].getDevMacAddr() 을 이용해서 DB에서 Cmds를 가져와야 한다.
            // ********************
            // CmdOrder[] cmds = null;

            //reMainOrder.getSelectOrder().setDevMacAddr(devs[0].getDevMacAddr());
            reMainOrder.getSelectOrder().setDevMacAddr(responseDevInfo.getDevInfo()[0].getDevMacAddr());
            mainOrderRepository.update(reMainOrder);
            orderSaveService.saverCmdOrders(providerId, cmds);

            return kakaoSimpleTextService.makerCmdsCard(providerId, -1);
        }

        //return kakaoSimpleTextService.makerDevsCard(devs);
        return kakaoSimpleTextService.makerDevsCard(responseDevInfo.getDevInfo());
    }



    // 사용자가 발화한 장치 번호를 파싱해서 매개변수로 받는다.
    // 모듈 선택 시점에 최초에는 responserCmds 를 실행해서 최상위 노드들의 명령 코드들을 반환한다.
    // 모듈 선택한 시점에 한 번만 쓰이는 메소드.
    public ResponseDtoVerTwo responserCmds(String providerId, int devSeq) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);
        reMainOrder.getSelectOrder().setDevMacAddr(reMainOrder.getDevOrderList()[devSeq - 1].getDevMacAddr());
        mainOrderRepository.save(reMainOrder);

        // 필수 구현 : DevIdentifier를 이용해서 데이터베이스에서 사용가능한 장비의 명령 정보들을 불러왔다고 치자.
        // 나중에 DB Schema 설계 이후 mapper 작성
        // DB에서 Cmds를 가져와야 한다.
        //**************************************

        // cmds를 redis에 저장하는 코드 작성.
        orderSaveService.saverCmdOrders(providerId, cmds);

        return kakaoSimpleTextService.makerCmdsCard(providerId, -1);
    }



    // 모듈 선택 시점에 최초에는 responserCmds 를 실행해서 최상위 노드들의 명령 코드들을 반환한다.
    // 그러나 그 이후에는 자식 노드들의 명령 코드들을 반환할 때는 아래의 메소드인 responserChildCmds 메소드가 쓰인다.
    public ResponseDtoVerTwo responserChildCmds(String providerId, int selectedCode, String strCheck, String strMinute) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);

        SelectCmdOrder[] selectCmds = reMainOrder.getSelectOrder().getCmds();
        int curIndex = reMainOrder.getSelectOrder().getCurIndex();
        log.info("curIndex >> " + curIndex);
        log.info("reMainOrder >> " + reMainOrder);
        // 만약 버튼 블록을 통해서 버튼을 클릭했다면, strCheck == "버튼", 이고 그게 아니라면 ""일것이다.
        // 즉, strCheck가 "버튼" 이라면 selectedCode를 parentCode로 다시 파싱할 필요 없음
        CmdOrder[] cmds = reMainOrder.getCmdOrderList();
        int parentCode = -100;

        if (strCheck == "") {
            // 핵심 코드 : 최근에 선택된 명령어의 부모 명령어를 이용해서 사용자가 실제로 선택한 명령어의 실제 코드를 아는 코드
            int cnt = 0;
            for (int i = 0; i < cmds.length; i++) {
                if (cmds[i].getParentCode() == reMainOrder.getCurrentParentCode()) {
                    cnt++;
                    if (cnt == selectedCode) {
                        parentCode = cmds[i].getCmdCode();   // 사용자가 선택한 명령어의 실제 코드
                        // 실제 선턱된 코드가 다음 parentCode 가 된다.
                        log.info("break >> " + parentCode);
                        break;
                    }
                }
            }
        } else if (strCheck == "버튼") {
            // 사용자가 버튼 목록중에서 하나를 선택했을때
            curIndex++;
            selectCmds[curIndex] = new SelectCmdOrder(reMainOrder.getCurrentParentCode(), selectedCode);
            reMainOrder.getSelectOrder().setCurIndex(curIndex);
            mainOrderRepository.update(reMainOrder);
            parentCode = reMainOrder.getCurrentParentCode();
            log.info("버튼 클릭했을때, responserChildCmds >> " + reMainOrder.toString());
            return kakaoSimpleTextService.makerCmdsCard(providerId, parentCode);
        }

        // selectOrder에 명령 코드 삽입하는 코드 작성
        curIndex++;
        selectCmds[curIndex] = new SelectCmdOrder(parentCode, -1); // 나중에 다음 명령어로 갈때 인덱스를 증가시켜줘야함.
        reMainOrder.getSelectOrder().setCurIndex(curIndex);
        // 만약 strCheck가 숫자를 포함한다면 설정된 시간이 들어있는거임
        if (strMinute.matches("^[0-9]*$")) {
            selectCmds[curIndex].setData(Integer.parseInt(strMinute));
        }
        mainOrderRepository.update(reMainOrder);
        log.info("responserChildCmds >> " + reMainOrder.toString());

        return kakaoSimpleTextService.makerCmdsCard(providerId, parentCode);
    }



    public ResponseDtoVerTwo responserButtonCmds(String providerId, int selectedCode) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);
        CmdOrder[] cmds = reMainOrder.getCmdOrderList();
        int parentCode = -100;
        int cnt = 0;
        for (int i = 0; i < cmds.length; i++) {
            if (cmds[i].getParentCode() == reMainOrder.getCurrentParentCode()) {
                cnt++;
                if (cnt == selectedCode) {
                    parentCode = cmds[i].getCmdCode();   // 사용자가 선택한 명령어의 실제 코드
                    // 실제 선턱된 코드가 다음 parentCode 가 된다.
                    log.info("break >> " + parentCode);
                    break;
                }
            }
        }

        reMainOrder.setCurrentParentCode(parentCode);
        mainOrderRepository.save(reMainOrder);

        return kakaoSimpleTextService.makerButtonCmdsCard(providerId, parentCode);
    }



    public ResponseDtoVerTwo responserDirectInput(String providerId, int selectedCode) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);
        CmdOrder[] cmds = reMainOrder.getCmdOrderList();
        int parentCode = -100;
        int cnt = 0;
        for (int i = 0; i < cmds.length; i++) {
            if (cmds[i].getParentCode() == reMainOrder.getCurrentParentCode()) {
                cnt++;
                if (cnt == selectedCode) {
                    parentCode = cmds[i].getCmdCode();   // 사용자가 선택한 명령어의 실제 코드
                    // 실제 선턱된 코드가 다음 parentCode 가 된다.
                    log.info("break >> " + parentCode);
                    break;
                }
            }
        }

        SelectCmdOrder[] selectCmds = reMainOrder.getSelectOrder().getCmds();
        int curIndex = reMainOrder.getSelectOrder().getCurIndex();
        curIndex++;
        selectCmds[curIndex] = new SelectCmdOrder(parentCode, -1);
        reMainOrder.getSelectOrder().setCurIndex(curIndex);
        reMainOrder.setCurrentParentCode(parentCode);
        mainOrderRepository.update(reMainOrder);

        return kakaoSimpleTextService.makerDirectInputText(providerId, parentCode);
    }



    public ResponseDtoVerTwo responserChildCmdsAfterDirInput(String providerId, int dirInUtterance) {

        MainOrder reMainOrder = mainOrderRepository.find(providerId);
        int parentCode = reMainOrder.getCurrentParentCode();

        // dirInUttreance 저장
        SelectCmdOrder[] selectCmds = reMainOrder.getSelectOrder().getCmds();
        int curIndex = reMainOrder.getSelectOrder().getCurIndex();
        selectCmds[curIndex].setData(dirInUtterance);
        mainOrderRepository.update(reMainOrder);

        log.info("ReMainOrder >> " + reMainOrder);
        return kakaoSimpleTextService.makerCmdsCard(providerId, parentCode);
    }
}