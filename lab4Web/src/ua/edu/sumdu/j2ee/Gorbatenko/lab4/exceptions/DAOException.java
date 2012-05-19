package ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DAOException() {
        super();
    }
    
    public DAOException(String message) {
        super(message);
    }
    
    public DAOException(String message, Throwable arg) {
        super(message, arg);
    }
}
