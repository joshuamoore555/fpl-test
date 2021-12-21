package fpl.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;


@Builder
@Value
public class Player implements Comparable<Player> {
  String name;
  String position;
  double ictIndex;

  @Override
  public int compareTo(Player o) {
    return Double.compare(this.getIctIndex(), o.getIctIndex());
  }
}
