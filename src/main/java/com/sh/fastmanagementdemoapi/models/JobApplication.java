package com.sh.fastmanagementdemoapi.models;

import com.sh.fastmanagementdemoapi.enums.Status;

public class JobApplication {
    public JobApplication(int id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    private int id;
    private String name;
    private Status status;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
