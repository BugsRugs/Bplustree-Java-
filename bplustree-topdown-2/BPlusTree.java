import java.util.*;
import java.io.*;

class BPlusTree{

    private static Tnode tree;
    private static int degree;
    private static int count;
    public static int node_count;
    
    private BPlusTree(int x){
        degree = x;
        tree = new LeafNode(degree);
        
    }

    private static void insertIntoTree(DataNode dnode){
        tree = tree.insert(dnode);
    }

    public static void main(String[] args) throws IOException{
         if(args.length <2) {
            System.err.println("Syntax error in call sequence, use:\n\tjava BplusTree");
        }
        BPlusTree bpt=new BPlusTree(6);
        
        
        BPlusTree.degree=6;
        Tnode root = new LeafNode(BPlusTree.degree);
        BPlusTree.tree=root;
        count=0;
        node_count=0;
        final int BUFFERSIZE = 2048;
        String infile = args[0];
        String outfile = args[1];
        ExternalSort ex = new ExternalSort();
        ex.externalsort(infile,outfile);
        FileReader file =null;
        BufferedReader br = null;
        BufferedWriter bw = new BufferedWriter(new FileWriter("final.txt",true));
        try{
            file = new FileReader(outfile);
            br = new BufferedReader(file,BUFFERSIZE);
            int i,t;
            String line;
            for(i=0;i<100;i++){
                if((line=br.readLine())!=null){
                    //t=line;
                    DataNode dnode = new DataNode(line);
                    BPlusTree.insertIntoTree(dnode);
                }
            }
            bpt.print_tree(bw);
            System.out.println("Height of B+Tree: "+count);
            System.out.println("Number of Nodes of B+Tree: "+node_count);
        }finally{
            br.close();
            bw.close();
        }
    }
    public void print_tree(BufferedWriter bw)throws IOException{
        Vector<Tnode> tnodes = new Vector<Tnode>();
        tnodes.add(tree);
        boolean finished=false;
        while(finished==false){
            String print_this = "";
            Vector<Tnode> tnodes_next = new Vector<Tnode>();
            for(int i=0; i<tnodes.size();i++){
                Tnode node = tnodes.elementAt(i);
                print_this+=node.toString()+" ";
                if(node.isLeafNode())
                    finished=true;
                else{
                    for(int j=0;j<node.size()+1;j++){
                        tnodes_next.add(((TreeNode)node).getPointerAt(j));
                    }
                }
            }
            bw.write(print_this);
            bw.newLine();
            count++;
            tnodes = tnodes_next;
        }
    }
}


