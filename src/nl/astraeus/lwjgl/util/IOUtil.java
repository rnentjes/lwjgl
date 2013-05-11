package nl.astraeus.lwjgl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * User: rnentjes
 * Date: 5/11/13
 * Time: 12:27 PM
 */
public class IOUtil {
    private final static Logger logger = LoggerFactory.getLogger(IOUtil.class);

    private final static Charset UTF8 = Charset.forName("UTF-8");

    //@CheckForNull
    public static String loadFile(File file) {
        String result = null;

        if (file.exists()) {
            byte [] buffer = new byte[(int)file.length()];

            try (FileInputStream fis = new FileInputStream(file)) {
                int length = fis.read(buffer);

                if (length != buffer.length) {
                    throw new IllegalStateException("Length read and file.length differ!");
                }

                result = new String(buffer, UTF8);
            } catch (FileNotFoundException e) {
                logger.warn(e.getMessage(), e);
            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        }

        return result;
    }
}
