package com.jeterson.areaclientewinthor.common.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {
    private String code;
    private String message;
    private List<ErrorDTO> details;
    private ErrorDTO internalError;

    public ErrorDTO(String code, String message){
        this.code = code;
        this.message = message;
    }
    public void addError(String code, String message) {
        if (details == null)
            details = new ArrayList<>();

        details.add(new ErrorDTO(code, message, null,null));
    }

}
