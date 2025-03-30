public class DecisionModule {
    private final double threshold;
    
    public DecisionModule(double threshold) {
        this.threshold = threshold;
    }
    
    public DecisionModule() {
        this(50.0); // Default threshold
    }
    
    public Decision makeDecision(PerceivedData data) {
        String action;
        double confidence = 0.5 + (Math.abs(data.getPerceivedValue() - threshold) / 100.0);
        
        // Cap confidence at 1.0
        confidence = Math.min(confidence, 1.0);
        
        if (data.getPerceivedValue() > threshold) {
            action = "Buy";
        } else {
            action = "Ignore";
        }
        
        return new Decision(action, confidence, data);
    }
}
