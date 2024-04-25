package com.backend.jwt.response;

/**
 * Represents a response for a JWT validation request.
 */

public class ValidationResponse {

    private boolean isValid;
    private String message;

    public ValidationResponse(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}








