package chatbot.api.textbox.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TextBoxConstants {

    public static final Integer MAX_DEPTH = 3;

    public static final Integer BOX_TYPE_CONTROL = 1;
    public static final Integer BOX_TYPE_DYNAMIC = 2;
    public static final Integer BOX_TYPE_TIME = 3;
    public static final Integer BOX_TYPE_END = 4;
    public static final Integer BOX_TYPE_ENTRY = 5;

    public static final Character BUTTON_TYPE_CONTROL = '1';
    public static final Character BUTTON_TYPE_LOOKUP_RESERVATION = '2';
    public static final Character BUTTON_TYPE_LOOKUP_SENSING = '3';
    public static final Character BUTTON_TYPE_LOOKUP_DEVICE = '4';
    public static final Character BUTTON_TYPE_RESERVATION = '5';
    public static final Character BUTTON_TYPE_RESERVATION_DELETION = '6';

    // Destination
    public static final String BLOCK_ID_TO_HRDWRS_BOX = "5c7670efe821274ba78984c4"; // to hrdwrs box
    public static final String BLOCK_ID_TO_ENTRY_BOX = "5cb3ad0ee821270bd1ef6d0c";  // to entry box

    // Any
    public static final String BLOCK_ID_TO_ANY_BOX = "5cc940d9e82127558b7e7b91";    // from entry to any

    // Button Type Scenarios Case
    public static final String BLOCK_ID_TO_CONTROL = "5d22a1bab617ea0001153bf4";
    public static final String BLOCK_ID_TO_LOOKUP_RESERVATION = "5d22a1d8ffa7480001c5b917";
    public static final String BLOCK_ID_TO_LOOKUP_SENSING = "5d22a1e5ffa7480001c5b919";
    public static final String BLOCK_ID_TO_LOOKUP_DEVICE = "5d22a1f4ffa7480001c5b91b";
    public static final String BLOCK_ID_TO_ONLY_RESERVATION = "5d22a205ffa7480001c5b91d";

    // 제어 시나리오 블록s
    // END 박스는 Entry 역할을 할수도 혹은 진짜 명령 전송 박스일 수도 있다.
    // (제어->종료) 시나리오
    public static final String BLOCK_ID_END_ENTRY = "5cc93f6be82127558b7e7b87";
    // (제어->시간->종료) 시나리오
    public static final String BLOCK_ID_END_TIME_ENTRY = "5cc93f92e82127558b7e7b89";
    // (제어->시간->동적->종료) 시나리오
    public static final String BLOCK_ID_DYNAMIC_TIME_ENTRY = "5d24500ab617ea000115459b";
    public static final String BLOCK_ID_END_DYNAMIC_TIME_ENTRY = "5ccb0ea2384c5508fceef36e";
    // (제어->동적->종료) 시나리오
    public static final String BLOCK_ID_DYNAMIC_ENTRY = "5d242868b617ea0001154393";
    public static final String BLOCK_ID_END_DYNAMIC_ENTRY = "5d242873b617ea0001154395";
    // (제어->동적->시간->종료) 시나리오
    public static final String BLOCK_ID__DYNAMIC_ENTRY = "5d2428a8b617ea0001154397";
    public static final String BLOCK_ID_END_TIME__DYNAMIC_ENTRY = "5d2428d3b617ea0001154399";

    public static final String BLOCK_ID_SELECT_BOX_YES_OR_NO = "5cc93fd8e82127558b7e7b8f";
    public static final String BLOCK_ID_TRANSFER_RESULT_DATA = "5ccc1f5c384c5508fceef866";


    // 예약 시나리오 블록s
    public static final String BLOCK_ID_END_RESERVATION_ENTRY = "5d22a205ffa7480001c5b91d";

    public static final String BLOCK_ID_DY_RESERVATION_ENTRY = "5d3d9028ffa7480001de2c00";
    public static final String BLOCK_ID_END_DY_RESERVATION_ENTRY = "5d3d904092690d000124ce0b";

    public static final String BLOCK_ID_DY_ENTRY = "5d3d905d92690d000124ce0d";
    public static final String BLOCK_ID_END_RESERVATION_DY_ENTRY = "5d3d908fb617ea00018377d4";

    public static final String BLOCK_ID_RESERVATION_DELETION_RESULT = "5d3e35ddffa7480001de2d1c";

    public static final String ONLY_ONE_EXECUTE = "1회 실행";
    public static final String EVERY_DAY_EXECUTE = "매일 실행";
    public static final String EVERY_WEEK_EXECUTE = "매주 실행";

    public static final Character ONLY_ONE_TYPE = '1';
    public static final Character EVERY_DAY_TYPE = '2';
    public static final Character EVERY_WEEK_TYPE = '3';

    public static final Character INTERVAL_TYPE = '4';
}
