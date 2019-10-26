package digitalWorld;

import static model.Storage.AMOUNT_OF_INDEX_BYTES_OF_A_LED;

public class LED {
    private int index;
    private boolean changed;
    private int red,green,blue;

    public LED(int index) {
        if (index > Math.pow(256,AMOUNT_OF_INDEX_BYTES_OF_A_LED)-1) {
            throw new RuntimeException("A LED with a bigger index then "+(int)(Math.pow(256,AMOUNT_OF_INDEX_BYTES_OF_A_LED)-1)+" cannot be created!");
        }
        this.index = index;
        changed = false;
        red = 0;
        green = 0;
        blue = 0;
    }

    public boolean isChanged() {
        return changed;
    }
    public void setUnchanged() {
        this.changed = false;
    }

    public void setRGB(int red, int green, int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public float getRedAsFloat() {
        return (float)red/255;
    }
    public int getRed() {
        return red;
    }
    public void setRed(int red) {
        if (red > 255) {
            throw new RuntimeException("Rot kann nicht mehr als 255 sein!");
        }
        this.red = red;
        changed = true;
    }
    
    public float getGreenAsFloat() {
        return (float)green/255;
    }
    public int getGreen() {
        return green;
    }
    public void setGreen(int green) {
        changed = true;
        if (green > 255) {
            this.green = 255;
            throw new RuntimeException("Gruen kann nicht mehr als 255 sein!");
        }
        this.green = green;
        changed = true;
    }
    
    public float getBlueAsFloat() {
        return (float)blue/255;
    }
    public int getBlue() {
        return blue;
    }
    public void setBlue(int blue) {
        changed = true;
        if (blue > 255) {
            this.blue = 255;
            throw new RuntimeException("Blau kann nicht mehr als 255 sein!");
        }
        this.blue = blue;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        String formattedIndex = String.format("%3d", index);
        String formattedRed = String.format("%3d", red);
        String formattedGreen = String.format("%3d", green);
        String formattedBlue = String.format("%3d", blue);
        String formattedChanged = (changed?"  changed":"unchanged");
        return "LED #"+formattedIndex+" "+formattedRed+" "+formattedGreen+" "+formattedBlue+" "+formattedChanged;
    }
}
