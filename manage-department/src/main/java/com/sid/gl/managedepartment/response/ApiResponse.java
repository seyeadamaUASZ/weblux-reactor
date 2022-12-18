package com.sid.gl.managedepartment.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private int status = 200;
    @JsonIgnore
    private int httpStatus = 200;
    private String message;
    private Object data;

    public ApiResponse(int status) {
        this.status = status;
        if (status >= 1002 && status <= 1006) {
            setMessage("‘Unauthorized’");
            setData(null);
        }
    }


    public ApiResponse(DepartmentMessage departmentMessage) {
        this.httpStatus = departmentMessage.getHttpStatus();
        this.status = departmentMessage.getHttpStatus();
        this.message = departmentMessage.getMessage();
        if (departmentMessage.getData() != null) this.data = departmentMessage.getData();

    }

    public ApiResponse(DepartmentMessage departmentMessage, String message) {
        this.httpStatus = departmentMessage.getHttpStatus();
        this.status = departmentMessage.getHttpStatus();
        this.message = String.format(departmentMessage.getMessage(), message);
    }

    public ApiResponse(DepartmentMessage departmentMessage, Object... data) {
        this.httpStatus = departmentMessage.getHttpStatus();
        this.status = departmentMessage.getHttpStatus();
        this.message = departmentMessage.getMessage();
        if (data.length > 0) {
            this.data = data;
        }
    }
}
