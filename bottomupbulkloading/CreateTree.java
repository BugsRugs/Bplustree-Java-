import java.io.*;
import java.util.*;

public class CreateTree {
	public static int count;
	public void createLeaves(BufferedReader br, BPlusTree tree){
		String s="";
		DataNode oldleaf=new DataNode(true);
		Level level=new Level();
		DataNode newleaf=null;
		while(s!=null){
			try{
				int t;
				//t=(int)s.charAt(0);
				newleaf = new DataNode(true);
				for(int i=0;i<3;i++){
					if((s=br.readLine())==null)
						//t=(int)s.charAt(0);
					//else
						break;
					newleaf.addData(s);				
				}
				if(newleaf.getData().size()>0){
					oldleaf.setNext(newleaf);
					tree.addLeaf(newleaf);
					oldleaf=newleaf;
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		newleaf.setChildren(null);
		newleaf.setNext(null);
		if(tree.getLeaves().get(tree.getLeaves().size()-1).getData().size()<3){
			DataNode d = tree.getLeaves().get(tree.getLeaves().size()-2);
			for(int i=0;i<tree.getLeaves().get(tree.getLeaves().size()-1).getData().size();i++){
				d.addData(tree.getLeaves().get(tree.getLeaves().size()-1).getData().get(i));
			}
			tree.getLeaves().remove(tree.getLeaves().get(tree.getLeaves().size()-1));
			tree.getLeaves().remove(tree.getLeaves().get(tree.getLeaves().size()-1));
			tree.getLeaves().add(d);
		}
		if(tree.getLeaves().size()>0){
			tree.setHeight(tree.getHeight()+1);
			level.setList(tree.getLeaves());
			tree.addLevel(level);
		}
	}
	public void print_tree(BPlusTree tree) throws IOException{
		int i =tree.getLevels().size()-1;
		int sum=0;
		Level current;
		BufferedWriter bw=null;
		try{
			bw=new BufferedWriter(new FileWriter("final.txt",true));
			while(i>=0){
				current=tree.getLevels().get(i);
				int j=current.getList().size();
				sum+=j;
				//System.out.println(j);
				for(int k=0;k<j;k++){
					current.getList().get(k).printDataNode(bw);
				}
				bw.newLine();
				i--;
			}
			System.out.println("Number of nodes in tree: "+sum);
			System.out.println("Height of tree: "+tree.getLevels().size());
		}finally{
			bw.close();
		}
	}
	public static void main(String[] args) throws IOException{
		if(args.length<2){
			System.out.println("Please enter input and output files");
			return;
		}
		String infile=args[0];
		String outfile=args[1];
		ExternalSort ex = new ExternalSort();
		ex.externalsort(infile,outfile);
		FileReader fr=null;
		BufferedReader br=null;
		try{
			br=new BufferedReader(new FileReader(outfile));
			BPlusTree tree = new BPlusTree();
			CreateTree create = new CreateTree();
			CreateTree.count=0;
			create.createLeaves(br,tree);
			//System.out.println(tree.getLevels().get(0).getList().size());
			create.createLevels(tree, tree.getLevels().get(0));
			create.print_tree(tree);
		}finally{
			//fr.close();
			br.close();
		}
	}
	public void createLevels(BPlusTree tree, Level level){
		Level top = new Level();
		if(level.getList().size()<=1)
			return;
		else{
			
			DataNode node = new DataNode(false);
			//top.addDataNode(node);
			DataNode currentDataNode;
			int i=0;
			while(i<level.getList().size()){
				//System.out.println(node.getChildren().size());
				while(i<level.getList().size() && node.getChildren().size()<4){
					
					node.addChild(level.getList().get(i));
					//System.out.println(node.getChildren().size()+"   "+i);
					if(i%4 != 0){
						currentDataNode = node.getChildren().get(i%4);
						while(currentDataNode.getChildren()!=null && currentDataNode.getChildren().size() != 0){
							currentDataNode = currentDataNode.getChildren().get(0);
						}
						node.addData(currentDataNode.getData().get(0));
						//node.addData(null);

					}
					i++;
				}
				if(i==level.getList().size() && node.getData().size()<3 && top.getList().size()!=0){
					int k=0;
					int j;
					String d =null;
					
					DataNode e = top.getList().get(top.getList().size()-1);
					currentDataNode = node.getChildren().get(0);
					while(currentDataNode.getChildren()!=null && currentDataNode.getChildren().size() != 0){
						currentDataNode = currentDataNode.getChildren().get(0);
					}
					d=(currentDataNode.getData().get(0));
					e.addData(d);
					e.addChild(node.getChildren().get(0));
					for(j=0;j<node.getData().size();j++){
						e.addData(node.getData().get(j));
						e.addChild(node.getChildren().get(j+1));
					}
					//d.addChild(node.getChildren().get(j));
					top.getList().remove(top.getList().size()-1);
					top.addDataNode(e);
				}				
				else{
					top.addDataNode(node);
				}
				node=new DataNode(false);
			}
			tree.addLevel(top);
			createLevels(tree,top);
		}
	}
	
	
	
}
