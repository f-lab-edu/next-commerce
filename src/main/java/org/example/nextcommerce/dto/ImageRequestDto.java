package org.example.nextcommerce.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;


@Slf4j
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class ImageRequestDto {

    private InputStream imageInputStream;
    private String originalName;
    private String contentType;
    private Long fileSize;

}
