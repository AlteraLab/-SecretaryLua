package chatbot.api.common.config.rabbit;

public class RabbitMQConstants {

    // keep-alive
    public static final String SKILL_KEEP_ALIVE_EXCHANGE = "skill.keepalive";
    public static final String SKILL_KEEP_ALIVE_ROUTE_KEY = "skill.keepalive.route";
    public static final String SKILL_KEEP_ALIVE_QUEUE = "skill.keepalive.queue";

    // establish
    public static final String SKILL_ESTABLISH_EXCHANGE = "skill.establish";
    public static final String SKILL_ESTABLISH_ROUTE_KEY = "skill.establish.route";
    public static final String SKILL_ESTABLISH_QUEUE = "skill.establish.queue";

    // hub-log
    public static final String SKILL_HUB_LOG_EXCHANGE = "skill.hublog";
    public static final String SKILL_HUB_LOG_ROUTE_KEY = "skill.hublog.route";
    public static final String SKILL_HUB_LOG_QUEUE = "skill.hublog.queue";

    // dev
    public static final String SKILL_DEV_EXCHANGE = "skill.dev";
    public static final String SKILL_DEV_ROUTE_KEY = "skill.dev.route";
    public static final String SKILL_DEV_QUEUE = "skill.dev.queue";
}
