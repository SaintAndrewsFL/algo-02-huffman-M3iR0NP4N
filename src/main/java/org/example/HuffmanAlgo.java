package org.example;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HuffmanAlgo {


    public static Result encode(String text) {
        Map<Character, Integer> frequencyMap = buildFrequencyMap(text);
        HuffmanTree tree = buildHuffmanTree(frequencyMap);
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodes(tree.getRoot(), "", huffmanCodes);

        System.out.println("Character Encoding Table: ");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) System.out.println(entry.getKey() + ": " + entry.getValue() + "(Frequency: " + frequencyMap.get(entry.getKey()) + ")");
        StringBuilder encodedString = new StringBuilder();
        for (char ch : text.toCharArray()) encodedString.append(huffmanCodes.get(ch));

        System.out.println("Encoded String: " + encodedString);
        System.out.println("BFS of Huffman Tree: ");
        tree.bfsPrint();

        return new Result(tree, encodedString.toString());
    }

    public static String decode(HuffmanTree tree, String encodedText) {
        StringBuilder decodedString = new StringBuilder();
        HuffmanTree.Node current = tree.getRoot();

        for (char bit : encodedText.toCharArray()) {
            current = (bit == '0') ? current.getLeftChild() : current.getRightChild();
            if (current.getVal() != null) {
                decodedString.append(current.getVal());
                current = tree.getRoot();
            }
        }
        return decodedString.toString();
    }

    private static Map<Character, Integer> buildFrequencyMap(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : text.toCharArray()) frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        return frequencyMap;
    }

    private static HuffmanTree buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanTree.Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(HuffmanTree.Node::getFrequency));
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) priorityQueue.add(new HuffmanTree().new Node(entry.getValue(), entry.getKey()));
        while (priorityQueue.size() > 1) {
            HuffmanTree.Node left = priorityQueue.poll();
            HuffmanTree.Node right = priorityQueue.poll();
            HuffmanTree.Node parent = new HuffmanTree().new Node(left, right);
            priorityQueue.add(parent);
        }

        HuffmanTree tree = new HuffmanTree();
        tree.setRoot(priorityQueue.poll());
        return tree;
    }

    private static void generateHuffmanCodes(HuffmanTree.Node node, String code, Map<Character, String> huffmanCodes) {
        if (node.getLeftChild() == null && node.getRightChild() == null) {
            huffmanCodes.put(node.getVal(), code);
            return;
        }
        generateHuffmanCodes(node.getLeftChild(), code + "0", huffmanCodes);
        generateHuffmanCodes(node.getRightChild(), code + "1", huffmanCodes);
    }

    public String readText(String textFile) {
        StringBuilder content = new StringBuilder();
        Path path = Paths.get(System.getProperty("user.home"), "Desktop", textFile);

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) content.append(line).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return content.toString().trim();
    }

    public boolean writeToFile(String text, String fileLocation) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocation))) {
            bw.write(text);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public static class Result {
        private HuffmanTree tree;
        private String encodedText;

        public Result(HuffmanTree tree, String encodedText) {
            this.tree = tree;
            this.encodedText = encodedText;
        }
        public HuffmanTree getTree() { return tree; }
        public String getEncodedText() { return encodedText; }
    }
}
