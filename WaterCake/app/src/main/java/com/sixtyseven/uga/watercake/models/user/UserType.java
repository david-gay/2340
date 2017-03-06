package com.sixtyseven.uga.watercake.models.user;

/**
 * Enum for each possible User type
 */
public enum UserType {
    CONTRIBUTOR("Contributor"),
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
