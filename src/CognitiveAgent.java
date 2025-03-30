public class CognitiveAgent {
    private final String name;
    private final PerceptionModule perception;
    private final DecisionModule decision;
    private final java.util.List<Decision> decisionHistory;
    
    public CognitiveAgent(String name) {
        this.name = name;
        this.perception = new PerceptionModule();
        this.decision = new DecisionModule();
        this.decisionHistory = new java.util.ArrayList<>();
    }
    
    public void addBias(CognitiveBias bias) {
        perception.addBias(bias);
    }
    
    public Decision processStimulus(Stimulus stimulus) {
        PerceivedData data = perception.interpret(stimulus);
        Decision decision = this.decision.makeDecision(data);
        decisionHistory.add(decision);
        
        return decision;
    }
    
    public void printDecisionHistory() {
        System.out.println(name + "'s Decision History:");
        for (int i = 0; i < decisionHistory.size(); i++) {
            System.out.println((i + 1) + ". " + decisionHistory.get(i));
        }
    }
    
    public String getName() {
        return name;
    }
    
    public java.util.List<Decision> getDecisionHistory() {
        return new java.util.ArrayList<>(decisionHistory);
    }
}
