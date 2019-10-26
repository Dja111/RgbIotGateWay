package communicaton;

import de.ude.es.gatewaymessagequeue.message.Message64BitAddress;
import de.ude.es.gatewaymessagequeue.utilities.StringUtils;

public class Message extends Message64BitAddress {
    private de.ude.es.gatewaymessagequeue.addresses.CommunicationAddress address;
    private byte[] payload;
    
    Message(de.ude.es.gatewaymessagequeue.addresses.CommunicationAddress address, byte[] payload) {
        super(address, payload);
        this.address = address;
        this.payload = payload;
    }
    
    public Message(byte[] address, byte[] payload) {
        this(new CommunicationAddress(address),payload);
    }

    public byte[] getPayload(){
        return this.payload;
    }


    @Override
    public String toString() {
        String addressAsString = "Adresse als Hex: "+StringUtils.toHexString(address.getAddressValue());
        StringBuilder sb = new StringBuilder(addressAsString);
        sb.append("\n");
        for (int i = 0; i < payload.length/4; i++) {
            sb.append("Lampe # ").append(payload[i*4+0]);
            sb.append("  Rot: ").append(payload[i*4+1]);
            sb.append("  Gruen: ").append(payload[i*4+2]);
            sb.append("  Blau: ").append(payload[i*4+3]);
            sb.append("\n");
        }
        return sb.toString();
    }
}
