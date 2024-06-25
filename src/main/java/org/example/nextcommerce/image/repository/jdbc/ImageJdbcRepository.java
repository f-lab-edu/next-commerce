package org.example.nextcommerce.image.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.nextcommerce.common.exception.DatabaseException;
import org.example.nextcommerce.common.utils.errormessage.ErrorCode;
import org.example.nextcommerce.image.dto.ImageDto;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<ImageDto> imageDtoRowMapper(){
        return ((rs, rowNum) -> {
           ImageDto dto = ImageDto.builder()
                   .imageId(rs.getLong("image_id"))
                   .postId(rs.getLong("post_id"))
                   .filePath(rs.getString("path"))
                   .originalName(rs.getString("original_name"))
                   .fileSize(rs.getLong("size"))
                   .createdTime(rs.getTimestamp("created_time"))
                   .build();
           return dto;
        });
    }

    public void saveAll(List<ImageDto> imageDtoList, Long postId){
        String sql = "INSERT INTO images (post_id, path, original_name, size) VALUES (?,?,?,?)";

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


    public List<ImageDto> findAllByPostId(Long postId){
        String sql = "SELECT * FROM images WHERE post_id=?";
        return jdbcTemplate.query(sql, imageDtoRowMapper(), postId);
    }

    public void deleteByPostId(Long postId){
        String sql = "DELETE FROM images WHERE post_id=?";
        if(jdbcTemplate.update(sql, postId) < 1){
            throw new DatabaseException(ErrorCode.PostsDeleteFail);
        }
    }

    public ImageDto findOneByPostId(Long postId){
        String sql = "SELECT * FROM images WHERE post_id=? LIMIT 1 ";
        return jdbcTemplate.queryForObject(sql, imageDtoRowMapper(),postId);
    }

    public ImageDto findRecentOneByPostId(Long postId){
        String sql = "SELECT * FROM images WHERE post_id=?";
        List<ImageDto> imageDtoList = jdbcTemplate.query(sql, imageDtoRowMapper(), postId);
        return imageDtoList.stream().max(Comparator.comparing(ImageDto::getCreatedTime)).orElseThrow(DatabaseException::new);

    }

    public ImageDto findByImageId(Long imageId){
        String sql = "SELECT * FROM images WHERE image_id=?";
        return jdbcTemplate.queryForObject(sql, imageDtoRowMapper(), imageId);
    }


}
