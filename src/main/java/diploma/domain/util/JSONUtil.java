package diploma.domain.util;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public final class JSONUtil {
    private JSONUtil() {

    }

    public static String convertPumpDataToJSON(List<Integer> temperature,
                                               List<Double> heatLoss,
                                               List<Double> heatProdNormCoefficient,
                                               List<Double> normalizedHeatProd,
                                               List<Double> powerConsNormCoefficient,
                                               List<Double> normalizedPowerCons,
                                               List<Double> heatProdAdditionalHeater,
                                               List<Double> workloadCoefficient
    ) {
        StringBuilder builder = new StringBuilder();
        NumberFormat formatter = NumberFormat.getInstance(Locale.ENGLISH);
        int index = 0;
        builder.append("\"").append("[");
        for (Integer temp : temperature) {
            builder.append("{")
                    .append("\\\"temperature\\\"")
                    .append(": ").append(temp)
                    .append(",")
                    .append("\\\"heatLoss\\\"")
                    .append(": ").append(formatter.format(heatLoss.get(index)))
                    .append(",")
                    .append("\\\"heatProductivityNormalizationCoefficient\\\"")
                    .append(": ").append(formatter.format(heatProdNormCoefficient.get(index)))
                    .append(",")
                    .append("\\\"normalizedHeatProductivity\\\"")
                    .append(": ").append(formatter.format(normalizedHeatProd.get(index)))
                    .append(",")
                    .append("\\\"powerConsumptionNormalizationCoefficient\\\"")
                    .append(": ").append(formatter.format(powerConsNormCoefficient.get(index)))
                    .append(",")
                    .append("\\\"normalizedPowerConsumption\\\"")
                    .append(": ").append(formatter.format(normalizedPowerCons.get(index)))
                    .append(",")
                    .append("\\\"heatProductivityOfAdditionalHeater\\\"")
                    .append(": ").append(formatter.format(heatProdAdditionalHeater.get(index)))
                    .append(",")
                    .append("\\\"workloadCoefficient\\\"")
                    .append(": ").append(formatter.format(workloadCoefficient.get(index)))
                    .append("}")
                    .append(",");
            index++;
        }
        return builder.deleteCharAt(builder.length() - 1).append("]").append("\"").toString();
    }
}
