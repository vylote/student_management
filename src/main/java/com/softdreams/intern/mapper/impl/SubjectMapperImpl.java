package com.softdreams.intern.mapper.impl;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.response.SubjectResposne;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.mapper.SubjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public SubjectResposne toResponse(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectResposne subjectResposne = new SubjectResposne();

        subjectResposne.setId( subject.getId() );
        subjectResposne.setCode( subject.getCode() );
        subjectResposne.setName( subject.getName() );
        subjectResposne.setTotalLesson( subject.getTotalLesson() );
        subjectResposne.setProcessWeight( subject.getProcessWeight() );
        subjectResposne.setComponentWeight( subject.getComponentWeight() );

        return subjectResposne;
    }

    @Override
    public List<SubjectResposne> toResponses(List<Subject> subjects) {
        if ( subjects == null ) {
            return null;
        }

        List<SubjectResposne> list = new ArrayList<SubjectResposne>( subjects.size() );
        for ( Subject subject : subjects ) {
            list.add( toResponse( subject ) );
        }

        return list;
    }
}
