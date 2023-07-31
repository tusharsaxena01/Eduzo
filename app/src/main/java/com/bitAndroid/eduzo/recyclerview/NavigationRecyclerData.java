package com.bitAndroid.eduzo.recyclerview;

import android.net.Uri;

public class NavigationRecyclerData {

    Uri imgUrl;
    String itemText;

    public NavigationRecyclerData() {
    }

    public NavigationRecyclerData(Uri imgUrl, String itemText) {
        this.imgUrl = imgUrl;
        this.itemText = itemText;
    }

    public Uri getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Uri imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}