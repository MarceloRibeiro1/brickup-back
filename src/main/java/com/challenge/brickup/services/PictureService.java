package com.challenge.brickup.services;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    Boolean saveImg(MultipartFile img, String id);

    byte[] getImg(String imgName);

    boolean removeImg(String imgName);


}
