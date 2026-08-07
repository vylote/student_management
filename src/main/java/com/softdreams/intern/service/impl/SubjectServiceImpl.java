package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.response.SubjectResposne;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.SubjectMapper;
import com.softdreams.intern.repository.SubjectRepository;
import com.softdreams.intern.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    final SubjectRepository subjectRepository;

    final SubjectMapper subjectMapper;

    @Override
    public SubjectResposne addSubject(CreateSubjectRequest request) {
        if (subjectRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.SUBJECT_ALREADY_EXISTS);
        }

        Subject subject = subjectMapper.toSubject(request);
        return subjectMapper.toResponse(subjectRepository.save(subject));
    }
}
