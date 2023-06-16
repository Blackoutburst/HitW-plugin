package analytics;

public enum AnalyticsWallType {
    QUALIFICATION("0", "Qualification"),
    FINALS("1", "Finals"),
    LOBBY("2", "Lobby Wall"),
    WIDE_QUALIFICATION("3", "Wide Qualification"),
    WIDE_FINALS("4", "Wide Finals");

    public final String data;

    private final String stringName;

    AnalyticsWallType(String data, String stringName) {
        this.data = data;
        this.stringName = stringName;
    }

    public static AnalyticsWallType getFromStringName(String type) {
        for (AnalyticsWallType wall : AnalyticsWallType.values()) {
            if (wall.stringName.equals(type)) return wall;
        }

        return null;
    }
}
