package com.codewithomarm.rosterup.tenant.v1.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    /**
     * Provides a brief title for the error condition
     */
    private String title;
    /**
     * contains the HTTP status code for the current request
     */
    private int status;
    /**
     * List containing a short description of the errors
     */
    private List<String> details;
    /**
     * The time in milliseconds when the error occurred
     */
    private long timeStamp;
    /**
     * Exception class name or stack trace
     */
    private String developerMessage;

    public ErrorResponse() {
        this.details = new ArrayList<>();
    }

    public ErrorResponse(String title, int status, String developerMessage) {
        this();
        this.title = title;
        this.status = status;
        this.timeStamp = System.currentTimeMillis();
        this.developerMessage = developerMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public void addDetail(String detail){
        this.details.add(detail);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", details=" + details +
                ", timeStamp=" + timeStamp +
                ", developerMessage='" + developerMessage + '\'' +
                '}';
    }
}
