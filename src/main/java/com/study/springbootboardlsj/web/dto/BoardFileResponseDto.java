package com.study.springbootboardlsj.web.dto;


import com.study.springbootboardlsj.domain.board.Board;
import lombok.Getter;

@Getter
public class BoardFileResponseDto {

    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;

    public BoardFileResponseDto(Board entity) {
        this.id = entity.getId();
        this.origFilename = entity.getOrigFilename();
        this.filename = entity.getFilename();
        this.filePath = entity.getFilePath();
    }

}
