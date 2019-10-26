package remoteResourceFramework.model;

import java.util.Arrays;


public enum ExpectAck {
    FALSE(0),
    TRUE(1);

    private int value;

    ExpectAck(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ExpectAck getExpectedAck(byte value) {
        int intValue = value & 0xFF;
        return Arrays
                .stream(ExpectAck.values())
                .filter(expectAck -> expectAck.value == intValue)
                .findFirst()
                .orElse(null);
    }
}