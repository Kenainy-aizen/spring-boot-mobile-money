package mg.edu.eni.mobilemoney.exceptions;

public class LowBalanceException extends RuntimeException{
    public LowBalanceException(String message){
        super(message);
    }
}
