package com.sh.fastmanagementdemoapi.services;

import com.sh.fastmanagementdemoapi.enums.Status;
import com.sh.fastmanagementdemoapi.models.JobApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobApplicationService {

    private List<JobApplication> applications = new ArrayList<JobApplication>();
    private int lastId = 0;

    private int createId() {
        this.lastId++;
        return this.lastId;
    }

    private Optional<JobApplication> findCandidateById(int id) {
        if (id <= 0) return Optional.empty();
        for (int i = 0; i < applications.size(); i++) {
            final JobApplication candidate = applications.get(i);
            if (candidate.getId() == id ) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }

    private Optional<JobApplication> findCandidateByName(String name) {
        for (int i = 0; i < applications.size(); i++) {
            final JobApplication candidate = applications.get(i);
            if (candidate.getName().equals(name)) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }

    public int startApplication(String nameInput) throws Exception {
        final String name = nameInput.trim();
        if (name.isEmpty()) throw new Exception("Nome inválido");

        final Optional<JobApplication> verifyName = findCandidateByName(name);
        if (!verifyName.isEmpty()) throw new Exception("Candidato já participa do processo");

        final int id = this.createId();
        JobApplication candidate = new JobApplication(id, name, Status.RECEBIDO);
        this.applications.add(candidate);
        return id;
    }

    public void scheduleInterview(int id) throws Exception {
        final Optional<JobApplication> verifyCandidate = findCandidateById(id);
        if (verifyCandidate.isEmpty()) throw new Exception("Candidato não encontrado");

        final JobApplication candidate = verifyCandidate.get();
        if (candidate.getStatus() != Status.RECEBIDO) throw new Exception("Candidato não encontrado");

        candidate.setStatus(Status.QUALIFICADO);
    }

    public void endApplication(int id) throws Exception {
        final Optional<JobApplication> verifyCandidate = findCandidateById(id);
        if (verifyCandidate.isEmpty()) throw new Exception("Candidato não encontrado");

        final JobApplication candidate = verifyCandidate.get();
        applications.remove(candidate);
    }

    public String checkApplicationStatus(int id) throws Exception {
        final Optional<JobApplication> verifyCandidate = findCandidateById(id);
        if (verifyCandidate.isEmpty()) throw new Exception("Candidato não encontrado");

        return verifyCandidate.get().getStatus().name();
    }

    public void approveCandidate(int id) throws Exception {
        final Optional<JobApplication> verifyCandidate = findCandidateById(id);
        if (verifyCandidate.isEmpty()) throw new Exception("Candidato não encontrado");

        final JobApplication candidate = verifyCandidate.get();
        if (candidate.getStatus() != Status.QUALIFICADO) throw new Exception("Candidato não encontrado");

        candidate.setStatus(Status.APROVADO);
    }

    public List<String> sendApproved() {
        final ArrayList<String> approved = new ArrayList<>();
        for (int i = 0; i < applications.size(); i++) {
            final JobApplication candidate = applications.get(i);
            if (candidate.getStatus() == Status.APROVADO) {
                approved.add(candidate.getName());
            }
        }
        return approved;
    }
}
