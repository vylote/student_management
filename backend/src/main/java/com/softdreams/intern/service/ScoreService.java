package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.UpdateScoreRequest;
import com.softdreams.intern.dto.response.ScoreResponse;
import com.softdreams.intern.entity.Score;
import com.softdreams.intern.entity.Subject;

import java.util.List;

public interface ScoreService {

    ScoreResponse updateScore(UpdateScoreRequest request);

    void calculateAndSetFinalScore(Score score, Subject subject);

    List<ScoreResponse> getAllScoresByStudentId(Long studentId);
}
