package fpl.util;

import com.google.api.client.http.GenericUrl;

public class RestUtil {
    public static GenericUrl getGenericUrl(String baseUrl, String path) {
        return new GenericUrl(baseUrl + path);
    }

}