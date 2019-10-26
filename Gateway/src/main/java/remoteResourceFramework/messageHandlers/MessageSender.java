package remoteResourceFramework.messageHandlers;

import remoteResourceFramework.model.RRFMessage;
import de.ude.es.gatewaymessagequeue.gateway.IoTDeviceGateway;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MessageSender {
    private ExecutorService sendMessageTaskExecutorService;
    private IoTDeviceGateway ioTDeviceGateway;

    public MessageSender(IoTDeviceGateway ioTDeviceGateway) {
        this.sendMessageTaskExecutorService = Executors.newCachedThreadPool();
        this.ioTDeviceGateway = Objects.requireNonNull(ioTDeviceGateway);
    }

    public void sendMessage(RRFMessage rrfMessage) {
        MessageSenderWorker messageSenderWorker = new MessageSenderWorker(ioTDeviceGateway, rrfMessage);
        sendMessageTaskExecutorService.execute(messageSenderWorker);
    }
}