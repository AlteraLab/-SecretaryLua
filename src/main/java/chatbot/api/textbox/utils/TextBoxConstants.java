package chatbot.api.textbox.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TextBoxConstants {

    public static final Integer BOX_TYPE_BUTTON = 1;
    public static final Integer BOX_TYPE_DYNAMIC = 2;
    public static final Integer BOX_TYPE_TIME = 3;
    public static final Integer BOX_TYPE_END = 4;
    public static final Integer BOX_TYPE_ENTRY = 5;

    // 최초에 엔트리 박스를 만들때, 센싱에 관한 박스를 만들지 에약에 관한 박스를 만들지
    // 제어에 관한 박스를 만들지, 필터를 하는 단계
    public static final Character BUTTON_TYPE_CONTROL = '1';
    public static final Character BUTTON_TYPE_LOOKUP_RESERVATION = '2';
    public static final Character BUTTON_TYPE_LOOKUP_SENSING = '3';
    public static final Character BUTTON_TYPE_LOOKUP_DEVICE = '4';
    //public static final Character BUTTON_TYPE_RESERVATION = '5';

    public static final String BLOCK_ID_RETURN_HRDWRS = "5c7670efe821274ba78984c4";

    public static final String BLOCK_ID_RETURN_BTNS = "5cb3ad0ee821270bd1ef6d0c";

    public static final String BLOCK_ID_BTN_TEXTBOX = "5cc940d9e82127558b7e7b91";

    public static final String BLOCK_ID_TIME_TEXTBOX = "5cc93f6be82127558b7e7b87";

    public static final String BLOCK_ID_INPUT_TEXTBOX = "5cc93f92e82127558b7e7b89";


    public static final String BLOCK_ID_BUILDED_CODES = "5cc93fd8e82127558b7e7b8f";

    public static final String BLOCK_ID_TRANSFER_RESULT = "5ccc1f5c384c5508fceef866";

    public static final String BLOCK_ID_CANCLE_COMPLETE = "5ccfec8ae821271a946123d9";
}
