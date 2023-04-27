package com.api.exceptions;

public class resourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String resourceField;
    private long fieldValue;

    public resourceNotFoundException(String resourceName, String resourceField, long fieldValue) {
        super(String.format("%s is not found with %s : %d", resourceName, resourceField, fieldValue));
        this.resourceName = resourceName;
        this.resourceField = resourceField;
        this.fieldValue = fieldValue;
    }

    public resourceNotFoundException(String resourceName, String resourceField) {
        super(String.format("%s is not found with %s", resourceName, resourceField));
        this.resourceName = resourceName;
        this.resourceField = resourceField;
    }
}
