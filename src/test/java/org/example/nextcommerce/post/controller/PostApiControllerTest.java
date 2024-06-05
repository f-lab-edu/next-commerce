package org.example.nextcommerce.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.nextcommerce.member.repository.jdbc.MemberJdbcRepository;
import org.example.nextcommerce.member.service.MemberService;
import org.example.nextcommerce.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberJdbcRepository memberJdbcRepository;

    @MockBean
    private MemberService memberService;

    @MockBean
    private PostService postService;


    @Test
    public void successImageUpload() throws Exception{

        ClassPathResource classPathResource = new ClassPathResource("/test.jpg");

        mockMvc.perform(multipart("/api/posts/images")
                .file(new MockMultipartFile("files", "test.jpg", "image/jpeg", classPathResource.getInputStream()))
        ).andExpect(status().isOk()).andDo(print());

    }


}
