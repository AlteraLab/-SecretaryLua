package chatbot.api.test;

import chatbot.api.common.domain.ResponseDTO;
import chatbot.api.mappers.HrdwrMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private HrdwrMapper hrdwrMapper;

    @GetMapping("/hub/scan")
    public Object devScan() {
        log.info("Dev Scan");
/*
        Dev dev1 = new Dev("11:11:11:11:11:11", "dev 1", "11112222333344445555666677778888");
        Dev dev2 = new Dev("21:21:21:21:21:21", "dev 2", "55556666777788831111222233334444");
        Dev dev3 = new Dev("12:12:12:12:12:12", "dev 3", "55556666771111221222233334778444");
        Dev dev4 = new Dev("13:13:13:13:13:13", "dev 4", "55556677686677778883222233334444");
        Dev dev5 = new Dev("31:31:31:31:31:31", "dev 5", "55556666772244447788477888311112");*/
        Dev dev1 = new Dev("11:11:11:11:11:11", "dev 1");
        Dev dev2 = new Dev("21:21:21:21:21:21", "dev 2");
        Dev dev3 = new Dev("12:12:12:12:12:12", "dev 3");
        Dev dev4 = new Dev("13:13:13:13:13:13", "dev 4");
        Dev dev5 = new Dev("31:31:31:31:31:31", "dev 5");
        Dev[] devs = {dev1, dev2, dev3, dev4, dev5};

        return new Object(){
            public HttpStatus status = HttpStatus.OK;
            public Object devices = devs;
        };
    }

    //POST http://{hub_url}:{hub_port}/hub/connect/{bluetooth_mac_address}
    @PostMapping("/hub/connect/{bluetooth_mac_address}")
    public Object connectDev() {
        log.info("Connect Dev");

        /*return new Object(){
            public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            public String msg = "디바이스 연결이 실패하였습니다.";
        };*/
        return new Object(){
            public HttpStatus status = HttpStatus.OK;
            public String msg = "디바이스 연결이 성공하였습니다.";
        };
    }

    @GetMapping("/hub/devs")
    public Object requestrConnectedDevs() {
        log.info("Request Connected Devs");

        Dev dev1 = new Dev("11:11:11:11:11:11", "Hello", "b776d155a4d44816969408f831600e2d");
        Dev dev2 = new Dev("21:21:21:21:21:21", "C++", "721ebcf02bc64ef89f698cc26dca7992");
        Dev dev3 = new Dev("12:12:12:12:12:12", "C#", "b776d155a4d44816969408f831600e2d");
        Dev dev4 = new Dev("13:13:13:13:13:13", "JS", "721ebcf02bc64ef89f698cc26dca7992");
        Dev dev5 = new Dev("31:31:31:31:31:31", "RUST", "aaaabbbbccccddddeeeeffffgggghhhh");
        Dev[] devs = {dev1, dev2, dev3, dev4, dev5};

        return new Object(){
            public HttpStatus status = HttpStatus.OK;
            public Object devices = devs;
        };
    }

    @GetMapping("/hub/devs/{devType}")
    //public Object getDevDetail(@PathVariable("devType") String authKey) {
    public Object getDevDetail(@PathVariable("devType") String authKey) {
        Object dev = hrdwrMapper.getHrdwrByAuthKey(authKey);
        log.info(dev + "");
        return new Object(){
            public HttpStatus status = HttpStatus.OK;
            public Object devDetail = dev;
        };
    }
}
