public class Stimulus {
    private double value;
    private String context;
    private String type;

    public Stimulus(double value, String context, String type)
    {
        this.value = value;
        this.context = context;
        this.type = type;
    }
    
    public double getValue() {
        return value;
    }

    public String getContext() {
        return context;
    }

    public String getType(){
        return type;
    }

    @Override
    public String toString() {
        return "Stimulus{value=" + value + ",context=" + context + ",type=" + type + "'}";
    }
}
