package com.challenge.brickup.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
class InMemoryPictureServiceTest {

    @Autowired
    InMemoryPictureService inMemoryPictureService;

    MockMultipartFile filePng = new MockMultipartFile("data", "filename.png", "text/plain", "some xml".getBytes());
    MockMultipartFile fileTxt = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

    @Test
    void saveImgSuccess() {
        String imageId = "test_save_success";
        Boolean imgPath = inMemoryPictureService.saveImg(filePng, imageId);
        Assertions.assertEquals(imgPath, true);
        inMemoryPictureService.removeImg(imageId);
    }

    @Test
    void saveImgToThrow() {
        String imageId = "test_save_txt";
        Assertions.assertThrows(RuntimeException.class, () -> inMemoryPictureService.saveImg(fileTxt, imageId));
        inMemoryPictureService.removeImg(imageId);

    }

    @Test
    void imgMustNotBeNull() {
        Assertions.assertThrows(RuntimeException.class, () -> inMemoryPictureService.saveImg(null, "sd"));
    }


}