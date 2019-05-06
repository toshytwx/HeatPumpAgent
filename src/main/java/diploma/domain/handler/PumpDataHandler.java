package diploma.domain.handler;

import diploma.domain.enums.TemperatureMode;
import diploma.domain.util.CorrectionData;

import java.util.Map;
import java.util.TreeMap;

public final class PumpDataHandler {

    private static final Map<Integer, Double> BUILDING_HEAT_LOSS = new TreeMap<>();

    static {
        for (int t = -25; t <= 15; t++) {
            double value = (340.0 - 17.0 * t) / 41.0;
            BUILDING_HEAT_LOSS.put(t, value);
        }
    }

    public static Double getHeatLoss(int temperature) {
        return BUILDING_HEAT_LOSS.get(temperature);
    }

    public static Double getNormalizedPowerConsumption(int temperature, TemperatureMode temperatureMode) {
        Map<Integer, Double> modePowerConsumption = CorrectionData.POWER_CONSUMPTION_CORRECTION.get(temperatureMode);
        return modePowerConsumption.get(temperature);
    }

    public static Double getNormalizedHeatProductivity(int temperature, TemperatureMode temperatureMode) {
        Map<Integer, Double> modeHeatProductivity = CorrectionData.HEAT_PRODUCTIVITY_CORRECTION.get(temperatureMode);
        return modeHeatProductivity.get(temperature);
    }
}
