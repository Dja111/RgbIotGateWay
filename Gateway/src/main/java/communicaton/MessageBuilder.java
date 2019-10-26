package communicaton;

import digitalWorld.LED;
import main.Main;
import remoteResourceFramework.model.ExpectAck;
import remoteResourceFramework.model.MessageType;
import remoteResourceFramework.model.RRFMessage;

import java.nio.ByteBuffer;
import java.util.List;

import static model.Storage.ALLOWED_PAYLOAD_SIZE_IN_BYTES;
import static model.Storage.AMOUNT_OF_INDEX_BYTES_OF_A_LED;

public class MessageBuilder {
    private static int sizeOfOneLedInBytes;

    /**
     * Before we do anything, we need to know, how many Bytes one LED needs:
     * 1 for Red
     * 1 for Green
     * 1 for Blue
     * plus the amount of index bytes we declared in the Storage
     */
    static {
        sizeOfOneLedInBytes = 3 + AMOUNT_OF_INDEX_BYTES_OF_A_LED;
    }


    /**
     * Creates a Message-Array with smallest possible length.
     *
     * @param byteAddress the Receiver of the Messages, as byte Array
     * @param leds        the (changed) LEDs, which we want to send, as List
     * @return a Message Array, with smallest possible length
     */
    public static RRFMessage[] buildMessages(CommunicationAddress communicationAddress, List<LED> leds) {
        int maxLedsInOneMessage = ALLOWED_PAYLOAD_SIZE_IN_BYTES / sizeOfOneLedInBytes;
        int amountOfMessages = (int) Math.ceil((float) leds.size() / maxLedsInOneMessage);

        RRFMessage[] messages = new RRFMessage[amountOfMessages];
        for (int messageNr = 0; messageNr < messages.length; messageNr++) {
            List<LED> sendableList = createAListOfTheLedsWePutInThisMessage(leds, messageNr, maxLedsInOneMessage, amountOfMessages);
            Main.LOGGER.info("SIZEE:" + sendableList.size());
            messages[messageNr] = new RRFMessage(MessageType.DATA, messageNr, ExpectAck.FALSE, "LED", payloadBuffer(sendableList), communicationAddress);
        }
        return messages;
    }

    /**
     * this method returns a byte array which contains each LED´s Index, Red, Green, Blue values.
     * For example:  3 LEDs were changed to blue. => create a byte[] array which is: IRGBIRGBIRGB.
     * The size is: 3*sizeof(sendableList)
     * Attention: this method is an important requirement for building a message. see @buildMessage method.
     *
     * @param sendableList The list, which is ready to send.
     * @return a resizeable byte array which contains all LED´s I R G B.
     */
    public static byte[] payloadBuffer(List<LED> sendableList) {

        ByteBuffer bufferTmp = ByteBuffer.allocate(sendableList.size() * sizeOfOneLedInBytes);
        for (int i = 0; i < sendableList.size(); i++) {
            bufferTmp.put((byte) sendableList.get(i).getIndex());
            bufferTmp.put((byte) sendableList.get(i).getRed());
            bufferTmp.put((byte) sendableList.get(i).getGreen());
            bufferTmp.put((byte) sendableList.get(i).getBlue());
        }
        return bufferTmp.array();
    }

    /**
     * creates a List of Leds, which is only as big as we can put into one message
     * The IndexOutOfBoundsException means, that there aren't enough leds to fill the message
     *
     * @param leds                the list of all leds we want to send
     * @param messageNr           the number of the message, which we want to build
     * @param maxLedsInOneMessage amount of leds we can put into one message
     * @return a list of leds, which is excatly as big as one message
     */
    private static List<LED> createAListOfTheLedsWePutInThisMessage(List<LED> leds, int messageNr, int maxLedsInOneMessage, int amountOfMessages) {
        if (messageNr == amountOfMessages - 1 && (leds.size() % maxLedsInOneMessage != 0)) {
            return leds.subList(maxLedsInOneMessage * messageNr, maxLedsInOneMessage * messageNr + (leds.size() % maxLedsInOneMessage));
        }
        return leds.subList(maxLedsInOneMessage * messageNr, (maxLedsInOneMessage * (messageNr + 1)));


    }
}

