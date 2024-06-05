package org.example.nextcommerce.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageDto {

    private Long imageId;
    private Long postId;
    private String originalName;
    private String filePath;
    private Long fileSize;


   public ImageDto(String originalName, String filePath, Long fileSize){
       this.originalName = originalName;
       this.filePath = filePath;
       this.fileSize = fileSize;
   }




}
