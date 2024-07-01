package org.example.nextcommerce.image;

import org.example.nextcommerce.image.controller.ImageApiController;
import org.example.nextcommerce.image.dto.ImageDto;
import org.example.nextcommerce.image.repository.jpa.ImageJpaRepository;
import org.example.nextcommerce.image.service.ImageFileService;
import org.example.nextcommerce.image.service.ImageJpaService;
import org.example.nextcommerce.member.repository.jpa.MemberJpaRepository;
import org.example.nextcommerce.member.service.MemberJpaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ImageApiController.class)
public class ImageApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageJpaRepository imageJpaRepository;

    @MockBean
    private ImageJpaService imageJpaService;

    @MockBean
    private ImageFileService imageFileService;

    @MockBean
    private MemberJpaService memberJpaService;

    @MockBean
    private MemberJpaRepository memberJpaRepository;

    private ImageDto imageDto;

    @Test()
    @DisplayName("이미지 파일 다운로드 성공 테스트")
    public void successImageFile() throws Exception{
        byte[] bytes = {0,1,1,1};
        when(imageJpaService.getImageFileOne(any())).thenReturn(bytes);
        mockMvc.perform(get("/api/image/1").contentType(MediaType.IMAGE_JPEG))
                .andExpect(status().isOk())
                .andDo(print());


    }

}
