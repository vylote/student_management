package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.UpdateScoreRequest;
import com.softdreams.intern.dto.response.ScoreResponse;
import com.softdreams.intern.entity.Score;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.ScoreMapper;
import com.softdreams.intern.repository.ScoreRepository;
import com.softdreams.intern.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    final ScoreRepository scoreRepository;

    final ScoreMapper scoreMapper;

    @Transactional
    @Override
    public ScoreResponse updateScore(UpdateScoreRequest request) {
        Score score = scoreRepository.findByStudentIdSubjectId(request.getStudentId(), request.getSubjectId())
                        .orElseThrow(() -> new AppException(ErrorCode.SCORE_NOT_FOUND));

        scoreMapper.updateScore(request, score);

        calculateAndSetFinalScore(score, score.getSubject());
        return scoreMapper.toResponse(scoreRepository.save(score));
    }

    public void calculateAndSetFinalScore(Score score, Subject subject) {
        double finalScore = score.getProcessScore() * subject.getProcessWeight()
                + score.getComponentScore() * subject.getComponentWeight();

        finalScore = Math.round(finalScore * 10.0) / 10.0;

        score.setFinalScore(finalScore);
        score.setPassed(finalScore >= 4.0);
    }

    @Override
    public List<ScoreResponse> getAllScoresByStudentId(Long studentId) {
        List<Score> scores = scoreRepository.findByStudentId(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.SCORE_NOT_FOUND));
        return scoreMapper.toResponseList(scores);
    }
}
