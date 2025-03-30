public class CognitiveIllusionStimulation {
    public static void main(String[] args) {
        runAnchoringBiasSimulation();
        System.out.println("\n" + "=".repeat(50) + "\n");
        runConfirmationBiasSimulation();
        System.out.println("\n" + "=".repeat(50) + "\n");
        runAvailabilityBiasSimulation();
    }
    
    private static void runAnchoringBiasSimulation() {
        System.out.println("ANCHORING BIAS SIMULATION");
        System.out.println("-------------------------");
        
        // Create an agent with anchoring bias
        CognitiveAgent agent = new CognitiveAgent("Anchoring Agent");
        agent.addBias(new AnchoringBias());
        
        // Create identical stimuli but with different anchoring contexts
        Stimulus neutralStimulus = new Stimulus(30, "neutral", "investment");
        Stimulus highAnchorStimulus = new Stimulus(30, "high_anchor", "investment");
        Stimulus lowAnchorStimulus = new Stimulus(30, "low_anchor", "investment");
        
        System.out.println("Processing identical stimuli with different anchoring contexts...");
        Decision neutralDecision = agent.processStimulus(neutralStimulus);
        Decision highAnchorDecision = agent.processStimulus(highAnchorStimulus);
        Decision lowAnchorDecision = agent.processStimulus(lowAnchorStimulus);
        
        System.out.println("Neutral context: " + neutralDecision);
        System.out.println("High anchor: " + highAnchorDecision);
        System.out.println("Low anchor: " + lowAnchorDecision);
    }
    
    private static void runConfirmationBiasSimulation() {
        System.out.println("CONFIRMATION BIAS SIMULATION");
        System.out.println("---------------------------");
        
        // Create an agent with confirmation bias (believes in "tech" investments)
        CognitiveAgent agent = new CognitiveAgent("Confirmation Bias Agent");
        agent.addBias(new ConfirmationBias("tech"));
        
        // Create stimuli with identical values but different types
        Stimulus techStimulus = new Stimulus(40, "news", "tech");
        Stimulus financeStimulus = new Stimulus(40, "news", "finance");
        Stimulus healthStimulus = new Stimulus(40, "news", "health");
        
        System.out.println("Processing identical stimuli with different types...");
        Decision techDecision = agent.processStimulus(techStimulus);
        Decision financeDecision = agent.processStimulus(financeStimulus);
        Decision healthDecision = agent.processStimulus(healthStimulus);
        
        System.out.println("Tech investment: " + techDecision);
        System.out.println("Finance investment: " + financeDecision);
        System.out.println("Health investment: " + healthDecision);
    }
    
    private static void runAvailabilityBiasSimulation() {
        System.out.println("AVAILABILITY BIAS SIMULATION");
        System.out.println("---------------------------");
        
        // Create an agent with availability bias
        CognitiveAgent agent = new CognitiveAgent("Availability Bias Agent");
        AvailabilityBias availabilityBias = new AvailabilityBias();
        agent.addBias(availabilityBias);
        
        // First decision before any recent experiences
        Stimulus stockStimulus = new Stimulus(45, "market", "stock");
        Decision initialStockDecision = agent.processStimulus(stockStimulus);
        System.out.println("Initial stock decision: " + initialStockDecision);
        
        // Add "stock" as a recent experience
        availabilityBias.addRecentExperience("stock");
        System.out.println("Agent just experienced news about stocks...");
        
        // Same stimulus, but now with availability bias active
        Decision subsequentStockDecision = agent.processStimulus(stockStimulus);
        System.out.println("Subsequent stock decision: " + subsequentStockDecision);
        
        // Comparing with a different type
        Stimulus bondStimulus = new Stimulus(45, "market", "bond");
        Decision bondDecision = agent.processStimulus(bondStimulus);
        System.out.println("Bond decision: " + bondDecision);
    }
}
