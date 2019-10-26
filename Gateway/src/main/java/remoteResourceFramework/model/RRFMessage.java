package remoteResourceFramework.model;

import de.ude.es.gatewaymessagequeue.addresses.CommunicationAddress;
import de.ude.es.gatewaymessagequeue.addresses.XbeeAddress64Bit;
import de.ude.es.gatewaymessagequeue.utilities.StringUtils;

import java.util.Objects;


public class RRFMessage {
    private MessageType messageType;
    private int transactionID;
    private ExpectAck expectAck;
    private int uriSize;
    private String uri;
    private int payloadSize;
    private byte[] payload;

    private CommunicationAddress address;

    public RRFMessage() {
        //empty constructor
    }

    public RRFMessage(MessageType messageType, int transactionID, ExpectAck expectAck, String uri, byte[] payload, CommunicationAddress address) {
        this.messageType = messageType;
        this.transactionID = transactionID;
        this.expectAck = expectAck;
        this.uri = Objects.requireNonNull(uri);
        this.uriSize = uri.length();
        this.payloadSize = payload.length;
        this.payload = payload;
        this.address = address;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public ExpectAck getExpectAck() {
        return expectAck;
    }

    public void setExpectAck(ExpectAck expectAck) {
        this.expectAck = expectAck;
    }

    public int getUriSize() {
        return uriSize;
    }

    public void setUriSize(int uriSize) {
        this.uriSize = uriSize;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    public void setPayloadSize(int payloadSize) {
        this.payloadSize = payloadSize;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public CommunicationAddress getAddress() {
        return address;
    }

    public void setAddress(CommunicationAddress address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RRFMessage{" +
                "messageType=" + messageType +
                ", transactionID=" + transactionID +
                ", expectAck=" + expectAck +
                ", uriSize=" + uriSize +
                ", uri='" + uri + '\'' +
                ", payloadSize=" + payloadSize +
                ", payload=" + StringUtils.toHexString(payload) +
                ", address=" + address +
                '}';
    }
}