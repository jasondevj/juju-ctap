package hms.juju.rate;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Jason on 24/10/2014.
 */
public class RateMeService {

    private UssdConnector ussdConnector;

    public RateMeService() throws IOException {
        ussdConnector = new UssdConnector();
    }

    public void ussdProcess(Message message) {
        String requestMessage = message.getMoMessage().getMessage();

        try {
            if (requestMessage.isEmpty() || message.isNewSession()) {
                message.setMessage("Best Movie in 2014\n 1) Transformers\n2) X-Men\n3) Godzilla\n4) Spider Man");
                ussdConnector.send(message);
            } else {
                //todo keep a counter and send back the response
                String responseMessage = "Thank you for your response, winner will be announced end of the week.";
                switch (requestMessage) {
                    case "1": {

                    }
                    case "2" : {

                    }
                    case "3" : {

                    }
                    case "4" : {
                        message.setMessage(responseMessage);
                    }

                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            message.setMessage("Invalid selection.");
            ussdConnector.send(message);
        }
    }
}
