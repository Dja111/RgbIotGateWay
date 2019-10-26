package remoteResourceFramework;


import remoteResourceFramework.model.RRFMessage;


public interface MessageHandler {
    void handleMessage(RRFMessage rrfMessage);
}