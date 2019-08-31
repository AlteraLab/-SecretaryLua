package chatbot.api.skillhub.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HubConstants {

    public static final String ROLE_USER = "ROLE_USER";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";



    public static final String FAIL_MSG_NO_HUB_REGISTED_AS_ADMIN = "fail : You do not have a hub registered as admin";

    public static final String SUCCESS_MSG_GET_HUBS_SEQ_AND_NAME = "success : List of id and name of hub registered as admin";


    public static final String FAIL_MSG_BECAUSE_NO_EXIST = "fail : No exist hub";

    public static final String FAIL_MSG_DELETE_HUB_BECAUSE_NO_ADMIN  = "fail : Exist hub but You are not a hub admin";

    public static final String FAIL_MSG_DELETE_HUB_BECAUSE_FAIL_RESTTEMPLATE  = "Failed to erase ip from hub server. Please try again later";


    public static final String FAIL_MSG_EXPLICIT_DEL_AT_QUERY_ABOUT_ROLL = "fail : Failed to delete record in ROLL table";

    public static final String FAIL_MSG_EXPLICIT_DEL_AT_QUERY_ABOUT_HUB = "fail : Failed to delete record in hub table";

    public static final String SUCCESS_MSG_EXPLICIT_DEL = "success : Succesed to delete record in hub and role table";

    public static final String EXCEPTION_MSG_DURING_DELETER = "삭제 중에 예외가 발생했습니다.";


    public static final String FAIL_MSG_NO_EXIST_USER_FROM_USERS_TABLE = "fail : no exist user from user table";

    public static final String FAIL_MSG_REGIST_HUB_INTO_HUB_TABLE = "fail : Failed insert into hub table";

    public static final String SUCCESS_MSG_REGIST_INTO_HUB_AND_ROLL = "success : Successed to regist hub and roll";


    public static final String EXCEPTION_MSG_DURING_EDITER = "exception : An exception occurred During editer execution";

    public static final String EXCEPTION_MSG_DURING_IMPLICIT_DELETE_HUB = "exception : An exception occurred During ImplicitDeleteHub execution";

    public static final String SUCCESS_MSG_EDIT_HUB = "success : Successed to edit hub";

    public static final String FAIL_MSG_TO_EDIT_HUB_BECAUSE_NO_ADMIN = "fail : Failed to edit hub because you are not admin about hub";
}
