/*
 * Tree.java
 *
 * Created on 11 de julio de 2005, 10:23
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lib;

import java.util.*;

/**
 * Implements a generic Tree. Each node can be any Object derived class.
 * @author ismael.flores
 */
public class Tree<V> 
{
    /**
     * Node contens.
     */
    private V value;
    
    /**
     * Reference to parent node. Null if this node is root.
     */
    private Tree<V> parent;
    
    /**
     * List of tree branches. Left to right ordered.
     */
    private List<Tree<V>> branches = new ArrayList<Tree<V>>();

    /**
     * Creates a new instance of Tree with no branches and no contens (root empty leaf).
     */
    public Tree() {
        value = null;
        parent = null;
    }
    
    /**
     * Creates a new instance of Tree with defined contens and no branches (root with contens).
     * @param v Contens of root node.
     */
    public Tree(V v) {
        value = v;
        parent = null;
    }

    /**
     * Creates a new instance of Tree with defined contens and parent and no branches. This is not root.
     * @param p Parent node (this node is not root).
     * @param v Node contens.
     */
    public Tree(V v, Tree<V> p) {
        value = v;
        parent = p;
    }
    
    /**
     * Obtains node contens.
     * @return Node contens (generic type).
     */
    public V getValue() {
        return value;
    }
    
    /**
     * Stores contens for this node.
     * @param v Contens (generic type).
     */
    public void setValue(V v) {
        value = v;
    }
    
    /**
     * Obtains parent of this node. Null it this is a root node.
     * @return Parent node (Tree<V> type).
     */
    public Tree<V> getParent() {
        return parent;
    }
    
    /**
     * Stores the parent of this node (Node is not root).
     * @param p Parent for this node (Tree<V> type).
     */
    public void setParent(Tree<V> p) {
        parent = p;
    }

    /**
     * Obtains the 'i' sub-tree.
     * @param i Index (from left to right) of sub-tree to be returned.
     * @return The sub-tree. Null if index 'i' is out of range.
     */
    public Tree<V> getBranch(int i) {
        return (i < 0 || i >= branches.size()) ? null : branches.get(i);
    }

    public Tree<V> getBranch(V v) {
        for (Tree<V> b : branches) {
            if (b.getValue() == v)
                return b;
        }
        return null;
    }

    public int getBranchIndex(Tree<V> b) {
        return branches.indexOf(b);
    }

    public void move(Tree<V> originBranch, Tree<V> targetBranch) {
        if (targetBranch == originBranch)
            return;
        int originIndex = branches.indexOf(originBranch);
        int targetIndex = branches.indexOf(targetBranch);
        if (targetIndex == -1)
            targetIndex = branches.size();
        branches.add(targetIndex, originBranch);
        if (originIndex < targetIndex)
            branches.remove(originIndex);
        else if (originIndex > targetIndex)
            branches.remove(originIndex + 1);
    }
    
    /**
     * Obtains level in tree
     * @return The level. 0 means that is root.
     */
    public int getLevel() {
        if (parent != null)
            return 1 + parent.getLevel();
        return 0;
    }
    
    /**
     * Add right sub-tree of this node (new right child).
     * @param branch New right child (sub-tree node).
     */
    public void addBranch(Tree<V> branch) {
        if (branch.getParent() == null)
            branch.setParent(this);
        branches.add(branch);
    }

    /**
     * Remove all sub-trees (childs) od this node.
     */
    public void removeAllBranches() {
        branches.clear();
    }

    /**
     * Removes 'i' child or sub-tree from this node ('i'-left child).
     * @param i Left-index child of node.
     * @return False if index is out of range. Treu elsewhere.
     */
    public Tree<V> removeBranch(int i) {
        return (i < 0 || i >= branches.size()) ? null : branches.remove(i);
    }
    
    /**
     * Returns if this node is a root node.
     * @return True if this node has not parent. False elsewhere.
     */
    public boolean isRoot() {
        return parent == null;
    }
    
    /**
     * Returns if node is a Leaf.
     * @return True if node has no childs (is a leaf). False elsewhere.
     */
    public boolean isLeaf() {
        return branches.size() == 0;
    }
    
    /**
     * Obtains the number of sub-trees (childs) for this node.
     * @return Number of childs (sub-trees) for this node.
     */
    public int getNumberOfBranches() {
        return branches.size();
    }
    
}
