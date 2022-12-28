package com.sid.gl.manageemployee.tools;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class ValidationError {
    private final String field;
    private final String message;
}