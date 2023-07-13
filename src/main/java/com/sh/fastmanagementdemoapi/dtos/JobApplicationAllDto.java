package com.sh.fastmanagementdemoapi.dtos;

import com.sh.fastmanagementdemoapi.models.JobApplication;

import java.util.List;

public record JobApplicationAllDto(List<JobApplication> application) {
}
