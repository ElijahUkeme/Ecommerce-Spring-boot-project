package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error;

public class DataAlreadyExistException extends Exception {
    public DataAlreadyExistException() {
        super();
    }

    public DataAlreadyExistException(String message) {
        super(message);
    }

    public DataAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAlreadyExistException(Throwable cause) {
        super(cause);
    }

    protected DataAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
