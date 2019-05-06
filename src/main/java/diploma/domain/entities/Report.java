package diploma.domain.entities;

import diploma.domain.enums.TemperatureMode;
import diploma.domain.handler.PumpDataHandler;
import diploma.domain.util.JSONUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public Report() {
    }

    public Report(int minT, int maxT, int temp, Pump pump) {
        this.pump = pump;
        this.temperatureMode = TemperatureMode.getModeByTemp(temp);
        buildReport(minT, maxT);
    }

    private void buildReport(int minT, int maxT) {
        for (int t = minT; t <= maxT; t++) {
            Double heatProductivityCoef = PumpDataHandler.getNormalizedHeatProductivity(t, temperatureMode);
            Double powerConsumptionCoef = PumpDataHandler.getNormalizedPowerConsumption(t, temperatureMode);
            double powerConsumption = powerConsumptionCoef * pump.getPowerConsumption();
            double heatProductivity = heatProductivityCoef * pump.getHeatProductivity();
            double heatLoses = PumpDataHandler.getHeatLoss(t);
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
            this.tempRate.add(t);
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
}
