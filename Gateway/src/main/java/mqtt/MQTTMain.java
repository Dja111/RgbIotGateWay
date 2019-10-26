package mqtt;

import de.ude.es.mqttmessagequeue.gateway.MqttGateway;
import de.ude.es.mqttmessagequeue.gateway.MqttGatewayImpl;
import de.ude.es.mqttmessagequeue.message.TopicedMessage;

public class MQTTMain {
    public static void mqttmain(){
        MyMQTTMessageHandler myMessageHandler = new MyMQTTMessageHandler();
        MqttGateway myMqttGateway =  new MqttGatewayImpl("localhost", "LEDGateway", myMessageHandler);
        myMqttGateway.startMqtt();
//        myMessageHandler.handleMQTTMessage(new TopicedMessage() {
//            @Override
//            public String getTopic() {
//                return "topicPublishedToServer";
//            }
//
//            @Override
//            public byte[] getPayload() {
//                byte[] b = {2,2,34};
//                return b;
//            }
//
//            @Override
//            public MqttMessage getEncapsulatedMessage() {
//                return null;
//            }
//        });

    }
}
