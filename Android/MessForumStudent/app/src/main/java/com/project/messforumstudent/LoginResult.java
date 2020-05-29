package com.project.messforumstudent;

public class LoginResult {

    private int id;

    private long rollno;

    private String name;

    private String gender;

    private String batch;

    private String dept;

    private String room;

    private String accessToken;

    public  String getAccessToken() {
        return accessToken;
    }

    public int getIdd() {
        return id;
    }

    public long getRoll() {
        return rollno;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBatch() {
        return batch;
    }

    public String getDept() {
        return dept;
    }

    public String getRoom() {
        return room;
    }
}