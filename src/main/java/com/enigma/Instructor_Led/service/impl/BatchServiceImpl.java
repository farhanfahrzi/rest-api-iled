package com.enigma.Instructor_Led.service.impl;

//import com.enigma.Instructor_Led.dto.request.CreateBatchRequest;
//import com.enigma.Instructor_Led.dto.request.UpdateBatchRequest;
//import com.enigma.Instructor_Led.dto.response.BatchResponse;
//import com.enigma.Instructor_Led.entity.Batch;
//import com.enigma.Instructor_Led.repository.BatchRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.stream.Collectors;

//@Service
//public class BatchServiceImpl implements BatchService {
//
//    @Autowired
//    private BatchRepository batchRepository;
//
//    @Override
//    public BatchResponse create(CreateBatchRequest createBatchRequest) {
//        Batch batch = Batch.builder()
//                .batchName(createBatchRequest.getBatchName())
//                .build();
//        batch = batchRepository.save(batch);
//        return new BatchResponse(batch.getId(), batch.getBatchName());
//    }
//
//    @Override
//    public BatchResponse update(UpdateBatchRequest updateBatchRequest) {
//        Batch batch = batchRepository.findById(updateBatchRequest.getId())
//                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + updateBatchRequest.getId()));
//        batch.setBatchName(updateBatchRequest.getBatchName());
//        batch = batchRepository.save(batch);
//        return new BatchResponse(batch.getId(), batch.getBatchName());
//    }
//
//    @Override
//    public BatchResponse getById(String id) {
//        Batch batch = batchRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + id));
//        return new BatchResponse(batch.getId(), batch.getBatchName());
//    }
//
//    @Override
//    public List<BatchResponse> getAll() {
//        return batchRepository.findAll().stream()
//                .map(batch -> new BatchResponse(batch.getId(), batch.getBatchName()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void delete(String id) {
//        Batch batch = batchRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + id));
//        batchRepository.delete(batch);
//    }
//}