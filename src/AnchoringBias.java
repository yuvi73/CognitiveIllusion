public class AnchoringBias implements CognitiveBias{
    @Override
    public PerceivedData applyBias(Stimulus stimulus) {
        double perceivedValue = stimulus.getValue();
        boolean distorted = false;
        
        if (stimulus.getContext().equals("high_anchor")) {
            perceivedValue *= 1.5;
            distorted = true;
        } else if (stimulus.getContext().equals("low_anchor")) {
            perceivedValue *= 0.5;
            distorted = true;
        }
        
        return new PerceivedData(
            perceivedValue, 
            stimulus.getContext(), 
            stimulus.getType(),
            distorted,
            distorted ? "Anchoring Bias" : "None"
        );
    }
    
    @Override
    public String getName() {
        return "Anchoring Bias";
    }
}
