package com.sh.fastmanagementdemoapi.repositories;

import com.sh.fastmanagementdemoapi.enums.Status;
import com.sh.fastmanagementdemoapi.models.JobApplication;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JobApplicationRespository {
    private List<JobApplication> applications = new ArrayList<JobApplication>();
    private int lastId = 0;

    private int createId() {
        this.lastId++;
        return this.lastId;
    }

    public int save (String name) {
       final int id = createId();
       JobApplication jobApplication = new JobApplication(id, name, Status.RECEBIDO);
       this.applications.add(jobApplication);
       return id;
    }

    public Optional<JobApplication> findByName (String name) {
        for (int i = 0; i < this.applications.size(); i++) {
            final JobApplication candidate = this.applications.get(i);
            if (candidate.getName().equals(name)) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }

    public Optional<JobApplication> findById (int id) {
        if (id <= 0) return Optional.empty();
        for (int i = 0; i < this.applications.size(); i++) {
            final JobApplication candidate = this.applications.get(i);
            if (candidate.getId() == id ) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }

    public List<String> findApproved () {
        final ArrayList<String> approved = new ArrayList<>();
        for (int i = 0; i < this.applications.size(); i++) {
            final JobApplication candidate = this.applications.get(i);
            if (candidate.getStatus() == Status.APROVADO) {
                approved.add(candidate.getName());
            }
        }
        return approved;
    }

    public List<JobApplication> findAll() {
        return applications;
    }

    public void updateApplication (int id, Status status) {
        Optional<JobApplication> application = findById(id);
        application.get().setStatus(status);
    }

    public void delete (int id) {
        Optional<JobApplication> application = findById(id);
        applications.remove(application.get());
    }
}
