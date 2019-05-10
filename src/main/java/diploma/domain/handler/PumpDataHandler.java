package diploma.domain.handler;

import diploma.domain.enums.TemperatureMode;
import diploma.domain.util.CorrectionData;

import java.util.Map;
import java.util.TreeMap;

public final class PumpDataHandler {
    private static final Map<Integer, Double> BUILDING_HEAT_LOSS = new TreeMap<>();

    private PumpDataHandler() {
    }

    public static void initValues(Double heatLoss) {
        for (int t = -40; t <= 40; t++) {
            double value = (heatLoss * (-t + 20)) / 42.0;
            value = value < 0 ? 0 : value;
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
