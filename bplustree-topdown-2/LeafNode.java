import java.util.*;
import java.io.*;

class LeafNode extends Tnode{
	private LeafNode nextNode;

	LeafNode(int degree) {
		super(degree);
		nextNode = null;
	}
	
	protected LeafNode getNextNode() {
		return nextNode;
	}

	private void setNextNode(LeafNode next) {
		nextNode = next;
	}
	
	protected void split_node(DataNode dnode) {
		boolean dnodeinserted = false;
		int i;
		for(i=0;!dnodeinserted && i<data.size();i++){
			if(((DataNode)data.elementAt(i)).inOrder(dnode)) {
				data.add(i,dnode);
				dnodeinserted = true;
			}
		}
		if(!dnodeinserted){
			data.add(data.size(), dnode);
		}
		int splitpt;
		if(maxsize%2 == 0){
			splitpt = maxsize/2;
		}
		else{
			splitpt = (maxsize+1)/2;
		}
	
		LeafNode right = new LeafNode(maxsize);
		
		for(i=data.size()-splitpt;i>0;i--){
			right.data.add(data.remove(splitpt));
		}
		
		right.setNextNode(this.getNextNode());
		this.setNextNode(right);
		DataNode midnode=(DataNode)data.elementAt(data.size()-1);
		this.propagate(midnode,right);
	}

	public Tnode insert(DataNode dnode){
		if(data.size()<maxsize-1){
			boolean dnodeinserted = false;
			int i=0;
			while(!dnodeinserted && i<data.size()){
				if(((DataNode)data.elementAt(i)).inOrder(dnode)) {
					data.add(i,dnode);
					dnodeinserted = true;
				}
				i++;
			}
			if(!dnodeinserted){
				data.add(data.size(),dnode);
			}
		}
		else{
			this.split_node(dnode);
		}
		return this.findRoot();
	}
	
}