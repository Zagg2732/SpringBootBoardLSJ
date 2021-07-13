package com.study.springbootboardlsj.dto;


import com.study.springbootboardlsj.domain.board.Board;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BoardSaveRequestDto {

    private String title;
    private String content;
    private String author;
    private String origFilename;
    private String filename;
    private String filePath;

    @Builder
    public BoardSaveRequestDto(String title, String content, String author, String origFilename, String filename, String filePath) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
    }

    public void setFile(String origFilename, String filename, String filePath) {
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }

}
