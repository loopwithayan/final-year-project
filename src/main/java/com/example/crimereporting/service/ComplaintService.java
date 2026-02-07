package com.example.crimereporting.service;

import com.example.crimereporting.dto.ComplaintRequest;
import com.example.crimereporting.dto.ComplaintResponse;
import com.example.crimereporting.model.User;
import java.util.List;

public interface ComplaintService {
    ComplaintResponse submitComplaint(ComplaintRequest request, User submittedBy);
    List<ComplaintResponse> getAllComplaints();
}
