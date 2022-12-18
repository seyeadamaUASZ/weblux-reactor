package com.sid.gl.manageemployee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private int status = 200;
    @JsonIgnore
    private int httpStatus = 200;
    private String message;
    private Object data;
}
