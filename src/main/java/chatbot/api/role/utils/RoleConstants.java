package chatbot.api.role.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoleConstants {

    public static final String FAIL_MSG_NO_EXIST_HUB = "fail : No exist hub";

    public static final String FAIL_MSG_NO_ADMIN = "fail : Failed because you are not admin about hub";

    public static final String FAIL_MSG_ALREADY_ROLE_USER = "fail : Failed because already ROLE_USER";

    public static final String FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE = "fail : Failed insert into role table";

    public static final String SUCCESS_MSG_ADD_ROLE_USER = "success : Successed to regist user as ROLE_USER";

    public static final String SUCCESS_MSG_GET_ROLES_USER = "success : Successed to get ROLES";

    public static final String EXCEPTION_MSG_DURING_REGISTER = "exception : An exception occurred During register execution";

    public static final String EXCEPTION_MSG_DURING_GETTER = "exception : An exception occurred During getter execution";

    public static final String FAIL_MSG_NO_ROLE_USER_AND_ROLE_ADMIN = "fail : You are not ROLL_USER AND ROLE_ADMIN";

    public static final String FAIL_MSG_NO_ROLE_USER = "fail : user who deleted about hub are not ROLL_USER";

    public static final String FAIL_MSG = "fail";

    public  static final String SUCCESS_MSG_DELETE_ROLE_USER = "success : delete ROLE_USER";

    public static final String EXCEPTION_MSG_DURING_DELETER = "exception : An exception occurred During deleter execution";

    public static final String FAIL_MSG_GET_ROLE_BECAUSE_NO_EXIST = "fail : no exist role";
}
