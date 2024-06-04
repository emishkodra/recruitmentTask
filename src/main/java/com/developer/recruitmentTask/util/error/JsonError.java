package com.developer.recruitmentTask.util.error;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JsonError implements Serializable {

    private String responseKey;
    private String responseMessage;

    public JsonError(String responseMessage, String responseKey) {
        this.responseMessage = responseMessage;
        this.responseKey = responseKey;
    }
}
