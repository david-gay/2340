package com.sixtyseven.uga.watercake.models.user;


public enum UserType {
    REGISTERED("Registered"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMINISTRATOR("Administrator");

    private String stringRepresentation;

    UserType(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
