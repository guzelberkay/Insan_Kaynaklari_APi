package org.takim2.insan_kaynaklari_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.takim2.insan_kaynaklari_api.dto.request.CommentSaveRequestDto;
import org.takim2.insan_kaynaklari_api.dto.request.LeaveSaveRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.response.CommentResponseDto;
import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")

public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save-comment")
    public ResponseEntity<ResponseDTO<Boolean>> saveComment(@RequestBody CommentSaveRequestDto dto) {
        commentService.saveComment(dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Yorum Başarıyla Eklendi.\n Teşekkür Ederiz.").data(Boolean.TRUE).build());
    }

    @GetMapping("/get-comment-list")
    public ResponseEntity<ResponseDTO<List<CommentResponseDto>>> getCommentList(
            @RequestParam int page,
            @RequestParam int size) {
        List<CommentResponseDto> comments = commentService.getAllComment(page, size);
        return ResponseEntity.ok(ResponseDTO.<List<CommentResponseDto>>builder()
                .code(200)
                .message("Yorumlar Listelendi")
                .data(comments)
                .build());
    }
}
