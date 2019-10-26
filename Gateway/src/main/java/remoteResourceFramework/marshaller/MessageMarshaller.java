package remoteResourceFramework.marshaller;

import remoteResourceFramework.model.ExpectAck;
import remoteResourceFramework.model.FrameOffsets;
import remoteResourceFramework.model.MessageType;
import remoteResourceFramework.model.RRFMessage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;


public class MessageMarshaller {

    public static byte[] marshal(RRFMessage rrfMessage) {

        Objects.requireNonNull(rrfMessage);

        int frameSize = FrameOffsets.MESSAGE_SIZE(rrfMessage.getUriSize(), rrfMessage.getPayloadSize());
        byte[] messageData = new byte[frameSize];

        messageData[FrameOffsets.MESSAGE_TYPE_OFFSET] = (byte) rrfMessage.getMessageType().getValue();
        messageData[FrameOffsets.TRANSACTION_ID_OFFSET] = (byte) rrfMessage.getTransactionID();
        messageData[FrameOffsets.EXPECT_ACK_OFFSET] = (byte) rrfMessage.getExpectAck().getValue();
        messageData[FrameOffsets.URI_SIZE_OFFSET] = (byte) rrfMessage.getUriSize();

        Charset charset = Charset.forName(FrameOffsets.CHARSET_NAME);
        byte[] uriBuffer = rrfMessage.getUri().getBytes(charset);
        System.arraycopy(uriBuffer, 0, messageData, FrameOffsets.URI_OFFSET, uriBuffer.length);

        messageData[FrameOffsets.PAYLOAD_SIZE_OFFSET(rrfMessage.getUriSize())] = (byte) rrfMessage.getPayloadSize();
        System.arraycopy(rrfMessage.getPayload(), 0, messageData, FrameOffsets.PAYLOAD_OFFSET(rrfMessage.getUriSize()), rrfMessage.getPayload().length);

        return messageData;
    }



    public static RRFMessage unmarshal(byte[] messageData) {
        Objects.requireNonNull(messageData);

        try {
            RRFMessage rrfMessage = new RRFMessage();

            rrfMessage.setMessageType(MessageType.getMessageType(messageData[FrameOffsets.MESSAGE_TYPE_OFFSET]));
            rrfMessage.setTransactionID(messageData[FrameOffsets.TRANSACTION_ID_OFFSET]);
            rrfMessage.setExpectAck(ExpectAck.getExpectedAck(messageData[FrameOffsets.EXPECT_ACK_OFFSET]));
            rrfMessage.setUriSize(getUnsigned(messageData[FrameOffsets.URI_SIZE_OFFSET]));
            if (rrfMessage.getUriSize() > 0) {
                rrfMessage.setUri(new String(messageData, FrameOffsets.URI_OFFSET, rrfMessage.getUriSize(), FrameOffsets.CHARSET_NAME));
            } else {
                rrfMessage.setUri("");
            }
            rrfMessage.setPayloadSize(getUnsigned(messageData[FrameOffsets.PAYLOAD_SIZE_OFFSET(rrfMessage.getUriSize())]));
            if (rrfMessage.getPayloadSize() > 0) {
                rrfMessage.setPayload(Arrays.copyOfRange(messageData, FrameOffsets.PAYLOAD_OFFSET(rrfMessage.getUriSize()), FrameOffsets.PAYLOAD_OFFSET(rrfMessage.getUriSize()) + rrfMessage.getPayloadSize()));
            } else {
                rrfMessage.setPayload(new byte[0]);
            }

            return rrfMessage;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getUnsigned(byte value) {
        return value & 0xFF;
    }
}