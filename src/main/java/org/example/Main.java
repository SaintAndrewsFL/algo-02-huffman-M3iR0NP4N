package org.example;

public class Main {
    public static void main(String[] args) {
        String text = "this is an example of a huffman tree. let me tell you a song of my people";
        HuffmanAlgo.Result result = HuffmanAlgo.encode(text);

        String decodedText = HuffmanAlgo.decode(result.getTree(), "00000");
        System.out.println("Decoded String: " + decodedText);
        System.out.println("?");
    }
}