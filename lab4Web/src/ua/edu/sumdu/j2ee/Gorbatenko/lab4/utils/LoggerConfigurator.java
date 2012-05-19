package ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LoggerConfigurator {
    
    public static Logger configure(String loggerName) {
        Logger log =Logger.getLogger(loggerName);
        String pattern = "[%d{dd.MM.yyyy HH:mm:ss}] %m %n";
        PatternLayout layout = new PatternLayout(pattern);
        
        ConsoleAppender consoleAppender = new ConsoleAppender(layout);
        log.addAppender(consoleAppender);
        FileAppender appender = null;
        try {
            appender = new FileAppender(layout, "lab4.log", false);
            appender.setImmediateFlush(true);
        } catch (IOException e) {
            log.warn(e);
        }
        
        if(appender != null) {
            log.addAppender(appender);
        }
        
        return log;
    }
}
