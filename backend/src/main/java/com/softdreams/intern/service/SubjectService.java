package com.softdreams.intern.service;


import com.softdreams.intern.dto.request.CreateSubjectRequest;
import com.softdreams.intern.dto.response.SubjectResposne;

public interface SubjectService {

    SubjectResposne addSubject(CreateSubjectRequest request);
}
