package com.sid.gl.managedepartment.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sid.gl.managedepartment.response.DepartmentMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class DepartmentException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentException.class);
    protected int code;
    protected String message;
    protected Throwable throwable;
    protected Object[] args;
    protected DepartmentMessage departmentMessage;
    protected Object data;
    private HttpStatus httpStatus;

    public DepartmentException(DepartmentMessage departmentMessage) {
        this.departmentMessage = departmentMessage;
        this.code = departmentMessage.getHttpStatus();
        //this.httpStatus = HttpStatus.valueOf(kycMessage.getHttpStatus());
        this.message = departmentMessage.getMessage();
        String devMsg = departmentMessage.getDeveloperMessage();
        if (devMsg != null && !devMsg.isEmpty())
            this.message += String.format(" [devMsg=%s]", devMsg);
    }

    public DepartmentException(DepartmentMessage departmentMessage, Object... messageParams) {
        this.departmentMessage = departmentMessage;
        this.args = messageParams;
        this.code = departmentMessage.getHttpStatus();
        //this.httpStatus = HttpStatus.valueOf(kycMessage.getHttpStatus());

        if (messageParams == null || messageParams.length == 0) {
            this.message = departmentMessage.getMessage();
            String devMsg = departmentMessage.getDeveloperMessage();
            if (devMsg != null && !devMsg.isEmpty())
                this.message += String.format(" [devMsg=%s]", devMsg);
        } else {
            List<String> translatedParams = new ArrayList<>();
            for (int i = 0; i < messageParams.length; i++) {
                String rev = messageParams[i].toString();
                translatedParams.add(rev);
            }
            this.message = String.format(departmentMessage.getMessage(), messageParams);
            String devMsg = departmentMessage.getDeveloperMessage();
            if (devMsg != null && !devMsg.isEmpty())
                this.message += String.format(" [devMsg=%s]", devMsg);
        }
    }

    public DepartmentException(DepartmentMessage departmentMessage, Throwable throwable) {
        this(departmentMessage);
        this.throwable = throwable;
    }


}
