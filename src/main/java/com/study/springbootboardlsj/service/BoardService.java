package com.study.springbootboardlsj.service;

import com.study.springbootboardlsj.domain.board.Board;
import com.study.springbootboardlsj.domain.board.BoardRepository;
import com.study.springbootboardlsj.web.dto.*;
import com.study.springbootboardlsj.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long savePost(MultipartFile files, BoardSaveRequestDto boardSaveRequestDto) {

        if(!files.isEmpty()) {
            try {
                String origFilename = files.getOriginalFilename();
                String filename = new MD5Generator(origFilename).toString();

                String savePath = System.getProperty("user.dir") + File.separator + "files";
                if(!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    }catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + File.separator + filename;
                files.transferTo(new File(filePath));

                boardSaveRequestDto.setFile(origFilename, filename, filePath);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return boardRepository.save(boardSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Page<BoardListResponseDto> getBoardList(Pageable pageable) {

        Page<Board> boardList = boardRepository.findAll(pageable);

        return new PageImpl<BoardListResponseDto>(boardList.getContent().stream()
                                                            .map(BoardListResponseDto::new)
                                                            .collect(Collectors.toList()),
                                                boardList.getPageable(),
                                                boardList.getTotalElements());
    }



    @Transactional
    public BoardResponseDto getPost(Long id) {
        Board entity = boardRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Not Found Post id = " + id));

        return new BoardResponseDto(entity);
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public Long updatePost(Long id, MultipartFile files, BoardUpdateRequestDto dto) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not Found Post id = " + id));

        if(!files.isEmpty()) {
            try {
                String origFilename = files.getOriginalFilename();
                String filename = new MD5Generator(origFilename).toString();

                String savePath = System.getProperty("user.dir") + File.separator + "files";
                if(!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + File.separator + filename;
                files.transferTo(new File(filePath));

                //update
                board.update(dto.getTitle(), dto.getContent(), origFilename, filename, filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //파일을 안넣었으면 제목과 내용만 업데이트
            board.update(dto.getTitle(), dto.getContent());
        }
        return id;
    }

    @Transactional
    public BoardFileResponseDto getFile(Long id) {
        Board entity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not Found File id = " + id));

        return new BoardFileResponseDto(entity);
    }

}
