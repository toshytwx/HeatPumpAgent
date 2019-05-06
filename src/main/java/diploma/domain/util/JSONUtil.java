package diploma.domain.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

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
        NumberFormat formatter = new DecimalFormat("#0.00");
        int index = 0;
        builder.append("\"").append("[");
        for (Integer temp : temperature) {
            builder.append("{")
                    .append("\\\"temperature\\\"")
                    .append(": ").append("\\\"").append(temp).append("\\\"")
                    .append(",")
                    .append("\\\"heatLoss\\\"")
                    .append(": ").append("\\\"").append(formatter.format(heatLoss.get(index))).append("\\\"")
                    .append(",")
                    .append("\\\"heatProductivityNormalizationCoefficient\\\"")
                    .append(": ").append("\\\"").append(formatter.format(heatProdNormCoefficient.get(index))).append("\\\"")
                    .append(",")
                    .append("\\\"normalizedHeatProductivity\\\"")
                    .append(": ").append("\\\"").append(formatter.format(normalizedHeatProd.get(index))).append("\\\"")
                    .append(",")
                    .append("\\\"powerConsumptionNormalizationCoefficient\\\"")
                    .append(": ").append("\\\"").append(formatter.format(powerConsNormCoefficient.get(index))).append("\\\"")
                    .append(",")
                    .append("\\\"normalizedPowerConsumption\\\"")
                    .append(": ").append("\\\"").append(formatter.format(normalizedPowerCons.get(index))).append("\\\"")
                    .append(",")
                    .append("\\\"heatProductivityOfAdditionalHeater\\\"")
                    .append(": ").append("\\\"").append(formatter.format(heatProdAdditionalHeater.get(index))).append("\\\"")
                    .append(",")
                    .append("\\\"workloadCoefficient\\\"")
                    .append(": ").append("\\\"").append(formatter.format(workloadCoefficient.get(index))).append("\\\"")
                    .append("}")
                    .append(",");
            index++;
        }
        return builder.deleteCharAt(builder.length() - 1).append("]").append("\"").toString();
    }
}
