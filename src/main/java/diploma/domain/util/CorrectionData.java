package diploma.domain.util;

import diploma.domain.enums.TemperatureMode;

import java.util.Map;
import java.util.TreeMap;

public final class CorrectionData {

    public static final Map<TemperatureMode, Map<Integer, Double>> HEAT_PRODUCTIVITY_CORRECTION = new TreeMap<>();
    public static final Map<TemperatureMode, Map<Integer, Double>> POWER_CONSUMPTION_CORRECTION = new TreeMap<>();

    static {
        initHeatProductivity();
        initPowerConsumption();
    }

    private static void initPowerConsumption() {
        Map<Integer, Double> powerConsumptionCorrection = new TreeMap<>();
        powerConsumptionCorrection.put(-25, 2.0);
        powerConsumptionCorrection.put(-24, 2.0);
        powerConsumptionCorrection.put(-23, 1.999);
        powerConsumptionCorrection.put(-22, 1.995);
        powerConsumptionCorrection.put(-21, 1.98);
        powerConsumptionCorrection.put(-20, 1.975);
        powerConsumptionCorrection.put(-19, 1.97);
        powerConsumptionCorrection.put(-18, 1.965);
        powerConsumptionCorrection.put(-17, 1.96);
        powerConsumptionCorrection.put(-16, 1.955);
        powerConsumptionCorrection.put(-15, 1.95);
        powerConsumptionCorrection.put(-14, 1.925);
        powerConsumptionCorrection.put(-13, 1.9);
        powerConsumptionCorrection.put(-12, 1.875);
        powerConsumptionCorrection.put(-11, 1.875);
        powerConsumptionCorrection.put(-10, 1.85);
        powerConsumptionCorrection.put(-9, 1.825);
        powerConsumptionCorrection.put(-8, 1.8);
        powerConsumptionCorrection.put(-7, 1.775);
        powerConsumptionCorrection.put(-6, 1.75);
        powerConsumptionCorrection.put(-5, 1.7);
        powerConsumptionCorrection.put(-4, 1.6375);
        powerConsumptionCorrection.put(-3, 1.575);
        powerConsumptionCorrection.put(-2, 1.5125);
        powerConsumptionCorrection.put(-1, 1.45);
        powerConsumptionCorrection.put(0, 1.44);
        powerConsumptionCorrection.put(1, 1.37);
        powerConsumptionCorrection.put(2, 1.3);
        powerConsumptionCorrection.put(3, 1.23);
        powerConsumptionCorrection.put(4, 1.16);
        powerConsumptionCorrection.put(5, 1.09);
        powerConsumptionCorrection.put(6, 1.104);
        powerConsumptionCorrection.put(7, 1.118);
        powerConsumptionCorrection.put(8, 1.132);
        powerConsumptionCorrection.put(9, 1.146);
        powerConsumptionCorrection.put(10, 1.16);
        powerConsumptionCorrection.put(11, 1.178);
        powerConsumptionCorrection.put(12, 1.196);
        powerConsumptionCorrection.put(13, 1.214);
        powerConsumptionCorrection.put(14, 1.232);
        powerConsumptionCorrection.put(15, 1.25);

        POWER_CONSUMPTION_CORRECTION.put(TemperatureMode.TEMP_MODE_MAX, powerConsumptionCorrection);
        for (int t = 20; t >= 15; t -= 5) {
            Map<Integer, Double> map = new TreeMap<>();
            for (Map.Entry<Integer, Double> entry : powerConsumptionCorrection.entrySet()) {
                map.put(entry.getKey(), entry.getValue() - 0.1);
            }
            POWER_CONSUMPTION_CORRECTION.put(TemperatureMode.getModeByTemp(t), map);
        }
    }

    private static void initHeatProductivity() {
        double deltaRate = 0.02;
        double startRate = 0.84;
        double idleRate = 1.04;
        int counter = 0;
        for (int t = 15; t <= 25; t += 5) {
            Map<Integer, Double> heatProductivityCorrection = new TreeMap<>();
            for (int tout = -25; tout <= 15; tout++) {
                double rate;
                if (tout >= -15 && tout <= 5) {
                    rate = idleRate - counter * 2 * deltaRate;
                    heatProductivityCorrection.put(tout, rate);
                } else {
                    int index = -1;
                    if (!heatProductivityCorrection.containsKey(tout + index)) {
                        rate = startRate - counter * 2 * deltaRate;
                    } else {
                        rate = heatProductivityCorrection.get(tout + index) + deltaRate;
                    }
                    heatProductivityCorrection.put(tout, rate);
                }
            }
            counter++;
            HEAT_PRODUCTIVITY_CORRECTION.put(TemperatureMode.getModeByTemp(t), heatProductivityCorrection);
        }
    }

    private CorrectionData() {
    }
}
