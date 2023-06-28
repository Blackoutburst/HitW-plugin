package analytics;

public enum AnalyticsActions {
    PLAYER_JOIN("0"),
    PLAYER_LEAVE("1"),
    GAME_START("2"),
    GAME_NATURAL_END("3"),
    GAME_MANUAL_END("4");

    public final String data;

    AnalyticsActions(String data) {
        this.data = data;
    }
}
