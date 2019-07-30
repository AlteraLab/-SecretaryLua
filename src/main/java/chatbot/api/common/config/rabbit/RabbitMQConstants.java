package chatbot.api.common.config.rabbit;

public class RabbitMQConstants {

    // exchage
    public static final String SKILL_EXCHANGE = "skill";

    // keep-alive
    public static final String SKILL_KEEP_ALIVE_ROUTE_KEY = "skill.keepalive.route";
    public static final String SKILL_KEEP_ALIVE_QUEUE = "skill.keepalive.queue";

    // hub-log
    public static final String SKILL_HUB_LOG_ROUTE_KEY = "skill.hublog.route";
    public static final String SKILL_HUB_LOG_QUEUE = "skill.hublog.queue";
}
