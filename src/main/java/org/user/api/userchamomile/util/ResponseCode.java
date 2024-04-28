package org.user.api.userchamomile.util;

import lombok.Getter;

@Getter
public enum ResponseCode {

    LCO000(500, "Failed operation."),
    LCO001(201, "Successfully register."),
    LCO002(400, "Not found address."),
    LCO003(400, "Not found user."),
    LCO004(201, "Successfully edited address."),
    LCO005(201, "Successfully deleted address."),
    LCO006(200, "Successfully get data"),
    LCO007(401, "Unauthorized."),
    LCO008(400, "Not found id");

    private final int status;
    private final String htmlMessage;

    ResponseCode(int status, String htmlMessage) {
        this.status = status;
        this.htmlMessage = htmlMessage;
    }

}
