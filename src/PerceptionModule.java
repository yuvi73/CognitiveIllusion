public class PerceptionModule{
    private final java.util.List<CognitiveBias> biases;
    private final java.util.Random random;
    
    public PerceptionModule() {
        this.biases = new java.util.ArrayList<>();
        this.random = new java.util.Random();
    }
    
    public void addBias(CognitiveBias bias) {
        biases.add(bias);
    }
    
    public PerceivedData interpret(Stimulus stimulus) {
        // If no biases registered, return undistorted perception
        if (biases.isEmpty()) {
            return new PerceivedData(
                stimulus.getValue(),
                stimulus.getContext(),
                stimulus.getType(),
                false,
                "None"
            );
        }
        
        // Randomly select one bias to apply (simulating how different biases may affect perception)
        int biasIndex = random.nextInt(biases.size());
        CognitiveBias selectedBias = biases.get(biasIndex);
        
        // Apply the selected bias
        return selectedBias.applyBias(stimulus);
    }
}