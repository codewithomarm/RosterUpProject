package com.codewithomarm.rosterup.roster.v1.common.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private String title;
    private int status;
    private List<String> details;
    private long timeStamp;
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
