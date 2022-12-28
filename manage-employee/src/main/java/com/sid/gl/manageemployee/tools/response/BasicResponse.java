package com.sid.gl.manageemployee.tools.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class BasicResponse {
    private int status = 200;
    @JsonIgnore
    private int httpStatus = 200;
    private String message;
    private Object data;

    public BasicResponse() {
    }



    public BasicResponse(int status) {
        this.status = status;
        if (status >= 1002 && status <= 1006) {
            setMessage("‘Unauthorized’");
            setData(null);
        }
    }


    public BasicResponse(String message) {
        this.message = message;
    }

    public BasicResponse(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public BasicResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BasicResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
