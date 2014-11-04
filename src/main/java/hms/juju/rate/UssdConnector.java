package hms.juju.rate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hms.kite.samples.api.ussd.UssdRequestSender;
import hms.kite.samples.api.ussd.messages.MoUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdResp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason on 24/10/2014.
 */
public class UssdConnector {

    private final Gson gson;
    private final Map<String, ConnectionDetails> detailsMap;

    public UssdConnector() throws IOException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader reader = new BufferedReader(new FileReader("/opt/hms/rate-me/conf/ctap_config.json"));
        detailsMap = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            String appId = split[1];
            String password = split[2];
            String url = split[3];
            detailsMap.put(split[1], new ConnectionDetails(appId, password, url));
        }
    }


    public Response send(Message message) {

        try {
            ConnectionDetails connectionDetails = detailsMap.get(message.getMoMessage().getApplicationId());
            if (connectionDetails == null) {
                throw new RuntimeException("Connection details not found for appId[" + message.getMoMessage().getApplicationId() + "]");
            }
            System.out.println("Connection Details : [" + connectionDetails + "]");

            UssdRequestSender ussdMtSender = new UssdRequestSender(new URL(connectionDetails.url));
            MoUssdReq moUssdReq = message.getMoMessage();
            MtUssdReq request = new MtUssdReq();
            request.setApplicationId(connectionDetails.appId);
            request.setPassword(connectionDetails.password);
            request.setEncoding(moUssdReq.getEncoding());
            request.setMessage(message.getMessage());
            request.setSessionId(moUssdReq.getSessionId());
            request.setUssdOperation("mo-cont");
            request.setVersion(moUssdReq.getVersion());
            request.setDestinationAddress(moUssdReq.getSourceAddress());
            System.out.println("Sending message : " + gson.toJson(request));
            MtUssdResp mtUssdResp = ussdMtSender.sendUssdRequest(request);
            System.out.println("Message Sent Status : " + gson.toJson(mtUssdResp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }

    private class ConnectionDetails {

        private final String appId;
        private final String password;
        private final String url;

        public ConnectionDetails(String appId, String password, String url) {
            this.appId = appId;
            this.password = password;
            this.url = url;
        }

        @Override
        public String toString() {
            return "ConnectionDetails{" +
                    "appId='" + appId + '\'' +
                    ", password='" + password + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
