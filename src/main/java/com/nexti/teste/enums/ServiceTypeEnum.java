package com.nexti.teste.enums;

public enum ServiceTypeEnum {
    SERVICE_A(1L),
    SERVICE_B(2L ),
    SERVICE_C(3L );

    private final Long id;
    ServiceTypeEnum(Long id ) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
