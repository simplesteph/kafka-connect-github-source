package com.simplesteph.kafka;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.kafka.connect.errors.ConnectException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

// GitHubHttpAPIClient used to launch HTTP Get requests
public class GitHubAPIHttpClient {

    private static final Logger log = LoggerFactory.getLogger(GitHubAPIHttpClient.class);

    // for efficient http requests
    private Integer XRateLimit = 9999;
    private Integer XRateRemaining = 9999;
    private long XRateReset = Instant.MAX.getEpochSecond();

    GitHubSourceConnectorConfig config;

    public static final String X_RATELIMIT_LIMIT_HEADER="X-RateLimit-Limit";
    public static final String X_RATELIMIT_REMAINING_HEADER="X-RateLimit-Remaining";
    public static final String X_RATELIMIT_RESET_HEADER="X-RateLimit-Reset";

    public GitHubAPIHttpClient(GitHubSourceConnectorConfig config){
        this.config = config;
    }

    protected JSONArray getNextIssues(Integer page, Instant since) throws InterruptedException {

        HttpResponse<JsonNode> jsonResponse;
        try {
            jsonResponse = getNextIssuesAPI(page, since);

            // deal with headers in any case
            Headers headers = jsonResponse.getHeaders();
            XRateLimit = Integer.valueOf(headers.getFirst(X_RATELIMIT_LIMIT_HEADER));
            XRateRemaining = Integer.valueOf(headers.getFirst(X_RATELIMIT_REMAINING_HEADER));
            XRateReset = Integer.valueOf(headers.getFirst(X_RATELIMIT_RESET_HEADER));
            switch (jsonResponse.getStatus()){
                case 200:
                    return jsonResponse.getBody().getArray();
                case 401:
                    throw new ConnectException("Bad GitHub credentials provided, please edit your config");
                case 403:
                    // we have issues too many requests.
                    log.info(jsonResponse.getBody().getObject().getString("message"));
                    log.info(String.format("Your rate limit is %s", XRateLimit));
                    log.info(String.format("Your remaining calls is %s", XRateRemaining));
                    log.info(String.format("The limit will reset at %s",
                            LocalDateTime.ofInstant(Instant.ofEpochSecond(XRateReset), ZoneOffset.systemDefault())));
                    long sleepTime = XRateReset - Instant.now().getEpochSecond();
                    log.info(String.format("Sleeping for %s seconds", sleepTime ));
                    Thread.sleep(1000 * sleepTime);
                    return getNextIssues(page, since);
                default:
                    log.error(constructUrl(page, since));
                    log.error(String.valueOf(jsonResponse.getStatus()));
                    log.error(jsonResponse.getBody().toString());
                    log.error(jsonResponse.getHeaders().toString());
                    log.error("Unknown error: Sleeping 5 seconds " +
                            "before re-trying");
                    Thread.sleep(5000L);
                    return getNextIssues(page, since);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            Thread.sleep(5000L);
            return new JSONArray();
        }
    }

    protected HttpResponse<JsonNode> getNextIssuesAPI(Integer page, Instant since) throws UnirestException {
        GetRequest unirest = Unirest.get(constructUrl(page, since));
        if (!config.getAuthUsername().isEmpty() && !config.getAuthPassword().isEmpty() ){
            unirest = unirest.basicAuth(config.getAuthUsername(), config.getAuthPassword());
        }
        log.debug(String.format("GET %s", unirest.getUrl()));
        return unirest.asJson();
    }

    protected String constructUrl(Integer page, Instant since){
        return String.format(
                "https://api.github.com/repos/%s/%s/issues?page=%s&per_page=%s&since=%s&state=all&direction=asc&sort=updated",
                config.getOwnerConfig(),
                config.getRepoConfig(),
                page,
                config.getBatchSize(),
                since.toString());
    }

    public void sleep() throws InterruptedException {
        long sleepTime = (long) Math.ceil(
                (double) (XRateReset - Instant.now().getEpochSecond()) / XRateRemaining);
        log.debug(String.format("Sleeping for %s seconds", sleepTime ));
        Thread.sleep(1000 * sleepTime);
    }

    public void sleepIfNeed() throws InterruptedException {
        // Sleep if needed
        if (XRateRemaining <= 10 && XRateRemaining > 0) {
            log.info(String.format("Approaching limit soon, you have %s requests left", XRateRemaining));
            sleep();
        }
    }
}
