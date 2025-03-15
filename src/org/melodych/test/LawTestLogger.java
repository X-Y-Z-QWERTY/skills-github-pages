package org.melodych.test;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LawTestLogger {

    private final Logger logger;

    public  LawTestLogger() {
        logger = Logger.getLogger("logger");

        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler("LawTestApp.log", true);
        } catch (IOException e) {return;}

        fileHandler.setFormatter(new SimpleFormatter());


        logger.addHandler(fileHandler);
    }
    public Logger getLawTestLogger() {return logger;}
}
