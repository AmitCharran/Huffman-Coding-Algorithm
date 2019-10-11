import java.io.*;
import java.util.Scanner;

//Amit Charran
//Huffman Coding Algorithm
//Encodes text, generates an encoded file and decodes text

public class Main {
    ////Node class
    public static class Node{
        private String chstr;
        private int prob;
        private String code;
        private Node next;
        private Node left;
        private Node right;

        public Node(){
            chstr = "";
            prob =  -1;
            code = "";
            next = null;
            left = null;
            right = null;
        }
        public Node(String ch, int p, String c, Node n, Node l, Node r){
            chstr = ch;
            prob = p;
            code = c;
            next = n;
            left = l;
            right = r;
        }
        public Node(String c, int p, Node n){
            chstr = c;
            prob = p;
            code = "";
            next = n;
            left = null;
            right = null;
        }
        Node(String c, int p){
            chstr = c;
            prob = p;
            code = "";
            next = null;
            left= null;
            right = null;
        }



        //Getter and Setter for Node Class
        public String getChstr() {
            return chstr;
        }

        public void setChstr(String chstr) {
            this.chstr = chstr;
        }

        public int getProb() {
            return prob;
        }

        public void setProb(int prob) {
            this.prob = prob;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }



        //Methods for Node Class
        public boolean isLeaf(){
            if(left == null && right == null){
                return true;
            }
            return false;
        }
        public String toString(){
            if(next != null && !isLeaf()){
                return ("( " + getChstr() + "," + getProb() + "," + next.getChstr() + "," + getCode()+ "," +
                        left.getChstr() +"," + right.getChstr() + " )");
            }
            else if(next == null && isLeaf()){
                return ("( " + getChstr() + "," + getProb() + "," + "NULL" + "," + getCode()+ "," +
                        "NULL" +"," +"NULL" + " )");
            }
            else if(next == null && !isLeaf()){
                return ("( " + getChstr() + "," + getProb() + "," + "NULL" + "," + getCode()+ "," +
                        left.getChstr() +"," +right.getChstr() + " )");
            }
            else if(isLeaf()&& next != null){
                return ("( " + getChstr() + "," + getProb() + "," + next.getChstr() + "," + getCode()+ "," +
                        "NULL" +"," +"NULL" + " )");
            }else{
                return "";
            }
        }
    }





//////Linked List
    public static class linkedList{
        private Node listHead;

        public linkedList(){
            listHead = new Node("listHead", -1, null);
        }
        public void insertNewNode(Node newNode){

            Node spot = findSpot(newNode.getProb());
            if(spot.getNext() != null){
                newNode.setNext(spot.getNext());
            }
            spot.setNext(newNode);
        }
        public Node findSpot(int prob){
            Node loopElement = listHead;
            Node spot = listHead;
            while(loopElement != null){
                if(loopElement.getNext() == null){
                    spot =  loopElement;
                    break;
                }else if(loopElement.getNext().getProb() > prob){
                    spot =  loopElement;
                    break;
                }
                loopElement = loopElement.getNext();
            }
            return spot;
        }

        public Node getListHead() {
            return listHead;
        }
        public Node getLastNode(){
            Node iterativeNode = listHead;
            while(iterativeNode.getNext() != null){
                iterativeNode = iterativeNode.getNext();
            }
            return iterativeNode;
        }
        public String toString(){
            StringBuilder output = new StringBuilder("");
            Node iterativeNode = listHead;

            while(iterativeNode!= null){
                if(iterativeNode.getNext() == null){
                    output.append(iterativeNode.toString() );
                }
                output.append((iterativeNode.toString() + " -->  "));
                iterativeNode = iterativeNode.getNext();
            }
            return output.toString();
        }
    }

    public static class Tree{
        private Node root;

        public Tree(){
            root = new Node();
        }


        //Getters and Setters
        public Node getRoot() {
            return root;
        }

        public void setRoot(Node root) {
            this.root = root;
        }

        public void createTree(linkedList lL){
            Node iterativeNode = lL.getListHead().getNext();
            while(iterativeNode != null){
                Node firstNode = iterativeNode;
                if(iterativeNode.getNext() == null){
                    break;
                }
                Node secondNode = iterativeNode.getNext();
                String chstr = firstNode.getChstr() + secondNode.getChstr();
                int prob = firstNode.getProb()+secondNode.getProb();
                Node newNode = new Node(chstr, prob, "", null, secondNode, firstNode);
                lL.insertNewNode(newNode);
                if(iterativeNode.getNext() != null){
                    iterativeNode = iterativeNode.getNext().getNext();
                }else if(iterativeNode.getNext() == null){
                    break;
                }
                root = lL.getLastNode();
            }
        }
        public void createCodeForNode(){
            String code = "";
            createCodeForNode(root, code);
        }
        private void createCodeForNode(Node node, String code){
            if(node == null) return;
            node.setCode(code);
            if(node == root){
                node.setCode("root");
            }
            createCodeForNode(node.getLeft(), (code + "0"));
            createCodeForNode(node.getRight(), (code + "1"));
        }
        public String postOrder(){
            StringBuilder ans = new StringBuilder("");
            ans.append("Post-Order\n");
            postOrder(root,ans);
            ans.append("\n");
            return ans.toString();
        }
        private void postOrder(Node node, StringBuilder ans){
            if(node == null) return;
            postOrder(node.getLeft(),ans);
            postOrder(node.getRight(),ans);
            ans.append(node + "\n");
        }

    }

    public static void decodeFile(File infile, File outfile, Tree tree) throws Exception{
        String s;
        Scanner sc = new Scanner(infile);
        FileWriter writer = new FileWriter(outfile);

        Node traversingNode = tree.getRoot();

        while(sc.hasNext()){
            s = sc.next();

            for(int i = 0; i < s.length(); i++){
                if(s.charAt(i) == '0'){
                    traversingNode = traversingNode.getLeft();
                } else if(s.charAt(i) == '1'){
                    traversingNode = traversingNode.getRight();
                } else{
                    System.out.println("the compressed file contains invalid characters");
                    return;
                }
                if(traversingNode.isLeaf()){
                    writer.write(traversingNode.getChstr());
                    traversingNode = tree.getRoot();
                }

            }
            writer.write(" ");
        }
        sc.close();
        writer.close();
    }

    public static File encodeWithFileOutput(File in, String name, linkedList lL) throws Exception{
        File output = new File(name);
        Scanner sc = new Scanner(in);
        FileWriter writer = new FileWriter(output);

        Node itNode = null;

        while(sc.hasNext()){
            String s = sc.next();
            for(int i = 0; i < s.length(); i++){
                itNode = lL.getListHead();
                while(itNode != null){
                    if(itNode.getChstr().equals( String.valueOf(s.charAt(i)) )){
                            writer.write(itNode.getCode());
                    }
                    itNode = itNode.getNext();
                }
            }
            writer.write(" ");
        }
        sc.close();
        writer.close();
        return output;
    }

    public static Tree createTreeFromFile(File infile, linkedList lL){
        int[] charCountArray = charCount(infile);

        for(int i = 0; i < 256; i++){
            if(charCountArray[i] != 0){
                String chstr = String.valueOf((char)i);
                Node newNode = new Node(chstr, charCountArray[i]);
                lL.insertNewNode(newNode);
            }
        }

        Tree tree = new Tree();
        tree.createTree(lL);
        tree.createCodeForNode();

        return tree;
    }

    public static int[] charCount(File file){
        char data;
        int[] arr = new int[256];
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        }catch(FileNotFoundException e){
            System.out.println("FNFE from inside charCount function :" + e.getMessage());
        }

        for(int i = 0; i < 256; i++){
            arr[i] = 0;
        }
        while(sc.hasNext()){
            String s = sc.next();
            for(int i = 0;i < s.length(); i++){
                arr[s.charAt(i)]++;
            }

        }
        sc.close();

        return arr;
    }

    public static String askForCompressedFileName(){
        System.out.println("Would you like to see the compressed file?\n"
                + "Enter Y for yes.\n"
                + "Enter N for no.");
        Scanner sc = new Scanner(System.in);
        char yn = sc.next().charAt(0);
        while(true) {
            if (yn == 'y' || yn == 'Y') {
                System.out.println("What is the name of the text file to be compressed");
                String ans = sc.next();
                return ans;
            } else if (yn == 'N' || yn == 'n') {
                return "...";
            } else {
                System.out.println("Please Enter 'Y' or 'N'");
                yn = sc.next().charAt(0);
            }
        }

    }

    //// Main
    public static void main(String[] args) throws Exception {
        linkedList list = new linkedList();
        String compressedFileName = askForCompressedFileName();
        if (compressedFileName.equals("...")) {
            return;
        }

        while (!compressedFileName.equals("...")) {
            String nameOrg = compressedFileName + ".txt";
            String nameCompressed = (compressedFileName + "_compressed.txt");
            String nameDecompressed = (compressedFileName + "_decompressed.txt");
            String debugFileName = (compressedFileName + "_debug.txt");

            File inputFile = new File(nameOrg);
            Tree tree = createTreeFromFile(inputFile, list);

            File encodedOutfile = encodeWithFileOutput(inputFile, nameCompressed, list);

            File decodedOutfile = new File(nameDecompressed);
            decodeFile(encodedOutfile, decodedOutfile, tree);
            
            File debugFile = new File(debugFileName);
            writeToDebugFile(debugFile, tree, list);
            
            compressedFileName = askForCompressedFileName();
        }
    }

    private static void writeToDebugFile(File debugFile, Tree tree, linkedList list) throws Exception {
        FileWriter writer = new FileWriter(debugFile);
        writer.write("Linked List\n" + list + "\n");
        writer.write("Tree\n" + tree.postOrder());
        writer.close();

    }


}
