package fpl.service.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpl.model.FplData;
import fpl.repository.FplRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FplElementService {
  private static final String BOOTSTRAP_STATIC = "/bootstrap-static/";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private final FplRepository fplRepository;

  public FplData getFplData() throws IOException {
    final String response = fplRepository.getResponse(BOOTSTRAP_STATIC);
    return MAPPER.readValue(response, FplData.class);
  }
}
