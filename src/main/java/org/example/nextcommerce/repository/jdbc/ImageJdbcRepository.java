package org.example.nextcommerce.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.dto.ImageDto;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<ImageDto> imageDtoRowMapper(){
        return ((rs, rowNum) -> {
           ImageDto dto = ImageDto.builder()
                   .imageId(rs.getLong("image_id"))
                   .productId(rs.getLong("product_id"))
                   .filePath(rs.getString("path"))
                   .originalName(rs.getString("original_name"))
                   .fileSize(rs.getLong("size"))
                   .build();
           return dto;
        });
    }

    public void saveAll(List<ImageDto> imageDtoList, Long postId){
        String sql = "INSERT INTO images (product_id, path, original_name, size) VALUES (?,?,?,?)";

        BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ImageDto imageDto = imageDtoList.get(i);
                ps.setLong(1, postId);
                ps.setString(2, imageDto.getFilePath());
                ps.setString(3, imageDto.getOriginalName());
                ps.setLong(4, imageDto.getFileSize());
            }

            @Override
            public int getBatchSize() {
                return imageDtoList.size();
            }
        };
        jdbcTemplate.batchUpdate(sql, batchPreparedStatementSetter);
    }

}