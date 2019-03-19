package chatbot.api.role.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoleConstants {

    public static final String FAIL_MSG_NO_EXIST_HUB = "fail : No exist hub";

    public static final String FAIL_MSG_NO_ADMIN = "fail : Failed to add ROLE_USER because you are not admin about hub";

    public static final String FAIL_MSG_ALREADY_ROLE_USER = "fail : Failed because already ROLE_USER";

    public static final String FAIL_MSG_REGIST_ROLE_INTO_ROLE_TABLE = "fail : Failed insert into role table";

    public static final String SUCCESS_MSG_ADD_ROLL_USER = "success : Successed to regist user as ROLE_USER";

    public static final String EXCEPTION_MSG_DURING_REGISTER = "exception : An exception occurred During register execution";

}
