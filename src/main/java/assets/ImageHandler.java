package assets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class ImageHandler {
    private static final Logger logger = LogManager.getLogger(ImageHandler.class);
    public static ImageView getImage(String property) {
        try {
            InputStream input = new FileInputStream("./src/main/resources/config/Images.properties");
            Properties properties = new Properties();
            properties.load(input);
            input.close();
            FileInputStream inputImage = new FileInputStream(properties.getProperty(property));
            Image image = new Image(inputImage);
            ImageView imageView = new ImageView(image);
            inputImage.close();
            return imageView;
        }catch (IOException e) {
            logger.error("Couldn't load Image");
        }
        return null;
    }
}
