public class ConfirmationBias implements CognitiveBias {
        private final String existingBelief;
        
        public ConfirmationBias(String existingBelief) {
            this.existingBelief = existingBelief;
        }
        
        @Override
        public PerceivedData applyBias(Stimulus stimulus) {
            double perceivedValue = stimulus.getValue();
            boolean distorted = false;
            
            // If the stimulus type matches the agent's existing belief, enhance its perceived value
            if (stimulus.getType().equals(existingBelief)) {
                perceivedValue *= 1.8; // Significant boost to confirming information
                distorted = true;
            } else {
                perceivedValue *= 0.6; // Discount contradicting information
                distorted = true;
            }
            
            return new PerceivedData(
                perceivedValue,
                stimulus.getContext(),
                stimulus.getType(),
                distorted,
                distorted ? "Confirmation Bias" : "None"
            );
        }
        
        @Override
        public String getName() {
            return "Confirmation Bias";
        }
}
