package com.bitAndroid.eduzo.recyclerview;

import android.net.Uri;

public class AboutUsData {

    Uri imgUrl;
    String name;
    String role;

    public AboutUsData() {
    }

    public AboutUsData(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public AboutUsData(Uri imgUrl, String name, String role) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.role = role;
    }

    public Uri getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Uri imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}