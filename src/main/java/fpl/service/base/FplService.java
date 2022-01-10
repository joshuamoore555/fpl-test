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
public class FplService {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final String BOOTSTRAP_STATIC = "/bootstrap-static/";
  private final FplRepository fplRepository;

  public FplData findAll() throws IOException {
    final String response = fplRepository.getResponse(BOOTSTRAP_STATIC);
    return MAPPER.readValue(response, FplData.class);
  }

  public Integer getPlayerCount() throws IOException {
    final var fplData = findAll();
    return fplData.getTotalPlayers();
  }
}
