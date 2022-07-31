package us.bzsf.weatherreader;

/**
 * Provides an abstraction of cardinal directions with methods to
 * work with them.
 *
 * @author r@kuci.org
 */
public enum CardinalDirection {
  N(0),
  NNE(1),
  NE(2),
  ENE(3),
  E(4),
  ESE(5),
  SE(6),
  SSE(7),
  S(8),
  SSW(9),
  SW(10),
  WSW(11),
  W(12),
  WNW(13),
  NW(14),
  NNW(15),
  UNKNOWN(16);

  private int cardinalPosition;

  CardinalDirection(int cardinalPosition) {
    this.cardinalPosition = cardinalPosition;
  }

  public int getCardinalPosition() {
    return this.cardinalPosition;
  }

  public static CardinalDirection getCardinalDirectionByDegrees(int degrees) {
    for (CardinalDirection cardinalDirection : CardinalDirection.values()) {
      if (cardinalDirection.getCardinalPosition() == Math.floorDiv(degrees, 44)) {
        return cardinalDirection;
      }
    } 
    return CardinalDirection.UNKNOWN;
  }
}