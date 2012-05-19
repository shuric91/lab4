package ua.edu.sumdu.j2ee.Gorbatenko.lab4.Controllers;


//import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.*;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.LoggerConfigurator;

/**
 * Application Lifecycle Listener implementation class Listener
 * 
 */
//@WebListener
public class Listener implements HttpSessionListener {
    
    public static Logger log = LoggerConfigurator.configure(Listener.class.getName());

    /**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
        log.info("Session starting...");
    }

    /**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {

    }

}
