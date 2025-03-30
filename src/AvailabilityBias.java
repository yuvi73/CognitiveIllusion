public class AvailabilityBias implements CognitiveBias{
    private final java.util.Set<String> recentExperiences;
    
    public AvailabilityBias() {
        this.recentExperiences = new java.util.HashSet<>();
    }
    
    public void addRecentExperience(String experience) {
        recentExperiences.add(experience);
    }
    
    @Override
    public PerceivedData applyBias(Stimulus stimulus) {
        double perceivedValue = stimulus.getValue();
        boolean distorted = false;
        
        // If the stimulus type is something recently experienced, it seems more significant
        if (recentExperiences.contains(stimulus.getType())) {
            perceivedValue *= 2.0; // Doubled perception if recently experienced
            distorted = true;
        }
        
        return new PerceivedData(
            perceivedValue,
            stimulus.getContext(),
            stimulus.getType(),
            distorted,
            distorted ? "Availability Bias" : "None"
        );
    }
    
    @Override
    public String getName() {
        return "Availability Bias";
    }
    
}
