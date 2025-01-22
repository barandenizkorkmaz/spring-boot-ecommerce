package com.org.ecommerce.payload.response;

/*
TODO: This class can even be more generic where instead of message, we use a payload which has a type of interface
used for all possible responses and also includes a status code?
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    public String message;
    private boolean status;
}
