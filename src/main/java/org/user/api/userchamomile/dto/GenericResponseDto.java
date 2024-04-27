package org.user.api.userchamomile.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.user.api.userchamomile.error.FieldError;
import org.user.api.userchamomile.util.ResponseCode;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class GenericResponseDto<T> {
    private String responseCode;
    private int status;
    private String responseMessage;
    private T data;
    private List<FieldError> fieldErrors;


    public GenericResponseDto(ResponseCode responseCode, String responseMessage, T data) {
        this.responseCode = responseCode.toString();
        this.status = responseCode.getStatus();
        this.responseMessage = responseCode.getHtmlMessage();
        this.data = data;
        this.fieldErrors = new ArrayList<>();
    }

    public GenericResponseDto(ResponseCode responseCode, String responseMessage, List<FieldError> fieldErrors) {
        this.status = responseCode.getStatus();
        this.responseCode = responseCode.toString();
        this.responseMessage = responseMessage;
        this.fieldErrors = fieldErrors;
    }
}
