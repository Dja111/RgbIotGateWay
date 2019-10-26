package model;

import digitalWorld.LEDStripe;

public class Storage {
    public static final int AMOUNT_OF_INDEX_BYTES_OF_A_LED = 1;
    public static final int ALLOWED_PAYLOAD_SIZE_IN_BYTES = 116;
    public static final int MAX_LEDS_IN_STRIPE = 240;

    
    private static LEDStripe ledStripe;

    public static LEDStripe getLedStripe() {
        return ledStripe;
    }
    public static void setLedStripe(LEDStripe ledList) {
        ledStripe = ledList;
    }
}
