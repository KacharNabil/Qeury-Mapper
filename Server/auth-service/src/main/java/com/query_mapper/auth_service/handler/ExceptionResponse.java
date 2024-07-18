package com.query_mapper.auth_service.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    
    private Integer BusinessErrorCode;
    private String BusinessErrorMessage;
    private String errorMessage;
    private Set<String> ValidationErrors;
    private Map<String,String> errors;

}
