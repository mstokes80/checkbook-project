package com.stokessoftwaresolutions.checkbookapi.exceptions;

public class PermissionDeniedException extends RuntimeException {

        public PermissionDeniedException(String message) {
            super(message);
        }
}
