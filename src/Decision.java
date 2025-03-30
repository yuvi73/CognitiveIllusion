public class Decision {
    private final String action;
    private final double confidence;
    private final PerceivedData basedOn;
    
    public Decision(String action, double confidence, PerceivedData basedOn) {
        this.action = action;
        this.confidence = confidence;
        this.basedOn = basedOn;
    }
    
    public String getAction() {
        return action;
    }
    
    public double getConfidence() {
        return confidence;
    }
    
    public PerceivedData getBasedOn() {
        return basedOn;
    }
    
    @Override
    public String toString() {
        return "Decision{action='" + action + "', confidence=" + confidence + 
               ", basedOn=" + basedOn + "}";
    }
}
