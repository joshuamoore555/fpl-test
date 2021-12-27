package fpl.util;

import com.google.api.client.http.GenericUrl;

public class RestUtil {
  private RestUtil() {}

  public static GenericUrl getGenericUrl(final String baseUrl, final String path) {
    return new GenericUrl(baseUrl + path);
  }
}
