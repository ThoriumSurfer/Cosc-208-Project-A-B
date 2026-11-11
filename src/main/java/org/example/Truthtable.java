package org.example;

import java.util.*;

/** Builds and prints the truth table for any expression tree. */
public class TruthTable {
    
    /** Prints every combination of the rule's propositions (2^n rows) with the result. */
    public static void print(String ruleName, LogicNode rule) {
        List<String> vars = new ArrayList<>(rule.variables());
        int n = vars.size();
        
        System.out.println("\nTruth table for " + ruleName + " = " + rule);
        
        StringBuilder header = new StringBuilder();
        for (String v : vars) header.append(String.format("%-9s", v));
        header.append("Result");
        System.out.println(header);
        System.out.println("-".repeat(header.length()));
        
        for (int row = 0; row < (1 << n); row++) {
            Map<String, Boolean> env = new HashMap<>();
            StringBuilder line = new StringBuilder();
            
            for (int i = 0; i < n; i++) {
                // first row = all true, last row = all false; first variable changes slowest
                boolean value = ((row >> (n - 1 - i)) & 1) == 0;
                env.put(vars.get(i), value);
                line.append(String.format("%-9s", value ? "T" : "F"));
            }
            
            boolean result = rule.eval(env);
            line.append(result ? "T" : "F");
            
            List<String> failed = rule.failedPropositions(env);
            if (!result && !failed.isEmpty()) {
                line.append("   <- fails because: ").append(String.join(", ", failed)).append(" is false");
            }
            System.out.println(line);
        }
    }
}