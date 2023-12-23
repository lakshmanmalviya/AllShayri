package com.example.allshayri.Modals;

public class ImageModal {
    String image,imageId;
    public ImageModal() {
    }

    public ImageModal(String image, String imageId) {
        this.image = image;
        this.imageId = imageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
