package net.ielpo.reactivestack.util;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alberto Ielpo
 */
public class PortChecker {

    private static final Logger logger = LoggerFactory.getLogger(PortChecker.class);

    /**
     * If a socket is possible it means that a connection is possible
     * 
     * @param port
     * @return
     */
    public static boolean isConnectionPossible(String host, int port) {
        logger.info(String.format("Trying to connect to %s:%s", host, port));
        Socket s = null;
        try {
            s = new Socket(host, port);
            logger.info(String.format("Connection is possible %s:%s", host, port));
            return true;
        } catch (IOException e) {
            logger.info(String.format("Cannot connect to %s:%s", host, port));
            return false;
        } finally {
            if (s != null) {
                try {
                    s.close();
                    logger.info(String.format("Connection released %s:%s", host, port));
                } catch (IOException e) {
                    throw new RuntimeException("Cannot close socket!", e);
                }
            }
        }
    }

    /**
     * 
     * @param port
     * @return
     */
    public static boolean isConnectionPossible(int port) {
        return PortChecker.isConnectionPossible("localhost", port);
    }

}