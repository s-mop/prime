package org.smop.red;

import org.smop.red.client.RedFluxClient;
import org.smop.red.client.RedTalkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@RestController
public class RedTalkService implements RedFluxClient {

    public List<String> hi() {
        return getIPs();
    }

    @Override
    public Mono<List<String> > yo() {
        return Mono.just(getIPs());
    }
    
    @Value("${server.port}")
    private int port;

    public String hello() {
        return "hello";
    }

    private List<String> getIPs() {
        try {
            List<String> allIPs = new ArrayList<>();
            Enumeration<NetworkInterface> allNetworkInterfaces =
                    NetworkInterface.getNetworkInterfaces();
            while (allNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = allNetworkInterfaces.nextElement();
                Enumeration<InetAddress> allInetAddress =
                        networkInterface.getInetAddresses();
                while (allInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = allInetAddress.nextElement();
                    if (inetAddress instanceof Inet4Address)
                        allIPs.add(inetAddress.getHostAddress());
                }
            }
            return allIPs;

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }


}
