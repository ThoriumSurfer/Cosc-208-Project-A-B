package org.example;

import java.util.*;

/**
 * One node of a propositional-logic expression tree.
 * Leaves (VAR) are propositions like "T" or "F"; internal nodes are AND / OR / NOT.
 * The SAME tree is used for evaluation, traversals, truth tables and explanations.
 */
public class LogicNode {
    
    public enum Op { VAR, AND, OR, NOT }
    
    private final Op op;
    
    private final String name;
    // only used when op == VAR
    private final LogicNode left;
    // NOT uses only 'left'
    private final LogicNode right;
    
    private LogicNode(Op op, String name, LogicNode left, LogicNode right) {
        this.op = op;
        this.name = name;
        this.left = left;
        this.right = right;
    }
    
    // ---------- Builders ----------
    public static LogicNode var(String name) { return new LogicNode(Op.VAR, name, null, null); }
    
    public static LogicNode not(LogicNode child)  { return new LogicNode(Op.NOT, null, child, null); }
    
    public static LogicNode and(LogicNode l, LogicNode r) { return new LogicNode(Op.AND, null, l, r); }
    
    public static LogicNode or(LogicNode l, LogicNode r)  { return new LogicNode(Op.OR, null, l, r); }
    
    /** a ∧ b ∧ c ∧ ... built left-associatively: ((a ∧ b) ∧ c) ∧ ... */
    public static LogicNode allOf(LogicNode first, LogicNode... rest) {
        
        LogicNode result = first;
        
        for (LogicNode n : rest) {
            
            result = and(result, n);
            
        }
        
        return result;
    }
    
    // ---------- Evaluation ----------
    public boolean eval(Map<String, Boolean> env) {
        switch (op) {
            case VAR: {
                
                Boolean value = env.get(name);
                
                if (value == null) {
                    
                    throw new IllegalArgumentException("No value supplied for proposition " + name);
                    
                }
                return value;
            }
            case NOT: return !left.eval(env);
            case AND: return left.eval(env) && right.eval(env);
            default:  return left.eval(env) || right.eval(env);
        }
    }
    
    // ---------- Traversals ----------
    public List<String> preorder() {
        List<String> out = new ArrayList<>();
        
        preorder(out);
        
        return out;
    }
    
    private void preorder(List<String> out) {
        out.add(label());
        
        if (left != null)  left.preorder(out);
        
        if (right != null) right.preorder(out);
    }
    
    /** Left, node, right. NOT has one child, so it is written before it: ¬ x. */
    public List<String> inorder() {
        List<String> out = new ArrayList<>();
        
        inorder(out);
        
        return out;
    }
    
    private void inorder(List<String> out) {
        if (op == Op.NOT) {
            out.add(label());
            
            left.inorder(out);
            
            return;
        }
        if (left != null)  left.inorder(out);
        
        out.add(label());
        
        if (right != null) right.inorder(out);
    }
    
    public List<String> postorder() {
        
        List<String> out = new ArrayList<>();
        
        postorder(out);
        
        return out;
    }
    
    private void postorder(List<String> out) {
        
        if (left != null)  left.postorder(out);
        
        if (right != null) right.postorder(out);
        
        out.add(label());
    }
    
    // ---------- Introspection (feeds truth table / explanations) ----------
    /** All proposition names in this tree, in order of first appearance. */
    public Set<String> variables() {
        
        Set<String> out = new LinkedHashSet<>();
       
        collectVariables(out);
        
        return out;
    }
    
    private void collectVariables(Set<String> out) {
        
        if (op == Op.VAR) out.add(name);
        
        if (left != null)  left.collectVariables(out);
        
        if (right != null) right.collectVariables(out);
    }
    
    /**
     * For a rule that is a pure chain of ANDs, returns which propositions were false.
     * That is exactly the "why it failed" list. (OR / NOT nodes are skipped on purpose;
     * explaining those needs a richer approach.)
     */
    public List<String> failedPropositions(Map<String, Boolean> env) {
        
        List<String> out = new ArrayList<>();
        
        collectFailed(env, out);
        
        return out;
    }
    
    private void collectFailed(Map<String, Boolean> env, List<String> out) {
        if (op == Op.VAR) {
            if (!env.get(name)) out.add(name);
        } else if (op == Op.AND) {
            left.collectFailed(env, out);
            
            right.collectFailed(env, out);
            
        }
    }
    
    public String label() {
        switch (op) {
            case VAR: return name;
            case NOT: return "NOT";
            case AND: return "AND";
            default:  return "OR";
        }
    }
    
    /** Fully parenthesised, e.g. ((T AND F) AND D). */
    @Override
    public String toString() {
        switch (op) {
            case VAR: return name;
            case NOT: return "NOT " + left;
            case AND: return "(" + left + " AND " + right + ")";
            default:  return "(" + left + " OR " + right + ")";
        }
    }
}