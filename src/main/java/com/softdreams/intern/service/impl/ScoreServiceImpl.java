package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateScoreRequest;
import com.softdreams.intern.dto.request.UpdateScoreRequest;
import com.softdreams.intern.dto.response.ScoreResponse;
import com.softdreams.intern.entity.Score;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.ScoreMapper;
import com.softdreams.intern.repository.ScoreRepository;
import com.softdreams.intern.repository.SubjectRepository;
import com.softdreams.intern.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    final ScoreRepository scoreRepository;

    final SubjectRepository subjectRepository;

    final ScoreMapper scoreMapper;

    @Override
    public ScoreResponse createScore(CreateScoreRequest request) {
        if (scoreRepository.existsByStudentIdAndSubjectId(request.getStudentId(), request.getSubjectId())) {
            throw new AppException(ErrorCode.SCORE_ALREADY_EXISTS);
        }

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));

        Score score = scoreMapper.toScore(request);

        calculateAndSetFinalScore(score, subject);

        return scoreMapper.toResponse(scoreRepository.save(score));
    }

    @Transactional
    @Override
    public ScoreResponse updateScore(UpdateScoreRequest request) {
        Score score = scoreRepository.findById(request.getId())
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
        List<Score> scores = scoreRepository.findByStudentId(studentId); // Cần định nghĩa hàm này trong ScoreRepository
        return scoreMapper.toResponseList(scores);
    }
}
