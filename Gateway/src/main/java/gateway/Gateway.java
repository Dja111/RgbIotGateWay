package gateway;

import de.ude.es.gatewaymessagequeue.gateway.IEEEGateway;
import de.ude.es.gatewaymessagequeue.gateway.IoTDeviceGateway;
import de.ude.es.gatewaymessagequeue.handling.MessageHandler;
import de.ude.es.gatewaymessagequeue.utilities.SerialUtils;
import de.ude.es.mqttmessagequeue.gateway.MqttGateway;
import de.ude.es.mqttmessagequeue.gateway.MqttGatewayImpl;
import digitalWorld.LEDStripe;
import main.Main;
import model.Model;
import model.Storage;
import mqtt.MyMQTTMessageHandler;

public class Gateway {

    private IoTDeviceGateway ieeeGateway;
    private Model model;

    public Gateway(Model m){
        this.model=m;
        createVirtualLeds();
        // TODO: 20.08.2019 why is createVirtualLeds here? it will be started again @ startGateway
    }
    public Gateway() {
        this(null);
        try {
            startGateway();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGateway()throws Exception{
        XBeeInitialization();
        createVirtualLeds();
        new Thread(this::MqttInitialization).start();

    }


    private void MqttInitialization() {
            MyMQTTMessageHandler myMessageHandler = new MyMQTTMessageHandler();
            MqttGateway myMqttGateway =  new MqttGatewayImpl("localhost", "LEDGateway", myMessageHandler);
            myMqttGateway.startMqtt();
            myMqttGateway.subscribeToTopic("RGBIot/Trampoline");
        }

        private void XBeeInitialization() throws Exception{
        MessageHandler handler;
        IEEEGateway ieeeGateway;
        int i = 1;
        do{
            try {
                SerialUtils.setLibPath();
                handler = new ExampleMessageHandler();
                ieeeGateway = new IEEEGateway(/*SerialUtils.getOSSerialPort()*/"COM3", handler, 9600);
                setIeeeGateway(ieeeGateway);
                getIeeeGateway().startGateway();
                Main.LOGGER.info("XBee found and handled!");
                if (model != null) {
                    model.setStatus("XBee connected");
                }
                break;
            } catch (Exception e) {
                System.out.println("XBEE NOT FOUND!");
                System.out.println("*****************");
                Thread.sleep(1000);
            }
            i++;
        }
        while (i<= 5) ;
    }

    private static void createVirtualLeds() {
        Storage.setLedStripe(new LEDStripe(Storage.MAX_LEDS_IN_STRIPE));
//        Main.LOGGER.info("LEDs created.");
    }

    public IoTDeviceGateway getIeeeGateway() {
        return ieeeGateway;
    }

    public void setIeeeGateway(IoTDeviceGateway ieeeGateway) {
        this.ieeeGateway = ieeeGateway;

    }
}