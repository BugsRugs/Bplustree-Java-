import java.util.*;
import java.io.*;

abstract class Tnode{
	protected Vector<DataNode> data;
	protected Tnode parent;
	protected int maxsize;
	//protected boolean leaf;
	public boolean isLeafNode(){
	    return this.getClass().getName().trim().equals("LeafNode");
	}
	abstract Tnode insert(DataNode dnode);

	protected boolean isFull() {
		return data.size() == maxsize-1;
	}
	
	public DataNode getDataAt(int index) {
		return (DataNode) data.elementAt(index);
	}

	protected void propagate(DataNode dnode, Tnode right) {
		if(parent == null){
			TreeNode newparent = new TreeNode(maxsize);
			newparent.data.add(dnode);
			newparent.pointer.add(this);
			newparent.pointer.add(right);
			this.setParent(newparent);
			right.setParent(newparent);
		}
		else{
			if(!parent.isFull()){
				boolean dnodeinserted = false;
				int i;
				for(i=0;!dnodeinserted && i<parent.data.size(); i++){
					if(((DataNode)parent.data.elementAt(i)).inOrder(dnode)){
						parent.data.add(i,dnode);
						((TreeNode)parent).pointer.add(i+1, right);
						dnodeinserted = true;
					}
				}
				if(!dnodeinserted){
					parent.data.add(dnode);
					((TreeNode)parent).pointer.add(right);
				}
				
				right.setParent(this.parent);
			}
			else {
                	((TreeNode)parent).split_node(dnode, this, right);

				}
			}
		}

	public int size() {
		return data.size();
	}	

	@SuppressWarnings("unchecked") Tnode(int degree){
	    parent=null;
	    data=new Vector();
	    maxsize=degree;
	}
	public String toString() {
		String s="";
		int i;
		for(i=0;i<data.size();i++){
			s+=((DataNode)(data.elementAt(i))).getData() + " ";
		}
		BPlusTree.node_count++;
		return "["+s + "]";
	}

	protected Tnode findRoot(){
		Tnode node = this;
		
		while(node.parent != null){
			node = node.parent;
		}
		
		return node;
	}

	

	protected void setParent(Tnode newparent) {
		this.parent = newparent;
	}
}