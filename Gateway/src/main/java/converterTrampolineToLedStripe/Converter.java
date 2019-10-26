package converterTrampolineToLedStripe;

import digitalWorld.LED;
import digitalWorld.LEDStripe;
import model.Storage;

public class Converter {
    private double scaleFactor = 3;
    private double maxValueTrampoline = 4820 * scaleFactor;
    private int minGreenLeds=1;
    private int maxGreenLeds;
    private int minYellowLeds;
    private int maxYellowLeds;
    private int minRedLeds;
    private int maxRedLeds;
    private LEDStripe ledStripe;

    public Converter(int trampolineY) {
        if (trampolineY > maxValueTrampoline) {
            trampolineY = (int)maxValueTrampoline;
        }


//        System.out.println(trampolineY + " soll jetzt gesendet werden...");
        ledStripe = Storage.getLedStripe();
        ledStripe.blackenLEDs();
        maxGreenLeds = (int) ((double) ledStripe.size()     * 1 / 3);
        minYellowLeds = (int) ((double) ledStripe.size()    * 1 / 3);
        maxYellowLeds = (int) ((double) ledStripe.size()    * 2 / 3);
        minRedLeds = (int) ((double) ledStripe.size()       * 2 / 3);
        maxRedLeds = (int) ((double) ledStripe.size()       * 3 / 3);
        convertToLeds(trampolineY);
    }

    public  void convertToLeds(int trampolineY) {
        double scaleToMaxColourValue = trampolineY / maxValueTrampoline;
        int amountOfLedsToTurnOn = (int) (ledStripe.size() * scaleToMaxColourValue);
//        System.out.println(amountOfLedsToTurnOn +" müssen dafür angemacht werden...");

        if (amountOfLedsToTurnOn >= minGreenLeds && amountOfLedsToTurnOn <= maxGreenLeds) {
            for (int i = minGreenLeds; i <= amountOfLedsToTurnOn; i++) {
                ledStripe.get(i).setRGB(0,255,0);
            }
        } else if (amountOfLedsToTurnOn > minYellowLeds && amountOfLedsToTurnOn <= maxYellowLeds) {
            for (int i = minGreenLeds; i <= maxGreenLeds; i++) {
                ledStripe.get(i).setRGB(0,255,0);
            }
            amountOfLedsToTurnOn -= (maxGreenLeds - minGreenLeds);
            for (int i = minYellowLeds; i < minYellowLeds+amountOfLedsToTurnOn; i++) {
                ledStripe.get(i).setRGB(255,255,0);
            }
        } else if (amountOfLedsToTurnOn > minRedLeds && amountOfLedsToTurnOn <= maxRedLeds) {
            for (int i = minGreenLeds; i <= maxGreenLeds; i++) {
                ledStripe.get(i).setRGB(0,255,0);
            }
            amountOfLedsToTurnOn -= (maxGreenLeds - minGreenLeds);
            for (int i = minYellowLeds; i <= maxYellowLeds; i++) {
                ledStripe.get(i).setRGB(255,255,0);
            }
            amountOfLedsToTurnOn -= (maxYellowLeds - minYellowLeds);
            for (int i = minRedLeds; i < minRedLeds+amountOfLedsToTurnOn; i++) {
                ledStripe.get(i).setRGB(255,0,0);
            }
        }
    }
}
