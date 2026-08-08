package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateScoreRequest;
import com.softdreams.intern.dto.request.UpdateScoreRequest;
import com.softdreams.intern.dto.response.ScoreResponse;
import com.softdreams.intern.entity.Score;
import com.softdreams.intern.entity.Subject;

import java.util.List;

public interface ScoreService {

    public ScoreResponse createScore(CreateScoreRequest request);

    public ScoreResponse updateScore(UpdateScoreRequest request);

    public void calculateAndSetFinalScore(Score score, Subject subject);

    public List<ScoreResponse> getAllScoresByStudentId(Long studentId);
}
