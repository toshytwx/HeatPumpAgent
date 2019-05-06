package diploma.domain.enums;

public enum EnergyEfficiency {
    NONE, AAA, AA, A;

    public static String valueAsString(EnergyEfficiency energyEfficiency) {
        switch (energyEfficiency) {
            case NONE:
                return "-";
            case AAA:
                return "A+++";
            case AA:
                return "A+";
            case A:
                return "A";
        }
        return "-";
    }
}
