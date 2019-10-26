package remoteResourceFramework.messageHandlers;

import de.ude.es.gatewaymessagequeue.handling.AbstractMessageHandler;
import de.ude.es.gatewaymessagequeue.message.Message;
import remoteResourceFramework.MessageHandler;
import remoteResourceFramework.service.TransactionService;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageReceiver extends AbstractMessageHandler {
    private ExecutorService receiveTaskPoolExecutorService;
    private TransactionService transactionService;
    private MessageHandler messageHandler;

    public MessageReceiver(TransactionService transactionService, MessageHandler messageHandler) {
        this.receiveTaskPoolExecutorService = Executors.newCachedThreadPool();
        this.transactionService = Objects.requireNonNull(transactionService);
        this.messageHandler = Objects.requireNonNull(messageHandler);
    }

    @Override
    public void handleMessage(Message message) {
        Objects.requireNonNull(message);
        MessageReceiverWorker messageReceiverWorker = new MessageReceiverWorker(message, transactionService, messageHandler);
        receiveTaskPoolExecutorService.execute(messageReceiverWorker);
    }
}