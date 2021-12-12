package fpl.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import static fpl.util.RestUtil.getGenericUrl;

@Repository
public class FplRepository {
    private static final String FPL_BASE_URL = "https://fantasy.premierleague.com/api";
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();


    public String getResponse(String path) throws IOException {
        HttpRequestFactory requestFactory
                = HTTP_TRANSPORT.createRequestFactory();
        GenericUrl url = getGenericUrl(FPL_BASE_URL, path);
        HttpRequest request = requestFactory.buildGetRequest(url);
        return request.execute().parseAsString();
    }
}
