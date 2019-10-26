package remoteResourceFramework.messageHandlers;


import de.ude.es.gatewaymessagequeue.gateway.IoTDeviceGateway;
import de.ude.es.gatewaymessagequeue.message.Message;
import de.ude.es.gatewaymessagequeue.message.Message64BitAddress;
import de.ude.es.gatewaymessagequeue.utilities.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import remoteResourceFramework.marshaller.MessageMarshaller;
import remoteResourceFramework.model.RRFMessage;

import java.util.Objects;


public class MessageSenderWorker implements Runnable {

    private IoTDeviceGateway ioTDeviceGateway;
    private RRFMessage rrfMessage;

    private Logger logger = LoggerFactory.getLogger(MessageSenderWorker.class);


    public MessageSenderWorker(IoTDeviceGateway ioTDeviceGateway, RRFMessage rrfMessage) {
        this.ioTDeviceGateway = Objects.requireNonNull(ioTDeviceGateway);
        this.rrfMessage = Objects.requireNonNull(rrfMessage);
    }

    @Override
    public void run() {

        //hier ist wahrscheinlich der Fehler
       // byte[] messageData = MessageMarshaller.marshal(rrfMessage);
        //Message message = new Message64BitAddress(rrfMessage.getAddress(), messageData);
        Message message = new Message64BitAddress(rrfMessage.getAddress(),rrfMessage.getPayload());

        ioTDeviceGateway.sendMessage(message);
        logger.info("RSA-Message sent to {} >> {}", StringUtils.toHexString(message.getAddress().getAddressValue()), rrfMessage.toString());
    }
}