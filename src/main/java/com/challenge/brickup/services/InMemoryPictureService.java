package com.challenge.brickup.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class InMemoryPictureService implements PictureService {
    private final String imgPath = "D:/imgid_";

    @Override
    public Boolean saveImg(MultipartFile img, String id) {
        try {
            if (img == null) {
                throw new RuntimeException("Img must not be null");
            }
            if (id == null) {
                throw new RuntimeException("invalid id");
            }
            if (!Objects.requireNonNull(img.getOriginalFilename()).endsWith(".png")) {
                throw new RuntimeException("img must be .png");
            }
            byte[] bytes = img.getBytes();
            Path path = Paths.get(this.imgPath + id + ".png");
            if (!Files.exists(path)) {
                Files.write(path, bytes);
                return true;
            } else throw new RuntimeException("file already exists");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public byte[] getImg(String imgName) {
        try {
            File img = new File(this.imgPath + imgName + ".png");
            return Files.readAllBytes(img.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeImg(String imgName) {
        try {
            File img = new File(this.imgPath + imgName + ".png");
            if (img.exists()) {
                Files.delete(img.toPath());
                return true;
            } else return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
