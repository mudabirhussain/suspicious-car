package ai.suspicious.car.utils;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageUtil {


    public static Resource base64ToResource(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) {
            throw new IllegalArgumentException("Base64 image string cannot be null or empty");
        }
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return new ByteArrayResource(imageBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to decode Base64 string", e);
        }
    }
}
