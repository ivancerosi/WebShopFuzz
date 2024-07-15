package hr.algebra.api.webshop2024api;

import java.net.MalformedURLException;
import java.net.URL;

public class ImageUrlValidator {
    public static boolean isValid(String url) {
        try {
            URL _url = new URL(url);


        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
}
