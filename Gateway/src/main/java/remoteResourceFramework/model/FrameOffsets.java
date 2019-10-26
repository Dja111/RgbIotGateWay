package remoteResourceFramework.model;


public class FrameOffsets {

    public static final String CHARSET_NAME = "US-ASCII";

    public static final int MESSAGE_HEADER_SIZE = 5;
    public static final int MESSAGE_TYPE_OFFSET = 0;
    public static final int TRANSACTION_ID_OFFSET = 1;
    public static final int EXPECT_ACK_OFFSET = 2;
    public static final int URI_SIZE_OFFSET = 3;
    public static final int URI_OFFSET = 4;

    public static int PAYLOAD_SIZE_OFFSET(int uriSize) {
        return URI_OFFSET + uriSize;
    }

    public static int PAYLOAD_OFFSET(int uriSize) {
        return PAYLOAD_SIZE_OFFSET(uriSize) + 1;
    }

    public static int MESSAGE_SIZE(int uriSize, int payloadSize) {
        return MESSAGE_HEADER_SIZE + uriSize + payloadSize;
    }

}