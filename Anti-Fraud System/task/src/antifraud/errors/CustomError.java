package antifraud.errors;

public class CustomError {
    String message;

    public CustomError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
