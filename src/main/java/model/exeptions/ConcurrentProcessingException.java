package model.exeptions;

public class ConcurrentProcessingException extends RuntimeException {


    public ConcurrentProcessingException(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage(){
        return message;
    }
}
