package com.example.allshayri.Modals;

public class TopContentModal {
    String shayri,shayriId;

    public TopContentModal() {
    }

    public TopContentModal(String shayri, String shayriId) {
        this.shayri = shayri;
        this.shayriId = shayriId;
    }

    public String getShayri() {
        return shayri;
    }

    public void setShayri(String shayri) {
        this.shayri = shayri;
    }

    public String getShayriId() {
        return shayriId;
    }

    public void setShayriId(String shayriId) {
        this.shayriId = shayriId;
    }
}
