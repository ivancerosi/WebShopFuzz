package hr.algebra.utils.ImageConverter;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

@RequiredArgsConstructor
public class ImageConverter {
    final int width;
    final int height;
    final String format;

    public String convertToBase64(String url) throws IOException {
        return convertToBase64(new URL(url));
    }

    public String convertToBase64(URL url) throws IOException {
        return convertToBase64(ImageIO.read(url));
    }

    public String convertToBase64(BufferedImage bufferedImage) throws IOException {
        BufferedImage result = Thumbnails.of(bufferedImage).width(width).height(height).asBufferedImage();

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(result, "png", bos);

        final String base64 = Base64.getEncoder().encodeToString(bos.toByteArray());
        return String.format("data:image/%s;base64,%s",format,base64);
    }
}