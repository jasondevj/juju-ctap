package hms.juju.rate;

import hms.kite.samples.api.ussd.MoUssdReceiver;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by Jason on 4/11/2014.
 */
public class Starter {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8090);
        ServletHandler handler = new ServletHandler();
        ServletHolder servletHolder = handler.addServletWithMapping(MoUssdReceiver.class, "/rate-me/");
        servletHolder.setInitParameter("ussdReceiver", "hms.juju.rate.UssdMessageReceiver");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}
