package com.example.crimereporting.service;

import com.example.crimereporting.dto.ComplaintRequest;
import com.example.crimereporting.dto.ComplaintResponse;
import com.example.crimereporting.model.Complaint;
import com.example.crimereporting.model.User;
import com.example.crimereporting.repository.ComplaintRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComplaintServiceImpl implements ComplaintService {

    private static final String DEFAULT_STATUS = "SUBMITTED";

    private final ComplaintRepository complaintRepository;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    public ComplaintResponse submitComplaint(ComplaintRequest request, User submittedBy) {
        Complaint complaint = new Complaint(
            request.title(),
            request.description(),
            DEFAULT_STATUS,
            submittedBy
        );
        Complaint saved = complaintRepository.save(complaint);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private ComplaintResponse toResponse(Complaint complaint) {
        return new ComplaintResponse(
            complaint.getId(),
            complaint.getTitle(),
            complaint.getDescription(),
            complaint.getStatus(),
            complaint.getCreatedAt(),
            complaint.getSubmittedBy().getEmail()
        );
    }
}
