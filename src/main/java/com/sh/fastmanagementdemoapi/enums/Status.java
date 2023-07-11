package com.sh.fastmanagementdemoapi.enums;

public enum Status {
    RECEBIDO("Recebido"),
    QUALIFICADO("Qualificado"),
    APROVADO("Aprovado");

    final String name;

    Status (String name) {
        this.name = name;
    }
}
