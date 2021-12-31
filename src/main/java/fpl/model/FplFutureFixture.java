package fpl.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Builder
public class FplFutureFixture {
  String teamName;
  Integer gameweek;
  boolean isNextGameweek;
  String fixtures;
}
