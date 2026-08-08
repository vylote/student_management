package com.softdreams.intern.mapper.impl;

import com.softdreams.intern.dto.request.CreateScoreRequest;
import com.softdreams.intern.dto.request.UpdateScoreRequest;
import com.softdreams.intern.dto.response.ScoreResponse;
import com.softdreams.intern.entity.Score;
import com.softdreams.intern.entity.Student;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.mapper.ScoreMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScoreMapperImpl implements ScoreMapper {
    @Override
    public Score toScore(CreateScoreRequest request) {
        if (request == null)
            return null;

        Score score = new Score();
        score.setProcessScore(request.getProcessScore());
        score.setComponentScore(request.getComponentScore());

        if (request.getStudentId() != null) {
            Student student = new Student();
            student.setId(request.getStudentId());
            score.setStudent(student);
        }

        if (request.getSubjectId() != null) {
            Subject subject = new Subject();
            subject.setId(request.getSubjectId());
            score.setSubject(subject);
        }

        return score;
    }

    @Override
    public void updateScore(UpdateScoreRequest request, Score score) {
        if (request == null || score == null)
            return;

        score.setProcessScore(request.getProcessScore());
        score.setComponentScore(request.getComponentScore());
    }

    @Override
    public ScoreResponse toResponse(Score score) {
        if (score == null)
            return null;

        ScoreResponse response = new ScoreResponse();

        response.setStudentId( scoreStudentId( score ) );
        response.setSubjectId( scoreSubjectId( score ) );
        response.setId( score.getId() );
        response.setProcessScore( score.getProcessScore() );
        response.setComponentScore( score.getComponentScore() );
        response.setFinalScore( score.getFinalScore() );
        response.setPassed( score.isPassed() );

        return response;
    }

    @Override
    public List<ScoreResponse> toResponseList(List<Score> scores) {
        if (scores == null)
            return null;

        List<ScoreResponse> list = new ArrayList<>(scores.size());

        for (Score score : scores) {
            list.add(toResponse(score));
        }

        return list;
    }

    private Long scoreStudentId(Score score) {
        Student student = score.getStudent();
        if ( student == null ) {
            return null;
        }
        return student.getId();
    }

    private Long scoreSubjectId(Score score) {
        Subject subject = score.getSubject();
        if ( subject == null ) {
            return null;
        }
        return subject.getId();
    }
}
