package gateway;

import de.ude.es.gatewaymessagequeue.addresses.CommunicationAddress;
import de.ude.es.gatewaymessagequeue.handling.AbstractMessageHandler;
import de.ude.es.gatewaymessagequeue.message.Message;
import de.ude.es.gatewaymessagequeue.utilities.StringUtils;

import java.util.Arrays;

public class ExampleMessageHandler extends AbstractMessageHandler {
    public ExampleMessageHandler() {
        super();
    }

    public void handleMessage(Message message) {
        CommunicationAddress address = message.getAddress();
//        System.out.println("Payload: " + Arrays.toString(message.getPayload()));
//        System.out.println("Payload formatted: " + StringUtils.toHexString(message.getPayload()));
//        System.out.println("Address: " + StringUtils.toHexString(message.getAddress().getAddressValue()));
        byte[] payload = message.getPayload();
//        Message reply = new Message64BitAddress(address, Fragmentation.frag(5, 255, 0, 255));
//        sendMessage(reply);
//        System.out.println("Message sent");
    }
}
