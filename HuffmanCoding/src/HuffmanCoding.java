/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tien Thinh
 */
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class HuffmanCoding {

    public Map<Character, String> charPrefixHashMap = new HashMap<>();
    HuffmanNode root;

    public HuffmanNode buildTree(Map<Character, Integer> frequency) {
        //Build huffman tree: 2 smallest frequence = new node -> add to queue -> loop until queue empty
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        Set<Character> keySet = frequency.keySet();
        for (Character c : keySet) {

            HuffmanNode huffmanNode = new HuffmanNode();
            huffmanNode.data = c;
            huffmanNode.frequency = frequency.get(c);
            huffmanNode.left = null;
            huffmanNode.right = null;
            priorityQueue.offer(huffmanNode);
        }

        while (priorityQueue.size() > 1) {

            HuffmanNode x = priorityQueue.poll();

            HuffmanNode y = priorityQueue.poll();

            HuffmanNode sum = new HuffmanNode();

            sum.frequency = x.frequency + y.frequency;
            sum.data = '-';

            sum.left = x;

            sum.right = y;
            root = sum;

            priorityQueue.offer(sum);

        }
        return priorityQueue.poll();
    }

    public void setPrefixCodes(HuffmanNode node, StringBuilder prefix) {
        //Set prefix code for each character. If in the left node then add 0 else add 1
        if (node != null) {
            if (node.left == null && node.right == null) {
                charPrefixHashMap.put(node.data, prefix.toString());

            } else {
                prefix.append('0');
                setPrefixCodes(node.left, prefix);
                prefix.deleteCharAt(prefix.length() - 1);

                prefix.append('1');
                setPrefixCodes(node.right, prefix);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }

    }

    public String encode(String test) {
        Map<Character, Integer> frequency = new HashMap<>();
        //Map with char is key, frequency is value
        for (int i = 0; i < test.length(); i++) {
            if (!frequency.containsKey(test.charAt(i))) {
                frequency.put(test.charAt(i), 1);
            } else {
                frequency.put(test.charAt(i), frequency.get(test.charAt(i)) + 1);
            }
        }
        //Build huffman tree and setprefix. Replace each char into a new string
        //System.out.println("Character Frequency Map = " + frequency);
        root = buildTree(frequency);

        setPrefixCodes(root, new StringBuilder());
        System.out.println("Character Prefix Map = " + charPrefixHashMap);
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < test.length(); i++) {
            char c = test.charAt(i);
            s.append(charPrefixHashMap.get(c));
        }

        System.out.println("Encoded: " + s.toString());
        return s.toString();
    }

    public String decode(String s) {

        StringBuilder stringBuilder = new StringBuilder();

        HuffmanNode temp = root;
        //loop until find a matched character leaf then replace. 
        for (int i = 0; i < s.length(); i++) {

            int j = Integer.parseInt(String.valueOf(s.charAt(i)));

            if (j == 0) {
                temp = temp.left;
                if (temp.left == null && temp.right == null) {
                    stringBuilder.append(temp.data);
                    temp = root;
                }
            }
            if (j == 1) {
                temp = temp.right;
                if (temp.left == null && temp.right == null) {
                    stringBuilder.append(temp.data);
                    temp = root;
                }
            }

        }

        return stringBuilder.toString();

    }
}
