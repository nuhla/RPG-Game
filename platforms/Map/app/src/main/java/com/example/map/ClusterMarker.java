package com.example.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {

    private LatLng position;
    private String title;
    private String snippet;
    private int iconPicture;

    public void setPosition(LatLng position) {
        this.position = position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @NonNull
    @Override
    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getIconPicture() {
        return iconPicture;
    }

    public void setIconPicture(int inconPicture) {
        this.iconPicture = inconPicture;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return null;
    }

    public ClusterMarker(LatLng position, String title, String snippet, int inconPicture) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.iconPicture = inconPicture;
    }

    public ClusterMarker() {

    }

}