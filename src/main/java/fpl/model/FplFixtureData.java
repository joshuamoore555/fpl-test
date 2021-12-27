package fpl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;


@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FplFixtureData {



  @JsonProperty("team_h")
  private String homeTeamIndex;
  @JsonProperty("team_a")
  private String awayTeamIndex;

  private String homeName;
  private String awayName;

  @JsonProperty("team_h_score")
  private String homeTeamScore;
  @JsonProperty("team_a_score")
  private String awayTeamScore;

  @JsonProperty("team_h_difficulty")
  private String homeTeamDifficulty;
  @JsonProperty("team_a_difficulty")
  private String awayTeamDifficulty;

  @JsonProperty("event")
  private String event;


}
