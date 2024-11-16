package org.example;

import java.util.*;

public class HuffmanTree {


    public class Node {
       private Integer frequency;
       private Character val;
       private Node leftChild;
       private Node rightChild;

        public Node(Node leftChild, Node rightChild) {
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            frequency = leftChild.getFrequency() + rightChild.getFrequency();
            this.val = null;
        }

        public Node(Integer frequency, Character val) {
            this.frequency = frequency;
            this.val = val;
            this.leftChild = null;
            this.rightChild = null;
        }

        public Integer getFrequency() {
            return frequency;
        }
        public Character getVal() { return val; }
        public Node getLeftChild() { return leftChild; }
        public Node getRightChild() { return rightChild; }
    }
    private Node root;
    public Node getRoot() { return root; }
    public void setRoot(Node root) { this.root = root; }
    public void bfsPrint() {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Node node = queue.poll();
                System.out.print((node.getVal() != null ? node.getVal() : "*") + "(" + node.getFrequency() + ") ");
                if (node.getLeftChild() != null) queue.add(node.getLeftChild());
                if (node.getRightChild() != null) queue.add(node.getRightChild());
            }
            System.out.println();
        }
    }
}

