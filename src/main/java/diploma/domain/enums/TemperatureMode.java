package diploma.domain.enums;

public enum TemperatureMode {
    NONE(-1), TEMP_MODE_MIN(15), TEMP_MODE_MID(20), TEMP_MODE_MAX(25);

    private final int temperature;

    TemperatureMode(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public static TemperatureMode getModeByTemp(int temperature) {
        for (TemperatureMode mode : TemperatureMode.values()) {
            if (mode.getTemperature() == temperature) {
                return mode;
            }
        }
        return NONE;
    }
}
