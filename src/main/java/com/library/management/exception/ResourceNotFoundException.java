package com.library.management.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    String resource;
    Long id;
    public ResourceNotFoundException(String resource, long id) {
        super(String.format("%s with id %s not found", resource, id));
        this.resource = resource;
        this.id = id;
    }
}
