package remoteResourceFramework.exceptions;

/* If the address supplied in the rest path does not match with the address in the Node object */
public class NodeAddressMismatchException extends Exception {
    public NodeAddressMismatchException(String message) {
        super(message);
    }
}