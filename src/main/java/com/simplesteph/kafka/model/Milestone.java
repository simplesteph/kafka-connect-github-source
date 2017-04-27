
package com.simplesteph.kafka.model;

import java.util.HashMap;
import java.util.Map;

public class Milestone {

    private String url;
    private String htmlUrl;
    private String labelsUrl;
    private Integer id;
    private Integer number;
    private String state;
    private String title;
    private String description;
    private Creator creator;
    private Integer openIssues;
    private Integer closedIssues;
    private String createdAt;
    private String updatedAt;
    private String closedAt;
    private String dueOn;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Milestone() {
    }

    /**
     * 
     * @param openIssues
     * @param closedAt
     * @param state
     * @param number
     * @param url
     * @param creator
     * @param updatedAt
     * @param id
     * @param htmlUrl
     * @param title
     * @param closedIssues
     * @param description
     * @param createdAt
     * @param dueOn
     * @param labelsUrl
     */
    public Milestone(String url, String htmlUrl, String labelsUrl, Integer id, Integer number, String state, String title, String description, Creator creator, Integer openIssues, Integer closedIssues, String createdAt, String updatedAt, String closedAt, String dueOn) {
        super();
        this.url = url;
        this.htmlUrl = htmlUrl;
        this.labelsUrl = labelsUrl;
        this.id = id;
        this.number = number;
        this.state = state;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.openIssues = openIssues;
        this.closedIssues = closedIssues;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.dueOn = dueOn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Milestone withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Milestone withHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
        return this;
    }

    public String getLabelsUrl() {
        return labelsUrl;
    }

    public void setLabelsUrl(String labelsUrl) {
        this.labelsUrl = labelsUrl;
    }

    public Milestone withLabelsUrl(String labelsUrl) {
        this.labelsUrl = labelsUrl;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Milestone withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Milestone withNumber(Integer number) {
        this.number = number;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Milestone withState(String state) {
        this.state = state;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Milestone withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Milestone withDescription(String description) {
        this.description = description;
        return this;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Milestone withCreator(Creator creator) {
        this.creator = creator;
        return this;
    }

    public Integer getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(Integer openIssues) {
        this.openIssues = openIssues;
    }

    public Milestone withOpenIssues(Integer openIssues) {
        this.openIssues = openIssues;
        return this;
    }

    public Integer getClosedIssues() {
        return closedIssues;
    }

    public void setClosedIssues(Integer closedIssues) {
        this.closedIssues = closedIssues;
    }

    public Milestone withClosedIssues(Integer closedIssues) {
        this.closedIssues = closedIssues;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Milestone withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Milestone withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public Milestone withClosedAt(String closedAt) {
        this.closedAt = closedAt;
        return this;
    }

    public String getDueOn() {
        return dueOn;
    }

    public void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    public Milestone withDueOn(String dueOn) {
        this.dueOn = dueOn;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Milestone withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
