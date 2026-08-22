package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.request.SubjectSearchRequest;
import com.softdreams.intern.dto.response.PageResponse;
import com.softdreams.intern.dto.response.SubjectResponse;
import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.mapper.SubjectMapper;
import com.softdreams.intern.repository.SubjectRepository;
import com.softdreams.intern.service.SubjectService;
import com.softdreams.intern.specification.SubjectSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    final SubjectMapper subjectMapper;

    @Override
    public SubjectResponse addSubject(CreateSubjectRequest request) {
        if (subjectRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.SUBJECT_ALREADY_EXISTS);
        }

        Subject subject = subjectMapper.toSubject(request);
        return subjectMapper.toResponse(subjectRepository.save(subject));
    }

    @Transactional
    @Override
    public PageResponse<SubjectResponse> searchSubjects(SubjectSearchRequest request) {

        Specification<Subject> spec = Specification.where(SubjectSpecification.hasNameLike(request.getName()))
                .and(SubjectSpecification.hasCode(request.getCode()));

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        Page<Subject> pages = subjectRepository.findAll(spec, pageable);

        List<SubjectResponse> responses = subjectMapper.toResponses(pages.getContent());
        return PageResponse.of(pages, responses);
    }
}
