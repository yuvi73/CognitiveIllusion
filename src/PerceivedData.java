public class PerceivedData {
    private double perceivedValue;
    private String context;
    private String type;
    private boolean distorted;
    private String appliedBias;

    public PerceivedData(double perceivedValue, String context, String type, boolean distorted, String appliedBias)
    {   
        this.perceivedValue = perceivedValue;
        this.context = context;
        this.type = type;
        this.distorted = distorted;
        this.appliedBias = appliedBias;
    }

    public double getPerceivedValue() {
        return perceivedValue;
    }

    public String getContext() {
        return context;
    }
    
    public String getType() {
        return type;
    }
    
    public boolean isDistorted() {
        return distorted;
    }
    
    public String getAppliedBias() {
        return appliedBias;
    }
    
    @Override
    public String toString() {
        return "PerceivedData{perceivedValue=" + perceivedValue + 
               ", context='" + context + "', type='" + type + 
               "', distorted=" + distorted + 
               ", bias='" + appliedBias + "'}";
    }
}

    
    

