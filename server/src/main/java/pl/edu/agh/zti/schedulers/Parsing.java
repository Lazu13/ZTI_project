package pl.edu.agh.zti.schedulers;

import lombok.Data;
import pl.edu.agh.zti.annotations.logging.Loggable;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

/**
 * Abstract class parsing file and inserting to db
 */
@Data
public abstract class Parsing {

    protected UrlToByteArray urlToByteArray = new UrlToByteArray();

    /**
     * Method parsing local file and inserting data to db
     *
     * @param filename name of file
     * @throws Exception if something goes wrong
     */
    @Loggable
    public void parse(String filename) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        byte[] jsonData = Files.readAllBytes(file.toPath());
        parse(jsonData);
    }

    /**
     * Method parsing file from WEB and sinerting data to db
     *
     * @param url url of file
     * @throws Exception if something goes wrong
     */
    @Loggable
    public void parse(URL url) throws Exception {
        byte[] jsonData = urlToByteArray.convert(url);
        parse(jsonData);
    }

    abstract protected void parse(byte[] bytes) throws Exception;
}
