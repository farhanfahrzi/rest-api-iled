package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateBatchRequest;
import com.enigma.Instructor_Led.dto.request.UpdateBatchRequest;
import com.enigma.Instructor_Led.dto.response.BatchResponse;

import java.util.List;

public interface BatchService {

    BatchResponse create(CreateBatchRequest createBatchRequest);
    BatchResponse update(UpdateBatchRequest updateBatchRequest);
    BatchResponse getById(String id);
    List<BatchResponse> getAll();
    void delete(String id);
}
