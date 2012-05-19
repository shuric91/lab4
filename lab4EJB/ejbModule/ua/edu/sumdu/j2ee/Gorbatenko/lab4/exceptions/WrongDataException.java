package ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions;

public class WrongDataException extends RuntimeException {
    public WrongDataException() {
        super();
    }
    
    public WrongDataException(String message) {
        super(message);
    }
}
