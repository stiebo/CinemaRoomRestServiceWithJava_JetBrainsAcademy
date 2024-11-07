package cinema.domain;

public enum ErrorMessage {
    OUTOFBOUNDS("The number of a row or a column is out of bounds!"),
    SEATUNAVAILABLE("The ticket has been already purchased!"),
    WRONGTOKEN("Wrong token!"),
    WRONGPASSWORD("The password is wrong!");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
