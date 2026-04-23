package mg.edu.eni.mobilemoney.exceptions;

public class EnvoiNotFoundException extends RuntimeException {
    public EnvoiNotFoundException(String message) {
        super(message);
    }
}
