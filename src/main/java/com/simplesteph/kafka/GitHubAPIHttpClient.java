package com.simplesteph.kafka;

import com.simplesteph.kafka.utils.SetBasicAuthUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.connect.errors.ConnectException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

// GitHubHttpAPIClient used to launch HTTP Get requests
public class GitHubAPIHttpClient implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(GitHubAPIHttpClient.class);

    private HttpClientProvider httpClientProvider;
    private HttpClient httpClient;
    // for efficient http requests
    private Integer XRateLimit = 9999;
    private Integer XRateRemaining = 9999;
    private long XRateReset = Instant.MAX.getEpochSecond();

    GitHubSourceConnectorConfig config;

    public GitHubAPIHttpClient(GitHubSourceConnectorConfig config){
        this.config = config;
        this.httpClientProvider=new HttpClientProvider();
        this.httpClient=this.httpClientProvider.getHttpClient();
    }

    protected JSONArray getNextIssues(RepositoryVariables repoVar) throws InterruptedException {

        HttpResponse httpResponse;
        try {
            httpResponse = getNextIssuesAPI(repoVar);
            //reading the headers of the response
            HashMap<String,String> headers=new HashMap<String, String>();
            for(Header h:httpResponse.getAllHeaders())
                headers.put(h.getName(),h.getValue());

            XRateLimit = Integer.valueOf(httpResponse.getFirstHeader("X-RateLimit-Limit").getValue());
            XRateRemaining = Integer.valueOf(httpResponse.getFirstHeader("X-RateLimit-Remaining").getValue());
            XRateReset = Integer.valueOf(httpResponse.getFirstHeader("X-RateLimit-Reset").getValue());
            //reading the httpResponse content(body)
            String jsonResponse= EntityUtils.toString(httpResponse.getEntity());
            JSONArray jsonArray=new JSONArray();
            JSONObject jsonBody=new JSONObject();
            try{
                //try to read httpResponse as JSONArray if possible
                jsonArray=new JSONArray(jsonResponse);
            }
            catch (JSONException ex){
                //read as JSONObject
                jsonBody=new JSONObject(jsonResponse);
            }

            switch (httpResponse.getStatusLine().getStatusCode()){
                case 200:
                    return jsonArray;
                case 401:
                    throw new ConnectException("Bad GitHub credentials provided, please edit your config");
                case 403:
                    // we have issues too many requests.
                    log.info(jsonBody.getString("message"));
                    log.info(String.format("Your rate limit is %s", XRateLimit));
                    log.info(String.format("Your remaining calls is %s", XRateRemaining));
                    log.info(String.format("The limit will reset at %s",
                            LocalDateTime.ofInstant(Instant.ofEpochSecond(XRateReset), ZoneOffset.systemDefault())));
                    long sleepTime = XRateReset - Instant.now().getEpochSecond();
                    log.info(String.format("Sleeping for %s seconds", sleepTime ));
                    Thread.sleep(1000 * sleepTime);
                    return getNextIssues(repoVar);
                default:
                    log.error(constructUrl(repoVar));
                    log.error(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
                    log.error(jsonResponse);
                    log.error(headers.toString());
                    log.error("Unknown error: Sleeping 5 seconds " +
                            "before re-trying");
                    Thread.sleep(5000L);
                    return getNextIssues(repoVar);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Thread.sleep(5000L);
            return new JSONArray();
        }
    }

    protected HttpResponse getNextIssuesAPI(RepositoryVariables repoVar) throws IOException {
        HttpGet httpGet=new HttpGet(constructUrl(repoVar));
        if (!config.getAuthUsername().isEmpty() && !config.getAuthPassword().isEmpty() ){
            SetBasicAuthUtil.SetBasicAuth(httpGet,config.getAuthUsername(), config.getAuthPassword());
        }
        HttpResponse response= httpClient.execute(httpGet);
        return response;
    }

    protected String constructUrl(RepositoryVariables repoVar){
        return String.format(
                "https://api.github.com/repos/%s/%s/issues?page=%s&per_page=%s&since=%s&state=all&direction=asc&sort=updated",
                repoVar.getOwner(),
                repoVar.getRepoName(),
                repoVar.nextPageToVisit,
                config.getBatchSize(),
                repoVar.nextQuerySince.toString());
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

    @Override
    public void close() throws IOException {
        if(httpClientProvider!=null)
            httpClientProvider.close();
    }
}