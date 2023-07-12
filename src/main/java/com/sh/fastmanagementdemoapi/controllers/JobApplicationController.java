package com.sh.fastmanagementdemoapi.controllers;

import com.sh.fastmanagementdemoapi.dtos.JobApplicationIdDto;
import com.sh.fastmanagementdemoapi.dtos.JobApplicationNameDto;
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
@RequestMapping("/api/v1/hiring")
public class JobApplicationController {

    JobApplicationService service = new JobApplicationService();

    @PostMapping("/start")
    public ResponseEntity postApplication(@Valid @RequestBody JobApplicationNameDto candidate) throws Exception {
        int newApplicationId = service.startApplication(candidate.nome());
        return new ResponseEntity<>(newApplicationId, HttpStatus.CREATED);
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
        service.approveCandidate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<String>> getApproved() throws Exception {
        List<String> approved = service.sendApproved();
        return new ResponseEntity<>(approved, HttpStatus.OK);
    }
}