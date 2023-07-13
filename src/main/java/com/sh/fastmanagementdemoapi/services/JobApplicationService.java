package com.sh.fastmanagementdemoapi.services;

import com.sh.fastmanagementdemoapi.Exceptions.ConflictException;
import com.sh.fastmanagementdemoapi.Exceptions.InvalidBodyException;
import com.sh.fastmanagementdemoapi.Exceptions.NotFoundException;
import com.sh.fastmanagementdemoapi.enums.Status;
import com.sh.fastmanagementdemoapi.models.JobApplication;
import com.sh.fastmanagementdemoapi.repositories.JobApplicationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {

    JobApplicationRespository repository = new JobApplicationRespository();

    public int startApplication(String nameInput) throws Exception {
        final String name = nameInput.trim();
        if (name.isEmpty()) throw new InvalidBodyException("Nome inválido");

        final Optional<JobApplication> verifyName = repository.findByName(name);
        if (!verifyName.isEmpty()) throw new ConflictException("Candidato já participa do processo");

        final int id = repository.save(name);
        return id;
    }

    public void scheduleInterview(int id) throws Exception {
        final Optional<JobApplication> verifyCandidate = repository.findById(id);
        if (verifyCandidate.isEmpty()) throw new NotFoundException("Candidato não encontrado");

        final JobApplication candidate = verifyCandidate.get();
        if (candidate.getStatus() != Status.RECEBIDO) throw new NotFoundException("Candidato não encontrado");

        repository.updateApplication(id, Status.QUALIFICADO);
    }

    public void endApplication(int id) throws Exception {
        final Optional<JobApplication> verifyCandidate = repository.findById(id);
        if (verifyCandidate.isEmpty()) throw new NotFoundException("Candidato não encontrado");

        repository.delete(id);
    }

    public String checkApplicationStatus(int id) throws Exception {
        final Optional<JobApplication> verifyCandidate = repository.findById(id);
        if (verifyCandidate.isEmpty()) throw new NotFoundException("Candidato não encontrado");

        return verifyCandidate.get().getStatus().name();
    }

    public void approveCandidate(int id) throws Exception {
        final Optional<JobApplication> verifyCandidate = repository.findById(id);
        if (verifyCandidate.isEmpty()) throw new NotFoundException("Candidato não encontrado");

        final JobApplication candidate = verifyCandidate.get();
        if (candidate.getStatus() != Status.QUALIFICADO) throw new NotFoundException("Candidato não encontrado");

        repository.updateApplication(id, Status.APROVADO);
    }

    public List<JobApplication> sendAll() {
        return repository.findAll();
    }

    public List<String> sendApproved() {
        return repository.findApproved();
    }
}
