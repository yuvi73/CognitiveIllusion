import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application class for the Cognitive Illusion Simulator
 */
public class CognitiveIllusionSimulatorGUI extends JFrame {
    private CognitiveAgent agent;
    private final JComboBox<String> biasSelector;
    private final JTextField stimulusValueField;
    private final JComboBox<String> contextSelector;
    private final JTextField typeField;
    private final JTextArea resultArea;
    private final JTextArea historyArea;
    private final JPanel visualizationPanel;
    
    // For Availability Bias
    private final JPanel availabilityPanel;
    private final JTextField experienceField;
    private final DefaultListModel<String> experiencesListModel;
    
    // For Confirmation Bias
    private final JPanel confirmationPanel;
    private final JTextField beliefField;
    
    public CognitiveIllusionSimulatorGUI() {
        // Initialize the agent
        agent = new CognitiveAgent("UI Agent");
        
        // Set up the frame
        setTitle("Cognitive Illusion Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create main panels
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        JPanel simulationPanel = new JPanel(new BorderLayout(10, 10));
        
        // === CONTROL PANEL ===
        JPanel biasPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        biasPanel.setBorder(new TitledBorder("Cognitive Bias Settings"));
        
        // Bias selector
        biasPanel.add(new JLabel("Active Bias:"));
        String[] biasOptions = {"None", "Anchoring Bias", "Confirmation Bias", "Availability Bias"};
        biasSelector = new JComboBox<>(biasOptions);
        biasPanel.add(biasSelector);
        
        // Availability Bias Panel
        availabilityPanel = new JPanel(new BorderLayout(5, 5));
        availabilityPanel.setBorder(new TitledBorder("Availability Bias Settings"));
        
        JPanel experienceInputPanel = new JPanel(new BorderLayout(5, 5));
        experienceField = new JTextField(10);
        JButton addExperienceButton = new JButton("Add Recent Experience");
        experienceInputPanel.add(experienceField, BorderLayout.CENTER);
        experienceInputPanel.add(addExperienceButton, BorderLayout.EAST);
        
        experiencesListModel = new DefaultListModel<>();
        JList<String> experiencesList = new JList<>(experiencesListModel);
        JScrollPane experiencesScrollPane = new JScrollPane(experiencesList);
        experiencesScrollPane.setPreferredSize(new Dimension(200, 100));
        
        availabilityPanel.add(experienceInputPanel, BorderLayout.NORTH);
        availabilityPanel.add(experiencesScrollPane, BorderLayout.CENTER);
        availabilityPanel.setVisible(false);
        
        // Confirmation Bias Panel  
        confirmationPanel = new JPanel(new BorderLayout(5, 5));
        confirmationPanel.setBorder(new TitledBorder("Confirmation Bias Settings"));
        
        JPanel beliefPanel = new JPanel(new BorderLayout(5, 5));
        beliefPanel.add(new JLabel("Existing Belief:"), BorderLayout.WEST);
        beliefField = new JTextField("tech");
        beliefPanel.add(beliefField, BorderLayout.CENTER);
        
        JButton setBeliefButton = new JButton("Set Belief");
        beliefPanel.add(setBeliefButton, BorderLayout.EAST);
        
        confirmationPanel.add(beliefPanel, BorderLayout.NORTH);
        confirmationPanel.setVisible(false);
        
        // Stimulus panel
        JPanel stimulusPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        stimulusPanel.setBorder(new TitledBorder("Stimulus Configuration"));
        
        stimulusPanel.add(new JLabel("Value:"));
        stimulusValueField = new JTextField("50");
        stimulusPanel.add(stimulusValueField);
        
        stimulusPanel.add(new JLabel("Context:"));
        String[] contextOptions = {"neutral", "high_anchor", "low_anchor", "news", "market"};
        contextSelector = new JComboBox<>(contextOptions);
        stimulusPanel.add(contextSelector);
        
        stimulusPanel.add(new JLabel("Type:"));
        typeField = new JTextField("investment");
        stimulusPanel.add(typeField);
        
        // Action panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton simulateButton = new JButton("Run Simulation");
        JButton resetButton = new JButton("Reset Agent");
        actionPanel.add(simulateButton);
        actionPanel.add(resetButton);
        
        // Assemble control panel
        controlPanel.add(biasPanel, BorderLayout.NORTH);
        JPanel dynamicBiasPanel = new JPanel(new BorderLayout());
        dynamicBiasPanel.add(availabilityPanel, BorderLayout.NORTH);
        dynamicBiasPanel.add(confirmationPanel, BorderLayout.CENTER);
        controlPanel.add(dynamicBiasPanel, BorderLayout.CENTER);
        
        JPanel bottomControlPanel = new JPanel(new BorderLayout());
        bottomControlPanel.add(stimulusPanel, BorderLayout.CENTER);
        bottomControlPanel.add(actionPanel, BorderLayout.SOUTH);
        controlPanel.add(bottomControlPanel, BorderLayout.SOUTH);
        
        // === SIMULATION PANEL ===
        // Results area
        resultArea = new JTextArea(8, 40);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setBorder(new TitledBorder("Current Simulation Result"));
        
        // History area
        historyArea = new JTextArea(8, 40);
        historyArea.setEditable(false);
        JScrollPane historyScrollPane = new JScrollPane(historyArea);
        historyScrollPane.setBorder(new TitledBorder("Decision History"));
        
        // Visualization panel
        visualizationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawVisualization((Graphics2D) g);
            }
        };
        visualizationPanel.setBorder(new TitledBorder("Visualization"));
        visualizationPanel.setPreferredSize(new Dimension(400, 200));
        
        // Assemble simulation panel
        simulationPanel.add(resultScrollPane, BorderLayout.NORTH);
        simulationPanel.add(historyScrollPane, BorderLayout.CENTER);
        simulationPanel.add(visualizationPanel, BorderLayout.SOUTH);
        
        // Add panels to main frame
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, simulationPanel);
        splitPane.setDividerLocation(350);
        add(splitPane, BorderLayout.CENTER);
        
        // === EVENT HANDLERS ===
        // Bias selector change event
        biasSelector.addActionListener(e -> {
            String selectedBias = (String) biasSelector.getSelectedItem();
            availabilityPanel.setVisible("Availability Bias".equals(selectedBias));
            confirmationPanel.setVisible("Confirmation Bias".equals(selectedBias));
        });
        
        // Add experience button
        addExperienceButton.addActionListener(e -> {
            String experience = experienceField.getText().trim();
            if (!experience.isEmpty() && !experiencesListModel.contains(experience)) {
                experiencesListModel.addElement(experience);
                experienceField.setText("");
            }
        });
        
        // Set belief button
        setBeliefButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Belief set to: " + beliefField.getText());
        });
        
        // Simulate button
        simulateButton.addActionListener(e -> runSimulation());
        
        // Reset button
        resetButton.addActionListener(e -> {
            agent = new CognitiveAgent("UI Agent");
            historyArea.setText("");
            resultArea.setText("Agent reset successfully.");
            experiencesListModel.clear();
            repaint();
        });
    }
    
    private void runSimulation() {
        try {
            // Create the appropriate bias based on selection
            String selectedBias = (String) biasSelector.getSelectedItem();
            CognitiveBias bias = null;
            
            switch (selectedBias) {
                case "Anchoring Bias":
                    bias = new AnchoringBias();
                    break;
                case "Confirmation Bias":
                    bias = new ConfirmationBias(beliefField.getText().trim());
                    break;
                case "Availability Bias":
                    AvailabilityBias availabilityBias = new AvailabilityBias();
                    for (int i = 0; i < experiencesListModel.size(); i++) {
                        availabilityBias.addRecentExperience(experiencesListModel.get(i));
                    }
                    bias = availabilityBias;
                    break;
            }
            
            // Reset agent's biases and add the selected one if not "None"
            agent = new CognitiveAgent("UI Agent");
            if (bias != null) {
                agent.addBias(bias);
            }
            
            // Create and process stimulus
            double value = Double.parseDouble(stimulusValueField.getText());
            String context = (String) contextSelector.getSelectedItem();
            String type = typeField.getText().trim();
            
            Stimulus stimulus = new Stimulus(value, context, type);
            Decision decision = agent.processStimulus(stimulus);
            
            // Display result
            resultArea.setText("");
            resultArea.append("STIMULUS:\n");
            resultArea.append("Value: " + value + ", Context: " + context + ", Type: " + type + "\n\n");
            resultArea.append("PERCEIVED DATA:\n");
            resultArea.append("Perceived Value: " + decision.getBasedOn().getPerceivedValue() + "\n");
            resultArea.append("Distorted: " + decision.getBasedOn().isDistorted() + "\n");
            resultArea.append("Applied Bias: " + decision.getBasedOn().getAppliedBias() + "\n\n");
            resultArea.append("DECISION:\n");
            resultArea.append("Action: " + decision.getAction() + "\n");
            resultArea.append("Confidence: " + new DecimalFormat("0.00").format(decision.getConfidence() * 100) + "%\n");
            
            // Update history
            updateHistory();
            
            // Refresh visualization
            visualizationPanel.repaint();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid number for the stimulus value.", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "An error occurred: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateHistory() {
        List<Decision> history = agent.getDecisionHistory();
        historyArea.setText("");
        
        for (int i = 0; i < history.size(); i++) {
            Decision decision = history.get(i);
            historyArea.append("Decision #" + (i + 1) + ":\n");
            historyArea.append("  Stimulus: " + decision.getBasedOn().getType() + 
                               " (Value: " + decision.getBasedOn().getPerceivedValue() + ")\n");
            historyArea.append("  Action: " + decision.getAction() + 
                               " (Confidence: " + new DecimalFormat("0.00").format(decision.getConfidence() * 100) + "%)\n");
            historyArea.append("  Bias Applied: " + decision.getBasedOn().getAppliedBias() + "\n\n");
        }
    }
    
    private void drawVisualization(Graphics2D g2d) {
        List<Decision> history = agent.getDecisionHistory();
        if (history.isEmpty()) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Arial", Font.ITALIC, 14));
            g2d.drawString("No decisions have been made yet.", 50, 100);
            return;
        }
        
        int width = visualizationPanel.getWidth();
        int height = visualizationPanel.getHeight();
        int barWidth = width / (history.size() * 2);
        
        // Draw coordinate system
        g2d.setColor(Color.BLACK);
        g2d.drawLine(40, height - 40, width - 20, height - 40); // X-axis
        g2d.drawLine(40, 20, 40, height - 40); // Y-axis
        
        // Draw labels
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.drawString("Perceived Value", 5, height / 2);
        g2d.drawString("Decision Number", width / 2, height - 20);
        
        // Draw scale on Y-axis
        g2d.drawString("100", 25, 25);
        g2d.drawString("50", 25, height / 2);
        g2d.drawString("0", 25, height - 45);
        
        // Draw data points
        for (int i = 0; i < history.size(); i++) {
            Decision decision = history.get(i);
            double value = decision.getBasedOn().getPerceivedValue();
            double rawValue = decision.getBasedOn().getPerceivedValue(); // Assuming original value before bias
            boolean distorted = decision.getBasedOn().isDistorted();
            
            // Calculate bar heights (scale to fit)
            int barHeight = (int) (value / 100.0 * (height - 60));
            barHeight = Math.min(barHeight, height - 60);
            
            // X position for this decision
            int x = 50 + (i * (barWidth * 2));
            
            // Draw bar for perceived value
            if (distorted) {
                g2d.setColor(new Color(255, 100, 100, 200)); // Reddish for distorted
            } else {
                g2d.setColor(new Color(100, 100, 255, 200)); // Bluish for undistorted
            }
            g2d.fillRect(x, height - 40 - barHeight, barWidth, barHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, height - 40 - barHeight, barWidth, barHeight);
            
            // Draw decision number
            g2d.drawString(String.valueOf(i + 1), x + barWidth / 2 - 3, height - 25);
            
            // Draw value at top of bar
            g2d.setFont(new Font("Arial", Font.BOLD, 9));
            g2d.drawString(new DecimalFormat("0.0").format(value), x, height - 45 - barHeight);
            
            // Draw confidence indicator
            int confidenceX = x + barWidth + 5;
            int confidenceHeight = (int) (decision.getConfidence() * (height - 60));
            g2d.setColor(new Color(50, 200, 50, 150)); // Greenish for confidence
            g2d.fillRect(confidenceX, height - 40 - confidenceHeight, barWidth / 2, confidenceHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(confidenceX, height - 40 - confidenceHeight, barWidth / 2, confidenceHeight);
        }
        
        // Draw legend
        int legendY = 40;
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));
        
        g2d.setColor(new Color(100, 100, 255, 200));
        g2d.fillRect(width - 150, legendY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(width - 150, legendY, 15, 15);
        g2d.drawString("Undistorted perception", width - 130, legendY + 12);
        
        legendY += 25;
        g2d.setColor(new Color(255, 100, 100, 200));
        g2d.fillRect(width - 150, legendY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(width - 150, legendY, 15, 15);
        g2d.drawString("Distorted perception", width - 130, legendY + 12);
        
        legendY += 25;
        g2d.setColor(new Color(50, 200, 50, 150));
        g2d.fillRect(width - 150, legendY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(width - 150, legendY, 15, 15);
        g2d.drawString("Decision confidence", width - 130, legendY + 12);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CognitiveIllusionSimulatorGUI simulator = new CognitiveIllusionSimulatorGUI();
            simulator.setVisible(true);
        });
    }
}