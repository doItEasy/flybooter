package com.github.flybooter.common.constants;

import java.time.format.DateTimeFormatter;

public final class GlobalConstants {

    public static final String SPRING_PROFILE_DEVELOPMENT = "development";
    public static final String SPRING_PROFILE_TEST = "testing";
    public static final String SPRING_PROFILE_STAGING = "staging";
    public static final String SPRING_PROFILE_PRODUCTION = "production";
    public static final String SPRING_PROFILE_SWAGGER = "swagger";

    public static final String SYS_PREFIX = "ga";


    public static final String SYSTEM_ACCOUNT = "system";
    public static final String SYSTEM_ACCOUNT_NAME = "系统生成";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter IOS_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter STANDARD_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter SLASH_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter YIFUBAO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter SIMPLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM.dd");


}
