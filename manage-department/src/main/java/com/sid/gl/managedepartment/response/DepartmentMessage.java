package com.sid.gl.managedepartment.response;

public enum DepartmentMessage {

    WS_SUCCESS(200,  " Success: %s"),
    WS_DELETED(200,  "Deleted"),
    WS_ERROR(400,  "Error occurred: %s"),
    SEC_HTTP_UNAUTHORIZED(403,  "Unauthorized."),
    WS_CONSTRAINT_VIOLATION(400, "Not valid due to validation error: "),

    UNAUTHORIZED_FEATURE_FOR_ENV(404, "Feature not authorized for this environment"),

    CA_DUPLICATED_FIELDS(400,  "The %s is already in use."),
    CA_NOT_FOUND(400,  "No result for %s "),

    CA_BAD_ARGUMENTS(400, "%s is not correct- please try again."),
    CA_EQUIPMENT_IN_MAINTENANCE(400,  "%s is already in maintenance."),
    CA_UNIQUE_EMAIL(500,  "%s Email alrady exist.");


    private final int httpStatus;

    private String message;
    private String developerMessage;
    private Object data;

    public static final String CODE_UNIQUE_CONSTRAINT_ERROR = "Value already exists";

    DepartmentMessage(int httpStatus,  String message) {
        this.httpStatus = httpStatus;

        this.message = message;
    }

    DepartmentMessage(int httpStatus, int code, String message, String developerMessage) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    /*public static DepartmentMessage findByCode(int code) {
        for (DepartmentMessage message : values()) {
            if (message.code == code) {
                return message;
            }
        }

        return WS_ERROR;
    }*/

    public int getHttpStatus() {
        return httpStatus;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
