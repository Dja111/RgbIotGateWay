package remoteResourceFramework;

import de.ude.es.gatewaymessagequeue.addresses.CommunicationAddress;
import de.ude.es.gatewaymessagequeue.addresses.XbeeAddress64Bit;
import de.ude.es.gatewaymessagequeue.gateway.IoTDeviceGateway;
import remoteResourceFramework.messageHandlers.MessageReceiver;
import remoteResourceFramework.messageHandlers.MessageSender;
import remoteResourceFramework.model.ExpectAck;
import remoteResourceFramework.model.MessageType;
import remoteResourceFramework.model.RRFMessage;
import remoteResourceFramework.service.MessageQueueService;
import remoteResourceFramework.service.TransactionService;


@Service
@NoArgsConstructor
public class RemoteResourceFramework {

    private MessageSender messageSender;
    private TransactionService transactionService;
    private MessageQueueService messageQueueService;

    public RemoteResourceFramework(MessageHandler messageHandler,IoTDeviceGateway ioTDeviceGateway) {
        this.transactionService = new TransactionService();
        this.messageQueueService = new MessageQueueService();
        MessageReceiver messageReceiver = new MessageReceiver(transactionService, messageHandler);
        this.messageSender = new MessageSender(ioTDeviceGateway);
    }

    public Integer sendMessage(MessageType messageType, String resourceUri, CommunicationAddress address, ExpectAck expectACK, byte[] payload) throws Exception {
        Integer transactionId = transactionService.getNewTransactionID();
        RRFMessage rrfMessage = new RRFMessage(messageType, transactionId, expectACK, resourceUri, payload, address);
        messageSender.sendMessage(rrfMessage);
        return transactionId;
    }

    public Integer queueMessage(MessageType messageType, String resourceUri, XbeeAddress64Bit address, ExpectAck expectACK, byte[] payload) throws Exception {
        Integer transactionId = transactionService.getNewTransactionID();
        RRFMessage rrfMessage = new RRFMessage(messageType, transactionId, expectACK, resourceUri, payload, address);
        messageQueueService.put(transactionId, rrfMessage);
        return transactionId;
    }

    public void sendQueuedMessage(int transactionId) {
        RRFMessage rrfMessage = messageQueueService.getOrNull(transactionId);
        if (rrfMessage != null) {
            messageQueueService.remove(transactionId);
            messageSender.sendMessage(rrfMessage);
        }
    }

    public void dequeueMessage(int transactionId) {
        messageQueueService.remove(transactionId);
        transactionService.freeTransactionID(transactionId);
    }
}