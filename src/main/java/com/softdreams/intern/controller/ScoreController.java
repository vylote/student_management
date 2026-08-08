package com.softdreams.intern.controller;

import com.softdreams.intern.dto.request.CreateScoreRequest;
import com.softdreams.intern.dto.request.UpdateScoreRequest;
import com.softdreams.intern.dto.response.ApiResponse;
import com.softdreams.intern.dto.response.ScoreResponse;
import com.softdreams.intern.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    final ScoreService scoreService;

    @PostMapping
    public ApiResponse<ScoreResponse> createScore(@RequestBody CreateScoreRequest request) {
        return ApiResponse.<ScoreResponse>builder()
                .data(scoreService.createScore(request))
                .build();
    }

    @PutMapping
    public ApiResponse<ScoreResponse> updateScore(@RequestBody UpdateScoreRequest request) {
        return ApiResponse.<ScoreResponse>builder()
                .data(scoreService.updateScore(request))
                .build();
    }

    @GetMapping("/student/{studentId}")
    public ApiResponse<List<ScoreResponse>> getAllScoresByStudentId(@PathVariable Long studentId) {
        return ApiResponse.<List<ScoreResponse>>builder()
                .data(scoreService.getAllScoresByStudentId(studentId))
                .build();
    }
}
