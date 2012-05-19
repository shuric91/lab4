package ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils;

import java.io.*;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.*;

public class DAOConfigurator {

    public static String getDAOFactoryName(String filename)
            throws SourceException, IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String s = reader.readLine();
            return s.split("=")[1];
        } catch (FileNotFoundException fnfe) {
            throw new SourceException(
                    "There are problems with defined DAOFactory from property file"
                            + fnfe.getMessage(), fnfe);
        } catch (IOException ioe) {
            throw new SourceException(
                    "There are problems with defined DAOFactory from property file"
                            + ioe.getMessage(), ioe);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

    }
}
