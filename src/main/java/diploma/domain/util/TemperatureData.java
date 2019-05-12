package diploma.domain.util;

import diploma.domain.entities.FormattedDate;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class TemperatureData {
    private static final int MAX_CAPACITY = 48;

    private static final Logger logger = LoggerFactory.getLogger(TemperatureData.class);
    private static final Map<FormattedDate, Integer> hourTemperature = new TreeMap<>();

    static {
        try {
            XLSXUtil.readXLSXTo(hourTemperature, "src/main/resources/data/temperature.xlsx");
        } catch (IOException | InvalidFormatException e) {
            logger.error(e.getMessage());
        }
    }

    private TemperatureData() {
    }

    public static Map<FormattedDate, Integer> getTemperatureForPeriod(final String dateFrom, final String dateTo) {
        Map<FormattedDate, Integer> collect = getFormattedDateInPeriod(dateFrom, dateTo);
        List<FormattedDate> keys = new ArrayList<>(hourTemperature.keySet());
        int keysIncrement = collect.size() / MAX_CAPACITY;
        return collect.entrySet().stream()
                .filter((Map.Entry<FormattedDate, Integer> x) -> {
                    FormattedDate key = x.getKey();
                    int index = keys.indexOf(key);
                    return index % keysIncrement == 0;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static Map<Integer, Integer> getTemperatureFrequency(final String dateFrom, final String dateTo) {
        Map<Integer, Integer> temperatureFrequency = new TreeMap<>();
        Map<FormattedDate, Integer> formattedDateInPeriod = getFormattedDateInPeriod(dateFrom, dateTo);
        for (Integer temperature : formattedDateInPeriod.values()) {
            Integer count = temperatureFrequency.get(temperature);
            if (count == null) {
                temperatureFrequency.put(temperature, 1);
            } else {
                temperatureFrequency.put(temperature, ++count);
            }
        }
        return temperatureFrequency;
    }

    private static Map<FormattedDate, Integer> getFormattedDateInPeriod(String dateFrom, String dateTo) {
        FormattedDate formDateTo = new FormattedDate(dateTo);
        FormattedDate formDateFrom = new FormattedDate(dateFrom);
        return new TreeMap<>(hourTemperature).entrySet().stream()
                .filter((Map.Entry<FormattedDate, Integer> x) -> {
                    FormattedDate formattedDate = x.getKey();
                    return formDateTo.compareTo(formattedDate) > 0 &&
                            formDateFrom.compareTo(formattedDate) <= 0;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
