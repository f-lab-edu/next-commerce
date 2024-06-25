package org.example.nextcommerce.imagefile;

import org.example.nextcommerce.common.exception.FileHandleException;
import org.example.nextcommerce.image.service.ImageFileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ImageFileServiceTest {

    @Autowired
    private ImageFileService imageFileService;

    @Test
    @DisplayName("파일 경로 유효성 실패 테스트")
    public void failValidImageFileNoPath(){
        String imagePath = "E:\\false";
        assertFalse(imageFileService.validImageFile(imagePath));
    }

    @Test
    @DisplayName("파일 유효성 실패 테스트")
    public void failValidImageFileNotExistFile(){
        String imagePath = "E:\\false.jpeg";
        assertFalse(imageFileService.validImageFile(imagePath));

    }

    @Test
    @DisplayName("파일 다운로드 성공 테스트")
    public void successFileDownload(){
        //given
        Path filePath = Paths.get("src","main","resources","test.jpg");
        //when
        byte[] bytes = imageFileService.downloadImageFile(filePath.toString());
        //then
        assertThat(bytes).isNotEmpty();
        assertEquals("src\\main\\resources\\test.jpg", filePath.toString());
    }

    @Test
    @DisplayName("파일 다운로드 실패 테스트")
    public void failFileDownload(){
        //given
        ClassPathResource classPathResource = new ClassPathResource("/testfail.jpg");

        //when-then
        assertThrows(FileHandleException.class, ()->{
            imageFileService.downloadImageFile(classPathResource.getPath());
        });
    }

    @Test
    @DisplayName("모든 파일(디레토리 포함) 삭제 실패 테스트")
    public void failDeleteDirectoryAll(){
        //given
        Path filePath = Paths.get("src","testDir","deleteDir");
        assertThrows(FileHandleException.class, ()->{
            imageFileService.deleteDirectoryAll(filePath.toString());
        });
    }

}
