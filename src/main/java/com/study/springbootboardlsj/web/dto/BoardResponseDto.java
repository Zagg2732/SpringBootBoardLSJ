package com.study.springbootboardlsj.web.dto;

import com.study.springbootboardlsj.domain.board.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String origFilename;
    private LocalDateTime modifiedDate;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.origFilename = entity.getOrigFilename();
        this.modifiedDate = entity.getModifiedDate();
    }

}

