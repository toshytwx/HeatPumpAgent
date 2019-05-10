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
            XLSXUtil.readXLSXTo(hourTemperature, "C:\\Users\\dmytro.antonkin\\Univ\\gs-serving-web-content\\complete\\src\\main\\resources\\data\\temperature.xlsx");
        } catch (IOException | InvalidFormatException e) {
            logger.error(e.getMessage());
        }
    }

    private TemperatureData() {
    }

    public static Map<FormattedDate, Integer> getTemperatureForPeriod(final String dateFrom, final String dateTo) {
        Map<FormattedDate, Integer> collect = new TreeMap<>(hourTemperature).entrySet().stream()
                .filter((Map.Entry<FormattedDate, Integer> x) -> {
                    FormattedDate formattedDate = x.getKey();
                    return new FormattedDate(dateTo).compareTo(formattedDate) > 0 &&
                            new FormattedDate(dateFrom).compareTo(formattedDate) <= 0;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

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
}
