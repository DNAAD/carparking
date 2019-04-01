package com.coalvalue.compont.backup;// Licensed under Apache License version 2.0
// Original license LGPL

//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA



import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

/**
 * Sample Code for Service Discovery using JmDNS and a ServiceListener.
 * <p>
 * Run the main method of this class. It listens for HTTP services and lists all changes on System.out.
 *
 * @author Werner Randelshofer
 */
public class ExampleServiceDiscovery {

    static class SampleListener implements ServiceListener {
        JmDNS jmdns;
        public SampleListener(JmDNS jmdns) {
            this.jmdns = jmdns;
        }

        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added   : " + event.getName() + "." + event.getType());
            ServiceInfo[] serviceInfos = jmdns.list("_yulinmei._tcp.local.",6000);
            for (ServiceInfo info : serviceInfos) {
                //   jmdns.requestServiceInfo();
                System.out.println("## resolve service " + info.getKey()+info.getName()  + " : " + info.getURL());
                Map<String,Object> objectMap = new HashMap<>();
                objectMap.put("key", info.getKey());
                objectMap.put("name", info.getName());
                objectMap.put("type", info.getType());
                objectMap.put("domain", info.getDomain());
                objectMap.put("protocol", info.getProtocol());
                objectMap.put("application", info.getApplication());
                objectMap.put("subtype", info.getSubtype());
                objectMap.put("server", info.getServer());
                objectMap.put("port", info.getPort());
                objectMap.put("weight", info.getWeight());
                objectMap.put("priority", info.getPriority());
                objectMap.put("hostAddresses", info.getHostAddresses().toString());

                objectMap.put("url", Arrays.stream(info.getURLs()).map(e->e.toString()).collect(Collectors.toList()).toString());
                System.out.println(""+objectMap.toString());
            }

        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed : " + event.getName() + "." + event.getType());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
        }
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        try {

            // Activate these lines to see log messages of JmDNS
            boolean log = false;
            if (log) {
                Logger logger = Logger.getLogger(JmDNS.class.getName());
                ConsoleHandler handler = new ConsoleHandler();
                logger.addHandler(handler);
                logger.setLevel(Level.FINER);
                handler.setLevel(Level.FINER);
            }

            final JmDNS jmdns = JmDNS.create();
            jmdns.addServiceListener("_yulinmei._tcp.local.", new SampleListener(jmdns));




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