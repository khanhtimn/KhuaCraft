package store.teamti.network.handler;

public class ServerPayloadHandler {

    public static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();

    public static ServerPayloadHandler getInstance() {
        return INSTANCE;
    }
}
