package remoteResourceFramework.model;

import java.util.Arrays;


public enum MessageType {
    GET(0),
    POST(1),
    DATA(2),
    ACK(128),
    NACK(129);

    private int value;

    MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MessageType getMessageType(byte value) {
        int intValue = value & 0xFF;
        return Arrays
                .stream(MessageType.values())
                .filter(messageType -> messageType.value == intValue)
                .findFirst()
                .orElse(null);
    }
}