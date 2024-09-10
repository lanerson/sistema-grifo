package com.example.backend.image;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.util.UUID;

@Entity
public class ImageUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID imageId;
    private String imageName;

    @Lob
    private byte[] dataImage;

    public UUID getImageId() {
        return imageId;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getDataImage() {
        return dataImage;
    }

    public void setDataImage(byte[] dataImage) {
        this.dataImage = dataImage;
    }


}
