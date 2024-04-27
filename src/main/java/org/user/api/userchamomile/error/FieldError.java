package org.user.api.userchamomile.error;

import java.io.Serial;
import java.io.Serializable;

public record FieldError(String field, String error) implements Serializable {
    @Serial
    private static final long serialVersionUID = 3708567824775716466L;
}
