package org.user.api.userchamomile.util;

import lombok.Getter;

@Getter
public enum ResponseCode {

    LCO000(500, "Failed operation."),
    LCO001(201, "Successfully register.");

    private final int status;
    private final String htmlMessage;

    ResponseCode(int status, String htmlMessage) {
        this.status = status;
        this.htmlMessage = htmlMessage;
    }

}
