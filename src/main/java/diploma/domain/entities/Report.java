package diploma.domain.entities;

import diploma.domain.enums.TemperatureMode;
import diploma.domain.handler.PumpDataHandler;
import diploma.domain.util.JSONUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pump_id")
    private Pump pump;

    @Enumerated(EnumType.STRING)
    private TemperatureMode temperatureMode;

    @ElementCollection
    @CollectionTable(
            name = "Norm_Heat_Prod",
            joinColumns = @JoinColumn(name = "normHeatProd_id")
    )
    private List<Double> normalizedHeatProductivity = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "Coef_Heat_Prod",
            joinColumns = @JoinColumn(name = "coefHeatProd_id")
    )
    private List<Double> heatProductivityCoefficients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "Coef_Pow_Cons",
            joinColumns = @JoinColumn(name = "coefPowCons_id")
    )
    private List<Double> powerConsumptionCoefficients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "Norm_Pow_Cons",
            joinColumns = @JoinColumn(name = "normPowCons_id")
    )
    private List<Double> normalizedPowerConsumption = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "Heat_Loss",
            joinColumns = @JoinColumn(name = "heatLoss_id")
    )
    private List<Double> heatLoss = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "Heater_Prod",
            joinColumns = @JoinColumn(name = "heaterProd_id")
    )
    private List<Double> heaterProductivity = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "Temp_Rate",
            joinColumns = @JoinColumn(name = "tempRate_id")
    )
    private List<Integer> tempRate = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "Workload_Rate",
            joinColumns = @JoinColumn(name = "workload_id")
    )
    private List<Double> workloadRate = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "Work_Date",
            joinColumns = @JoinColumn(name = "workdate_id")
    )
    private List<String> workDate = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "Temp_Freq",
            joinColumns = @JoinColumn(name = "tempFreq_id")
    )
    private Map<Integer, Integer> tempFreq = new TreeMap<>();

    public Report() {
    }

    public Report(Map<FormattedDate, Integer> tempPeriod,
                  Map<Integer, Integer> tempFreq,
                  int temp, Pump pump, Double heatLoss) {
        this.pump = pump;
        this.tempFreq = tempFreq;
        this.workDate = tempPeriod.keySet()
                .stream()
                .map(FormattedDate::toString)
                .collect(Collectors.toList());
        this.temperatureMode = TemperatureMode.getModeByTemp(temp);
        buildReport(new ArrayList<>(tempPeriod.values()), heatLoss);
    }

    private void buildReport(List<Integer> tempRate, Double heatLoss) {
        PumpDataHandler.initValues(heatLoss);
        for (Integer temp : tempRate) {
            Double heatProductivityCoef = PumpDataHandler.getNormalizedHeatProductivity(temp, temperatureMode);
            Double powerConsumptionCoef = PumpDataHandler.getNormalizedPowerConsumption(temp, temperatureMode);
            double powerConsumption = powerConsumptionCoef * pump.getPowerConsumption();
            double heatProductivity = heatProductivityCoef * pump.getHeatProductivity();
            double heatLoses = PumpDataHandler.getHeatLoss(temp);
            double heaterProductivityParticularTemp = heatLoses - heatProductivity;
            double approaxWorkload;
            if (heaterProductivityParticularTemp > 0) {
                approaxWorkload = (heatLoses - heaterProductivityParticularTemp) / heatProductivity;
            } else {
                approaxWorkload = heatLoses / heatProductivity;
            }
            double workload = approaxWorkload > 1 ? 1 : approaxWorkload;
            this.powerConsumptionCoefficients.add(powerConsumptionCoef);
            this.heatProductivityCoefficients.add(heatProductivityCoef);
            this.heaterProductivity.add(heaterProductivityParticularTemp < 0 ? 0 : heaterProductivityParticularTemp);
            this.normalizedHeatProductivity.add(heatProductivity);
            this.normalizedPowerConsumption.add(powerConsumption);
            this.heatLoss.add(heatLoses);
            this.tempRate.add(temp);
            this.workloadRate.add(workload);
        }
    }

    public String toJSON() {
        return JSONUtil.convertPumpDataToJSON(tempRate,
                heatLoss,
                heatProductivityCoefficients,
                normalizedHeatProductivity,
                powerConsumptionCoefficients,
                normalizedPowerConsumption,
                heaterProductivity,
                workloadRate);
    }

    public Pump getPump() {
        return pump;
    }

    public void setPump(Pump pump) {
        this.pump = pump;
    }

    public TemperatureMode getTemperatureMode() {
        return temperatureMode;
    }

    public void setTemperatureMode(TemperatureMode temperatureMode) {
        this.temperatureMode = temperatureMode;
    }

    public List<Double> getNormalizedHeatProductivity() {
        return normalizedHeatProductivity;
    }

    public void setNormalizedHeatProductivity(List<Double> normalizedHeatProductivity) {
        this.normalizedHeatProductivity = normalizedHeatProductivity;
    }

    public List<Double> getHeatProductivityCoefficients() {
        return heatProductivityCoefficients;
    }

    public void setHeatProductivityCoefficients(List<Double> heatProductivityCoefficients) {
        this.heatProductivityCoefficients = heatProductivityCoefficients;
    }

    public List<Double> getPowerConsumptionCoefficients() {
        return powerConsumptionCoefficients;
    }

    public void setPowerConsumptionCoefficients(List<Double> powerConsumptionCoefficients) {
        this.powerConsumptionCoefficients = powerConsumptionCoefficients;
    }

    public List<Double> getHeatLoss() {
        return heatLoss;
    }

    public void setHeatLoss(List<Double> heatLoss) {
        this.heatLoss = heatLoss;
    }

    public List<Double> getHeaterProductivity() {
        return heaterProductivity;
    }

    public void setHeaterProductivity(List<Double> heaterProductivity) {
        this.heaterProductivity = heaterProductivity;
    }

    public List<Double> getNormalizedPowerConsumption() {
        return normalizedPowerConsumption;
    }

    public void setNormalizedPowerConsumption(List<Double> normalizedPowerConsumption) {
        this.normalizedPowerConsumption = normalizedPowerConsumption;
    }

    public List<Integer> getTempRate() {
        return tempRate;
    }

    public void setTempRate(List<Integer> tempRate) {
        this.tempRate = tempRate;
    }

    public List<Double> getWorkloadRate() {
        return workloadRate;
    }

    public void setWorkloadRate(List<Double> workloadRate) {
        this.workloadRate = workloadRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public List<String> getWorkDate() {
        return workDate;
    }

    public void setWorkDate(List<String> workDate) {
        this.workDate = workDate;
    }

    public Map<Integer, Integer> getTempFreq() {
        return tempFreq;
    }

    public void setTempFreq(Map<Integer, Integer> tempFreq) {
        this.tempFreq = tempFreq;
    }
}
