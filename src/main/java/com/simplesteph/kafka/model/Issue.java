
package com.simplesteph.kafka.model;

import org.json.JSONObject;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.simplesteph.kafka.GitHubSchemas.*;

public class Issue {

    private Integer id;
    private String url;
    private String repositoryUrl;
    private String labelsUrl;
    private String commentsUrl;
    private String eventsUrl;
    private String htmlUrl;
    private Integer number;
    private String state;
    private String title;
    private String body;
    private User user;
    private List<Label> labels = null;
    private Assignee assignee;
    private Milestone milestone;
    private Boolean locked;
    private Integer comments;
    private PullRequest pullRequest;
    private Object closedAt;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Assignee> assignees = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Issue() {
    }

    /**
     * 
     * @param eventsUrl
     * @param body
     * @param assignees
     * @param closedAt
     * @param labels
     * @param state
     * @param assignee
     * @param number
     * @param url
     * @param updatedAt
     * @param milestone
     * @param id
     * @param htmlUrl
     * @param title
     * @param createdAt
     * @param commentsUrl
     * @param labelsUrl
     * @param pullRequest
     * @param locked
     * @param repositoryUrl
     * @param user
     * @param comments
     */
    public Issue(Integer id, String url, String repositoryUrl, String labelsUrl, String commentsUrl, String eventsUrl, String htmlUrl, Integer number, String state, String title, String body, User user, List<Label> labels, Assignee assignee, Milestone milestone, Boolean locked, Integer comments, PullRequest pullRequest, Object closedAt, Instant createdAt, Instant updatedAt, List<Assignee> assignees) {
        super();
        this.id = id;
        this.url = url;
        this.repositoryUrl = repositoryUrl;
        this.labelsUrl = labelsUrl;
        this.commentsUrl = commentsUrl;
        this.eventsUrl = eventsUrl;
        this.htmlUrl = htmlUrl;
        this.number = number;
        this.state = state;
        this.title = title;
        this.body = body;
        this.user = user;
        this.labels = labels;
        this.assignee = assignee;
        this.milestone = milestone;
        this.locked = locked;
        this.comments = comments;
        this.pullRequest = pullRequest;
        this.closedAt = closedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.assignees = assignees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Issue withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Issue withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public Issue withRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
        return this;
    }

    public String getLabelsUrl() {
        return labelsUrl;
    }

    public void setLabelsUrl(String labelsUrl) {
        this.labelsUrl = labelsUrl;
    }

    public Issue withLabelsUrl(String labelsUrl) {
        this.labelsUrl = labelsUrl;
        return this;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public Issue withCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
        return this;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public Issue withEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
        return this;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Issue withHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Issue withNumber(Integer number) {
        this.number = number;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Issue withState(String state) {
        this.state = state;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Issue withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Issue withBody(String body) {
        this.body = body;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Issue withUser(User user) {
        this.user = user;
        return this;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public Issue withLabels(List<Label> labels) {
        this.labels = labels;
        return this;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public Issue withAssignee(Assignee assignee) {
        this.assignee = assignee;
        return this;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public Issue withMilestone(Milestone milestone) {
        this.milestone = milestone;
        return this;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Issue withLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Issue withComments(Integer comments) {
        this.comments = comments;
        return this;
    }

    public PullRequest getPullRequest() {
        return pullRequest;
    }

    public void setPullRequest(PullRequest pullRequest) {
        this.pullRequest = pullRequest;
    }

    public Issue withPullRequest(PullRequest pullRequest) {
        this.pullRequest = pullRequest;
        return this;
    }

    public Object getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Object closedAt) {
        this.closedAt = closedAt;
    }

    public Issue withClosedAt(Object closedAt) {
        this.closedAt = closedAt;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Issue withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Issue withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public List<Assignee> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<Assignee> assignees) {
        this.assignees = assignees;
    }

    public Issue withAssignees(List<Assignee> assignees) {
        this.assignees = assignees;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Issue withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public static Issue fromJson(JSONObject jsonObject) {

        Issue issue = new Issue();
        issue.withUrl(jsonObject.getString(URL_FIELD));
        issue.withHtmlUrl(jsonObject.getString(HTML_URL_FIELD));
        issue.withTitle(jsonObject.getString(TITLE_FIELD));
        issue.withCreatedAt(Instant.parse(jsonObject.getString(CREATED_AT_FIELD)));
        issue.withUpdatedAt(Instant.parse(jsonObject.getString(UPDATED_AT_FIELD)));
        issue.withNumber(jsonObject.getInt(NUMBER_FIELD));
        issue.withState(jsonObject.getString(STATE_FIELD));

        // user is mandatory
        User user = User.fromJson(jsonObject.getJSONObject(USER_FIELD));
        issue.withUser(user);

        // pull request is an optional fields
        if (jsonObject.has(PR_FIELD)){
            PullRequest pullRequest = PullRequest.fromJson(jsonObject.getJSONObject(PR_FIELD));
            issue.withPullRequest(pullRequest);
        }

        return issue;
    }
}
