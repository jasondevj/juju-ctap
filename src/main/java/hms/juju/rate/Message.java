package hms.juju.rate;

import hms.kite.samples.api.ussd.messages.MoUssdReq;

/**
 * Created by Jason on 24/10/2014.
 */
public class Message {

    private MoUssdReq moMessage;
    private String message;
    private boolean newSession;
    private String source;

    public void setMoMessage(MoUssdReq moMessage) {
        this.moMessage = moMessage;
    }

    public MoUssdReq getMoMessage() {
        return moMessage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setNewSession(boolean newSession) {
        this.newSession = newSession;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public boolean isNewSession() {
        return newSession;
    }
}
