package com.stokessoftwaresolutions.checkbookapi.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException {

        public PermissionDeniedException(String message) {
            super(message);
        }
}
