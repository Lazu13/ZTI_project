package pl.edu.agh.zti.schedulers;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Spring component allowing to convert data from url to byte[]
 */
@Data
@Component
public class UrlToByteArray {
    /**
     * Method converting url to byte[]
     *
     * @param url WEB file address
     * @return file as byte[]
     * @throws Exception if something goes wrong
     */
    public byte[] convert(URL url) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = url.openStream();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ((n = is.read(byteChunk)) > 0) {
                baos.write(byteChunk, 0, n);
            }
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
            e.printStackTrace();
            // Perform any other exception handling that's appropriate.
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return baos.toByteArray();
    }
}
