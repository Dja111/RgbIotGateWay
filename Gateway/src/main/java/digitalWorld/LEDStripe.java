package digitalWorld;

import java.util.ArrayList;
import java.util.List;

public class LEDStripe extends ArrayList<LED> {

    public LEDStripe(int numberOfLEDs) {
        for (int i = 1; i <= numberOfLEDs; i++) {
            add(new LED(i));
        }
    }

    public void blackenLEDs() {
        for (LED led : this) {
            if (led.getRed() != 0 ||
                    led.getGreen() != 0 ||
                    led.getBlue() != 0) {
                led.setRGB(0,0,0);
            }
        }
    }

    public List<LED> getChangedLEDs() {
        List<LED> changedLEDs = new ArrayList<>();
        for (LED led : this) {
            if (led.isChanged()) {
                changedLEDs.add(led);
            }
        }
        return changedLEDs;
    }

    public void setLEDsToUnchanged() {
        for (LED led : this) {
            led.setUnchanged();
        }
    }

    /**
     * Da wir LEDs ab 1 zählen gibt es keine nullte LED.
     * Außerdem würde man mit .get(123) die LED mit der Nummer 122 bekommen.
     * Deswegen suchen wir die LEDs einzeln nach dem index ab.
     * @param index der Index der gesuchten LED (von 1 bis "im Konstruktor eingegebene 'quantitiy'")
     * @return die LED mit dem übertragenem Index
     */
    @Override
    public LED get(int index) {
        for (LED led : this) {
            if (led.getIndex() == index) {
                return led;
            }
        }
        throw new RuntimeException("LED "+index+" gibt es nicht");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LED led : this) {
            sb.append(led.toString());
            if (led.getIndex() != this.size()) {
                sb.append(String.format("%n"));
            }
        }
        return sb.toString();
    }
}
