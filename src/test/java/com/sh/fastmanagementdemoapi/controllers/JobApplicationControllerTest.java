package com.sh.fastmanagementdemoapi.controllers;

import com.sh.fastmanagementdemoapi.Exceptions.ConflictException;
import com.sh.fastmanagementdemoapi.Exceptions.NotFoundException;
import com.sh.fastmanagementdemoapi.enums.Status;
import com.sh.fastmanagementdemoapi.models.JobApplication;
import com.sh.fastmanagementdemoapi.services.JobApplicationService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@WebMvcTest
public class JobApplicationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private JobApplicationService service;

    @Test
    @DisplayName("POST /api/v1/start: Should return 201 when input is valid")
    public void testStartCreated() throws Exception {
        JobApplication application = new JobApplication(1, "candidato", Status.RECEBIDO);
        when(service.startApplication("candidato")).thenReturn(application.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hiring/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"candidato\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codCandidato", Is.is(1)));

    }

    @Test
    @DisplayName("POST /api/v1/hiring/start: Should return 409 when name exists")
    public void testStartConflict() throws Exception {
        when(service.startApplication("conflict"))
                .thenThrow(new ConflictException("Candidato já participa do processo"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hiring/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"conflict\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("POST /api/v1/hiring/start: Should return 400 when input is invalid")
    public void testStartBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hiring/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\" \"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/hiring/schedule: Should return 404 when no RECEBIDO candidate with passed id")
    public void testScheduleNotFound() throws Exception {
        doThrow(new NotFoundException("Candidato não encontrado")).when(service).scheduleInterview(1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hiring/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codCandidato\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/hiring/schedule: Should return 200")
    public void testScheduleOk() throws Exception {
        doNothing().when(service).scheduleInterview(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hiring/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codCandidato\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("POST /api/v1/hiring/disqualify: Should return 404 if no candidate match")
    public void testDisqualifyNotFound() throws Exception {
        doThrow(new NotFoundException("Candidato não encontrado")).when(service).endApplication(1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hiring/disqualify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codCandidato\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/hiring/disqualify: Should return 204")
    public void testDisqualifyOk() throws Exception {
        doNothing().when(service).endApplication(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hiring/disqualify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codCandidato\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("POST /api/v1/hiring/disqualify: Should return 404 if no candidate match")
    public void testApproveNotFound() throws Exception {
        doThrow(new NotFoundException("Candidato não encontrado")).when(service).approveCandidate(1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hiring/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codCandidato\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/hiring/approve: Should return 200")
    public void testApproveOk() throws Exception {
        doNothing().when(service).approveCandidate(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hiring/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codCandidato\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/hiring/status/candidate/{codCandidato}: Should return 404 if no candidate match")
    public void testCandidateStatusNotFound() throws Exception {
        doThrow(new NotFoundException("Candidato não encontrado")).when(service).checkApplicationStatus(1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hiring/status/candidate/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v1/hiring/status/candidate/{codCandidato}: Should return 200")
    public void testCandidateStatusOk() throws Exception {
        when(service.checkApplicationStatus(1)).thenReturn(Status.RECEBIDO.name());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hiring/status/candidate/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(Status.RECEBIDO.name())));;
    }

    @Test
    @DisplayName("GET /api/v1/hiring/approved: Should return 200")
    public void testCandidateOk() throws Exception {
        when(service.sendApproved()).thenReturn(Arrays.asList("candidate1", "candidate2"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hiring/approved"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.approved").exists());
    }
}
