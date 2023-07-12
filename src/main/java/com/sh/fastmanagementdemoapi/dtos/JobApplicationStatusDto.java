package com.sh.fastmanagementdemoapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record JobApplicationStatusDto (@NotBlank String status) {
}
