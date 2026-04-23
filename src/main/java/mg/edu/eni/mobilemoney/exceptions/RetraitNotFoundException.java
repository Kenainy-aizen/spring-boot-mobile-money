package mg.edu.eni.mobilemoney.exceptions;

public class RetraitNotFoundException extends RuntimeException{
    public RetraitNotFoundException(String message){
        super(message);
    }
}
