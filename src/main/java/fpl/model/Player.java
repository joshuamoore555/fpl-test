package fpl.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.util.List;


@Builder
@Data
public class Player implements Comparable<Player> {
  String name;
  String position;
  String id;
  String form;
  String transfersIn;
  String transfersOut;
  String goals;
  String assists;
  String minutesPlayed;
  String saves;
  String yellowCards;
  String redCards;
  String bonusPoints;
  String team;
  String selectedBy;
  List<FplFixtureData> nextFixtures;
  double ictIndex;

  @Override
  public int compareTo(Player o) {
    return Double.compare(this.getIctIndex(), o.getIctIndex());
  }
}
