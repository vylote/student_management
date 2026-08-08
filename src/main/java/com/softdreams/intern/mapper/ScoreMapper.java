package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.request.CreateScoreRequest;
import com.softdreams.intern.dto.request.UpdateScoreRequest;
import com.softdreams.intern.dto.response.ScoreResponse;
import com.softdreams.intern.entity.Score;

import java.util.List;

public interface ScoreMapper {

    Score toScore(CreateScoreRequest request);

    void updateScore(UpdateScoreRequest request, Score score);

    ScoreResponse toResponse(Score score);

    List<ScoreResponse> toResponseList(List<Score> scores);
}
