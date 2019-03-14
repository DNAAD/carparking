package com.coalvalue.service.assistant;

import com.coalvalue.service.assistant.BonjourService;



import java.util.logging.ConsoleHandler;
//import org.slf4j.Logger;
import java.util.logging.Logger;

import java.io.IOException;

import java.util.logging.Level;


import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;

/**
 * Sample Code for Service Type Discovery using JmDNS and a ServiceTypeListener.
 * <p>
 * Run the main method of this class. It lists all service types known on the local network on System.out.
 *
 * @author Werner Randelshofer
 */
public class DiscoverServiceTypes {


    private static class SampleListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added: " + event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
        }
    }



    static class SampleServiceTypeListener implements ServiceTypeListener {
        JmDNS jmdns;

        public SampleServiceTypeListener(JmDNS jmdns) {
            this.jmdns = jmdns;
        }

        @Override
        public void serviceTypeAdded(ServiceEvent event) {
            System.out.println("Service type added: " + event.getType()+"  |||"+event.getName());
            System.out.println("-----------: " + event.getDNS().getHostName());

            for(java.net.InetAddress inetAddresses:event.getInfo().getInetAddresses()){
                System.out.println("-----------inetAddresses: " + inetAddresses.toString());

            }

            jmdns.addServiceListener(event.getType(), new SampleListener());
        }

        /*
         * (non-Javadoc)
         * @see javax.jmdns.ServiceTypeListener#subTypeForServiceTypeAdded(javax.jmdns.ServiceEvent)
         */
        @Override
        public void subTypeForServiceTypeAdded(ServiceEvent event) {
            System.out.println("SubType for service type added: " + event.getType());
        }
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Activate these lines to see log messages of JmDNS Logger logger = LoggerFactory.getLogger(JmDNS.class.getName()); ConsoleHandler handler = new ConsoleHandler(); logger.addHandler(handler); logger.setLevel(Level.FINER);
         * handler.setLevel(Level.FINER);
         */


        /* Activate these lines to see log messages of JmDNS */


/*
        boolean log = true;
        if (log) {
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(Level.FINEST);
            for (Enumeration<String> enumerator = LogManager.getLogManager().getLoggerNames(); enumerator.hasMoreElements();) {
                String loggerName = enumerator.nextElement();
                Logger logger = Logger.getLogger(loggerName);
                logger.addHandler(handler);
              logger.setLevel(Level.INFO);
            }




        }

        */

        boolean log = true;
        if (log) {

        }
        Logger logger = Logger.getLogger(JmDNS.class.getName());
        ConsoleHandler handler = new ConsoleHandler();
        logger.addHandler(handler);
        logger.setLevel(Level.OFF);
        handler.setLevel(Level.OFF);

                try {
            JmDNS jmdns = JmDNS.create();
            jmdns.addServiceTypeListener(new SampleServiceTypeListener(jmdns));
            //jmdns.addServiceListener("_http._tcp.local.", new SampleListener());

            System.out.println("Press q and Enter, to quit");
            int b;
            while ((b = System.in.read()) != -1 && (char) b != 'q') {
                /* Stub */
            }
            jmdns.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}