package communicaton;

import com.digi.xbee.api.models.XBee64BitAddress;
import de.ude.es.gatewaymessagequeue.addresses.XbeeAddress64Bit;

public class  CommunicationAddress extends XbeeAddress64Bit {

   public CommunicationAddress(byte[] address) {
        super(new XBee64BitAddress(address));
    }

}