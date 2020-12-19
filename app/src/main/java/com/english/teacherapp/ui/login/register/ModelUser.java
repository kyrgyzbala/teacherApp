package com.english.teacherapp.ui.login.register;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class ModelUser {

    private String name;
    private String logo;
    private Boolean isProfileDone;
    @ServerTimestamp
    private Timestamp time;
    private String uid;

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public ModelUser() {
    }

    public ModelUser(String name, String logo, Boolean isProfileDone) {
        this.name = name;
        this.logo = logo;
        this.isProfileDone = isProfileDone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getProfileDone() {
        return isProfileDone;
    }

    public void setProfileDone(Boolean profileDone) {
        isProfileDone = profileDone;
    }
}
