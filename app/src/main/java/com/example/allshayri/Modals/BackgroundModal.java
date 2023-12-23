package com.example.allshayri.Modals;
public class BackgroundModal {
    String backImage,backImageId;
    public BackgroundModal() {
    }
    public BackgroundModal(String backImage, String backImageId) {
        this.backImage = backImage;
        this.backImageId = backImageId;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getBackImageId() {
        return backImageId;
    }

    public void setBackImageId(String backImageId) {
        this.backImageId = backImageId;
    }
}
