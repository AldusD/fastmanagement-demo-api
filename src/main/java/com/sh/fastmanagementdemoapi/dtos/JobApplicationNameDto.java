package com.sh.fastmanagementdemoapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record JobApplicationNameDto(@NotBlank String nome) {
}
