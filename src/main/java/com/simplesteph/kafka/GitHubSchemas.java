package com.simplesteph.kafka;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class GitHubSchemas {

    public static String NEXT_PAGE_FIELD = "next_page";

    // Issue fields
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

    // PR fields
    public static String PR_FIELD = "pull_request";
    public static String PR_URL_FIELD = "url";
    public static String PR_HTML_URL_FIELD = "html_url";

    // Schema names
    public static String SCHEMA_KEY = "issue_key";
    public static String SCHEMA_VALUE_ISSUE = "issue";
    public static String SCHEMA_VALUE_USER = "user";
    public static String SCHEMA_VALUE_PR = "pr";

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

    // optional schema
    public static Schema PR_SCHEMA = SchemaBuilder.struct().name(SCHEMA_VALUE_PR)
            .version(1)
            .field(PR_URL_FIELD, Schema.STRING_SCHEMA)
            .field(PR_HTML_URL_FIELD, Schema.STRING_SCHEMA)
            .optional()
            .build();

    public static Schema VALUE_SCHEMA = SchemaBuilder.struct().name(SCHEMA_VALUE_ISSUE)
            .version(1)
            .field(URL_FIELD, Schema.STRING_SCHEMA)
            .field(TITLE_FIELD, Schema.STRING_SCHEMA)
            .field(CREATED_AT_FIELD, Schema.INT64_SCHEMA)
            .field(UPDATED_AT_FIELD, Schema.INT64_SCHEMA)
            .field(NUMBER_FIELD, Schema.INT32_SCHEMA)
            .field(STATE_FIELD, Schema.STRING_SCHEMA)
            .field(USER_FIELD, USER_SCHEMA) // mandatory
            .field(PR_FIELD, PR_SCHEMA)     // optional
            .build();
}
