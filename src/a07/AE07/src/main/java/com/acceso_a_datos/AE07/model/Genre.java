package com.acceso_a_datos.AE07.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Genre {
    NOVELA("novela"),
    CIENCIA_FICCION("ciencia ficcion"),
    FANTASIA("fantasia"),
    HISTORIA("historia");

    @JsonValue
    private final String description;
}
