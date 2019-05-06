package diploma.domain.entities;

import diploma.domain.enums.EnergyEfficiency;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Pump {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String company;

    @Column
    private Integer phase;

    @Column
    private Double heatProductivity;

    @Column
    private Double powerConsumption;

    @Column
    private Double COP;

    @Column
    @Enumerated(EnumType.STRING)
    private EnergyEfficiency energyEfficiency;

    public Pump() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Double getHeatProductivity() {
        return heatProductivity;
    }

    public void setHeatProductivity(Double heatProductivity) {
        this.heatProductivity = heatProductivity;
    }

    public Double getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(Double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public Double getCOP() {
        return COP;
    }

    public void setCOP(Double COP) {
        this.COP = COP;
    }

    public EnergyEfficiency getEnergyEfficiency() {
        return energyEfficiency;
    }

    public String getEnergyEfficiencyAsString() {
        return EnergyEfficiency.valueAsString(energyEfficiency);
    }

    public void setEnergyEfficiency(EnergyEfficiency energyEfficiency) {
        this.energyEfficiency = energyEfficiency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pump)) return false;
        Pump pump = (Pump) o;
        return id.equals(pump.id) &&
                name.equals(pump.name) &&
                company.equals(pump.company) &&
                phase.equals(pump.phase) &&
                heatProductivity.equals(pump.heatProductivity) &&
                powerConsumption.equals(pump.powerConsumption) &&
                COP.equals(pump.COP) &&
                energyEfficiency == pump.energyEfficiency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, company, phase, heatProductivity, powerConsumption, COP, energyEfficiency);
    }

    @Override
    public String toString() {
        return "Pump{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", phase=" + phase +
                ", heatProductivity=" + heatProductivity +
                ", powerConsumption=" + powerConsumption +
                ", COP=" + COP +
                ", energyEfficiency=" + energyEfficiency +
                '}';
    }
}
