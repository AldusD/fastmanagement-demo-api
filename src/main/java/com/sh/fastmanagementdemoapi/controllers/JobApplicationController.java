package com.sh.fastmanagementdemoapi.controllers;

import com.sh.fastmanagementdemoapi.dtos.*;
import com.sh.fastmanagementdemoapi.enums.Status;
import com.sh.fastmanagementdemoapi.models.JobApplication;
import com.sh.fastmanagementdemoapi.services.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/hiring")
public class JobApplicationController {

    @Autowired
    JobApplicationService service;

    @PostMapping("/start")
    public ResponseEntity postApplication(@Valid @RequestBody JobApplicationNameDto candidate) throws Exception {
        int newApplicationId = service.startApplication(candidate.nome());
        return new ResponseEntity<>(new JobApplicationIdDto(newApplicationId), HttpStatus.CREATED);
    }

    @PostMapping("/schedule")
    public ResponseEntity postSchedule(@Valid @RequestBody JobApplicationIdDto id) throws Exception {
        service.scheduleInterview(id.codCandidato());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/disqualify")
    public ResponseEntity postDisqualify(@Valid @RequestBody JobApplicationIdDto id) throws Exception {
        service.endApplication(id.codCandidato());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/approve")
    public ResponseEntity postApprove(@Valid @RequestBody JobApplicationIdDto id) throws Exception {
        service.approveCandidate(id.codCandidato());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/status/candidate/{codCandidato}")
    public ResponseEntity getApplicationStatus(@PathVariable("codCandidato") String codCandidato) throws Exception {
        int id;
        try {
            id = Integer.parseInt(codCandidato);
        } catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String status = service.checkApplicationStatus(id);
        return new ResponseEntity<>(new JobApplicationStatusDto(status), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getall() throws Exception {
        List<JobApplication> applications = service.sendAll();
        return new ResponseEntity<>(new JobApplicationAllDto(applications), HttpStatus.OK);
    }

    @GetMapping("/approved")
    public ResponseEntity getApproved() throws Exception {
        List<String> approved = service.sendApproved();
        return new ResponseEntity<>(new JobApplicationApprovedDto(approved), HttpStatus.OK);
    }
}