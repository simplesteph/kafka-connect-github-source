package com.simplesteph.kafka;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class GitHubSchemas {

    public static String NEXT_PAGE_FIELD = "next_page";

    // Issue fields
    public static String FULL_REPO_FIELD = "owner/repository";
    public static String OWNER_FIELD = "owner";
    public static String REPOSITORY_FIELD = "repository";
    public static String CREATED_AT_FIELD = "created_at";
    public static String UPDATED_AT_FIELD = "updated_at";
    public static String NUMBER_FIELD = "number";
    public static String URL_FIELD = "url";
    public static String HTML_URL_FIELD = "html_url";
    public static String TITLE_FIELD = "title";
    public static String STATE_FIELD = "state";

    // User fields
    public static String USER_FIELD = "user";
    public static String USER_URL_FIELD = "url";
    public static String USER_HTML_URL_FIELD = "html_url";
    public static String USER_ID_FIELD = "id";
    public static String USER_LOGIN_FIELD = "login";

    // Schema names
    public static String SCHEMA_KEY = "GitHub Issue Key";
    public static String SCHEMA_VALUE_ISSUE = "GitHub Issue";
    public static String SCHEMA_VALUE_USER = "User";

    // Key Schema
    public static Schema KEY_SCHEMA = SchemaBuilder.struct().name(SCHEMA_KEY)
            .version(1)
            .field(OWNER_FIELD, Schema.STRING_SCHEMA)
            .field(REPOSITORY_FIELD, Schema.STRING_SCHEMA)
            .field(NUMBER_FIELD, Schema.INT32_SCHEMA)
            .build();

    // Value Schema
    public static Schema USER_SCHEMA = SchemaBuilder.struct().name(SCHEMA_VALUE_USER)
            .version(1)
            .field(USER_URL_FIELD, Schema.STRING_SCHEMA)
            .field(USER_ID_FIELD, Schema.INT32_SCHEMA)
            .field(USER_LOGIN_FIELD, Schema.STRING_SCHEMA)
            .build();

    public static Schema VALUE_SCHEMA = SchemaBuilder.struct().name(SCHEMA_VALUE_ISSUE)
            .version(1)
            .field(URL_FIELD, Schema.STRING_SCHEMA)
            .field(TITLE_FIELD, Schema.STRING_SCHEMA)
            .field(CREATED_AT_FIELD, Schema.INT64_SCHEMA)
            .field(UPDATED_AT_FIELD, Schema.INT64_SCHEMA)
            .field(NUMBER_FIELD, Schema.INT32_SCHEMA)
            .field(STATE_FIELD, Schema.STRING_SCHEMA)
            .field(USER_FIELD, USER_SCHEMA)
            .build();
}
