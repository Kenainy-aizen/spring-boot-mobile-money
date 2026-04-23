package mg.edu.eni.mobilemoney.exceptions;

public class FraisEmptyException extends RuntimeException{
    public FraisEmptyException(String message){
        super(message);
    }
}
