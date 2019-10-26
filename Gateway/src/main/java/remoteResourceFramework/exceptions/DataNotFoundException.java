package remoteResourceFramework.exceptions;

/* Generic exception type for: Node/Resource does not exist in database */
public class DataNotFoundException extends Exception {
    public DataNotFoundException(String message) {
        super(message);
    }
}