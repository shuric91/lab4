
package ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions;

public class SourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SourceException() {
        super();
    }
    
    public SourceException(String message) {
        super(message);
    }
    
    public SourceException(String message, Throwable arg) {
        super(message, arg);
    }
}
