package com.softdreams.intern.mapper.impl;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.response.SubjectResponse;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.mapper.SubjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SubjectMapperImpl implements SubjectMapper {
    @Override
    public Subject toSubject(CreateSubjectRequest request) {
        if ( request == null ) {
            return null;
        }

        Subject subject = new Subject();

        subject.setCode( request.getCode() );
        subject.setName( request.getName() );
        subject.setTotalLesson( request.getTotalLesson() );
        subject.setProcessWeight( request.getProcessWeight() );
        subject.setComponentWeight( request.getComponentWeight() );

        return subject;
    }

    @Override
    public SubjectResponse toResponse(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectResponse subjectResponse = new SubjectResponse();

        subjectResponse.setId( subject.getId() );
        subjectResponse.setCode( subject.getCode() );
        subjectResponse.setName( subject.getName() );
        subjectResponse.setTotalLesson( subject.getTotalLesson() );
        subjectResponse.setProcessWeight( subject.getProcessWeight() );
        subjectResponse.setComponentWeight( subject.getComponentWeight() );

        return subjectResponse;
    }

    @Override
    public List<SubjectResponse> toResponses(List<Subject> subjects) {
        if ( subjects == null ) {
            return Collections.emptyList();
        }

        List<SubjectResponse> list = new ArrayList<SubjectResponse>( subjects.size() );
        for ( Subject subject : subjects ) {
            list.add( toResponse( subject ) );
        }

        return list;
    }
}
