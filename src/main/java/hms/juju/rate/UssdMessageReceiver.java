package hms.juju.rate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hms.kite.samples.api.ussd.MoUssdListener;
import hms.kite.samples.api.ussd.messages.MoUssdReq;

import java.io.IOException;

/**
 * Created by Jason on 24/10/2014.
 */
public class UssdMessageReceiver implements MoUssdListener {

    private Gson gson;
    private RateMeService rateMeService;

    @Override
    public void init() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            rateMeService = new RateMeService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceivedUssd(MoUssdReq moUssdReq) {
        System.out.println("Message received : " + gson.toJson(moUssdReq));
        Message message = new Message();
        message.setMoMessage(moUssdReq);
        message.setNewSession(moUssdReq.getUssdOperation().equals("mo-init"));
        message.setSource("idea-mart");
        rateMeService.ussdProcess(message);
    }
}
