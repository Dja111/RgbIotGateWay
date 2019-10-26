package remoteResourceFramework.messageHandlers;

import remoteResourceFramework.model.RRFMessage;
import de.ude.es.gatewaymessagequeue.message.Message;
import remoteResourceFramework.MessageHandler;
import remoteResourceFramework.marshaller.MessageMarshaller;
import remoteResourceFramework.service.TransactionService;
import de.ude.es.gatewaymessagequeue.utilities.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class MessageReceiverWorker implements Runnable {
    private Message message;
    private TransactionService transactionService;
    private MessageHandler messageHandler;

    private Logger logger = LoggerFactory.getLogger(MessageReceiverWorker.class);


    public MessageReceiverWorker(Message message, TransactionService transactionService, MessageHandler messageHandler) {
        this.message = Objects.requireNonNull(message);
        this.transactionService = Objects.requireNonNull(transactionService);
        this.messageHandler = Objects.requireNonNull(messageHandler);
    }

    @Override
    public void run() {
        RRFMessage rrfMessage = MessageMarshaller.unmarshal(message.getPayload());
        assert rrfMessage != null;
        logger.info("RSA-Message received from {} >> {}", StringUtils.toHexString(message.getAddress().getAddressValue()), rrfMessage.toString());
        messageHandler.handleMessage(rrfMessage);
        transactionService.freeTransactionID(rrfMessage.getTransactionID());
    }
}