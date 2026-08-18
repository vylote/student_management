package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.UpdateScoreRequest;
import com.softdreams.intern.dto.response.ApiResponse;
import com.softdreams.intern.dto.response.ScoreResponse;
import com.softdreams.intern.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    final ScoreService scoreService;

    @PreAuthorize("hasAuthority('score:write')")
    @PutMapping
    public ApiResponse<ScoreResponse> updateScore(@RequestBody UpdateScoreRequest request) {
        return ApiResponse.<ScoreResponse>builder()
                .data(scoreService.updateScore(request))
                .build();
    }

    @PreAuthorize("hasAuthority('score:read')")
    @GetMapping("/student/{studentId}")
    public ApiResponse<List<ScoreResponse>> getAllScoresByStudentId(@PathVariable Long studentId) {
        return ApiResponse.<List<ScoreResponse>>builder()
                .data(scoreService.getAllScoresByStudentId(studentId))
                .build();
    }
}
