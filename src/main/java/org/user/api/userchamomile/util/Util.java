package org.user.api.userchamomile.util;

import org.springframework.validation.BindingResult;
import org.user.api.userchamomile.dto.GenericResponseDto;
import org.user.api.userchamomile.error.FieldError;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static GenericResponseDto<?> validation(BindingResult result) {
        List<FieldError> fieldErrors = new ArrayList<>();
        result.getFieldErrors().forEach(err -> {
            FieldError fieldError = new FieldError(err.getField(),  "El campo " + err.getField() + " " + err.getDefaultMessage());
            fieldErrors.add(fieldError);
        } );
        return new GenericResponseDto<>(ResponseCode.LCO000, ResponseCode.LCO000.getHtmlMessage(), fieldErrors);
    }
}
