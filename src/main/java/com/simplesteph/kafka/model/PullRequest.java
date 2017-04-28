
package com.simplesteph.kafka.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import static com.simplesteph.kafka.GitHubSchemas.*;

public class PullRequest {

    private String url;
    private String htmlUrl;
    private String diffUrl;
    private String patchUrl;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public PullRequest() {
    }

    /**
     * 
     * @param diffUrl
     * @param htmlUrl
     * @param patchUrl
     * @param url
     */
    public PullRequest(String url, String htmlUrl, String diffUrl, String patchUrl) {
        super();
        this.url = url;
        this.htmlUrl = htmlUrl;
        this.diffUrl = diffUrl;
        this.patchUrl = patchUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PullRequest withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public PullRequest withHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
        return this;
    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    public PullRequest withDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
        return this;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    public PullRequest withPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public PullRequest withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public static PullRequest fromJson(JSONObject pull_request) {
        return new PullRequest()
                .withUrl(pull_request.getString(PR_URL_FIELD))
                .withHtmlUrl(pull_request.getString(PR_HTML_URL_FIELD));

    }
}
