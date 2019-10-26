package remoteResourceFramework.exceptions;

/* If the label supplied in the rest path does not match with the label in the Resource object */
public class PrimaryKeyMismatchException extends Exception {
    public PrimaryKeyMismatchException(String message) {
        super(message);
    }
}