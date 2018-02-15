package com.kaizensoftware.students.dao.common;

/**
 * Created by Arturo Cordero
 */
public enum ResponseDescription {

    REQUIRED_DATA           (19, "Este campo es requerido"),
    INVALID_EMAIL           (20, "El e-mail es inválido"),
    EMPTY_FIELDS            (21, "Campos incompletos, favor llenar los campos faltantes"),
    NO_DATA_FOUND           (22, "No se han encontrado datos"),
    PASSWORDS_DONT_MATCH    (23, "Las contraseñas no coinciden"),
    INVALID_PASSWORD        (24, "La contraseña no es válida"),

    SQL_ERROR               (25, "Error al realizar operaciones en SQL"),
    INSERT_ERROR            (26, "Error al realizar la inserción de datos"),
    SELECT_ERROR            (27, "Error al obtener datos"),
    DELETE_ERROR            (28, "Error al eliminar datos"),
    OK                      (10, "Datos encontrados");

    private final int error;
    private final String description;

    ResponseDescription(int error, String description) {
        this.error = error;
        this.description = description;
    }

    public static ResponseDescription findResponseDescription(int error) {

        for (ResponseDescription responseDescription : values())
            if (responseDescription.error() == error)
                return responseDescription;

        return null;
    }

    public int error () {
        return error;
    }

    public String description() {
        return description;
    }

    @Override
    public String toString() {
        return  "ResponseDescription{" +
                    "error=" + error + ", " +
                    "description=" + description +
                "}";
    }
}
