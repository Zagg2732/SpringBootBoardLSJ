package com.study.springbootboardlsj.web;

import com.study.springbootboardlsj.web.dto.*;
import com.study.springbootboardlsj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(){
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardListResponseDto> boardDtoList = boardService.getBoardList(pageable);
        model.addAttribute("postList", boardDtoList);
        return "board/list.html";
    }
    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }

    @PostMapping("/post")
    public String save(@RequestParam("file") MultipartFile files, BoardSaveRequestDto boardSaveRequestDto) {
        boardService.savePost(files, boardSaveRequestDto);

        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", boardService.getPost(id));
        return "board/detail.html";
    }

    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        BoardResponseDto dto = boardService.getPost(id);
        model.addAttribute("post", dto);

        return "board/edit.html";
    }

    @PutMapping("/post/edit/{id}")
    public String update(@PathVariable("id") Long id, @RequestParam("file") MultipartFile files, BoardUpdateRequestDto boardUpdateRequestDto) {

        boardService.updatePost(id, files, boardUpdateRequestDto);

        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("id") Long id) throws IOException {
        BoardFileResponseDto dto = boardService.getFile(id);
        Path path = Paths.get(dto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getOrigFilename() + "\"")
                .body(resource);
    }

}