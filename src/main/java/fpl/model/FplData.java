package fpl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Data
public class FplData {

  private List<LinkedHashMap<String, Object>> events;


  private List<LinkedHashMap<String, Object>> phases;


  private List<LinkedHashMap<String, Object>> teams;

  private List<LinkedHashMap<String, Object>> elements;

  @JsonProperty("game_settings")
  private LinkedHashMap<String, Object> gameSettings;

  @JsonProperty("total_players")
  private Integer totalPlayers;

  @JsonProperty("element_stats")
  private List<LinkedHashMap<String, Object>> elementStats;

  @JsonProperty("element_types")
  private List<LinkedHashMap<String, Object>> elementTypes;
}
