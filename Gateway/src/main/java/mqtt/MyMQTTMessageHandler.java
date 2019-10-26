package mqtt;

import converterTrampolineToLedStripe.Converter;
import de.ude.es.gatewaymessagequeue.utilities.StringUtils;
import de.ude.es.mqttmessagequeue.handling.AbstractMqttMessageHandler;
import de.ude.es.mqttmessagequeue.message.TopicedMessage;
import digitalWorld.LED;
import digitalWorld.LEDStripe;
import model.Model;
import model.Storage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyMQTTMessageHandler extends AbstractMqttMessageHandler {
    private List<Integer> bufferList = new ArrayList<>();
    private int bufferCnt = 0;
    public MyMQTTMessageHandler(){
        super();
    }

    public void handleMQTTMessage(TopicedMessage messageToHandle){


        if (messageToHandle.getPayload().length == 6) {
//            System.out.println("I received a message with the topic: " + messageToHandle.getTopic());
//            System.out.println("RAW PAYLOAD: " + StringUtils.toHexString(messageToHandle.getPayload()));
            System.out.println("YTRAMPOLINE: " + ByteBuffer.wrap(messageToHandle.getPayload()).order(ByteOrder.LITTLE_ENDIAN).getShort(4));
            int trampolineY = ByteBuffer.wrap(messageToHandle.getPayload()).order(ByteOrder.LITTLE_ENDIAN).getShort(4);
            trampolineY = Math.abs(trampolineY);        //negative Werte auch reinnehmen

            bufferList.add(bufferCnt++, trampolineY);
//            for (Integer integer : bufferList) {
//                System.out.print(integer +", ");
//            }
//            System.out.println("----------------------------------------");

            if (bufferCnt > 1) {
                int maxVal = Collections.max(bufferList);

                new Converter(maxVal);

//                List<LED> ledStripe = Storage.getLedStripe().getChangedLEDs();
//                for (LED led : ledStripe) {
//                    System.out.println(led);
//                }

                try {
                    Model model = Model.getInstance();
                    model.sendHandler();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                bufferList.clear();
                bufferCnt = 0;
            }
        } else {
//            System.out.println("Schon wieder was falsches gesendet:");
//            System.out.println("RAW PAYLOAD: " + StringUtils.toHexString(messageToHandle.getPayload()));
        }

    }

}
