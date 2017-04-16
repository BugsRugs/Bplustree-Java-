import java.util.*;
import java.io.*;

class TreeNode extends Tnode{

	protected Vector<Tnode> pointer;
	@SuppressWarnings("unchecked") TreeNode(int x){
		super(x);
		pointer = new Vector();
	}
	public Tnode getPointerTo(DataNode x){
		int i = 0;
		boolean found = false;
		while(!found && i<data.size()){
			if(((DataNode)data.elementAt(i)).inOrder(x)){
				found = true;
			}
			else{
				i++;				
			}

		}
		return (Tnode) pointer.elementAt(i);
		
	}

	public Tnode getPointerAt(int index) {
		return (Tnode) pointer.elementAt(index);
	}


	protected void split_node(DataNode dnode, Tnode left, Tnode right) {
		int splitpt,i,insertpt= 0; 
		if(maxsize%2 == 0) {
			splitpt = maxsize/2;
		}
		else {
			splitpt = (maxsize+1)/2 -1;
		}
		
		boolean dnodeinserted = false;
		for(i=0;!dnodeinserted && i<data.size();i++){
			if(((DataNode)data.elementAt(i)).inOrder(dnode)) {
				data.add(i,dnode);
				((TreeNode)this).pointer.remove(i);
				((TreeNode)this).pointer.add(i,left);
				((TreeNode)this).pointer.add(i+1,right);
				dnodeinserted = true;
				insertpt= i;
			}
		}
		if(!dnodeinserted){
            insertpt = data.size();
			data.add(dnode);
			((TreeNode)this).pointer.remove(((TreeNode)this).pointer.size()-1);
			((TreeNode)this).pointer.add(left);
			((TreeNode)this).pointer.add(right);
            
		}
		DataNode midnode = (DataNode) data.remove(splitpt);
		TreeNode newright = new TreeNode(maxsize);
		for(i=data.size()-splitpt;i>0;i--) {
			newright.data.add(this.data.remove(splitpt));
			newright.pointer.add(this.pointer.remove(splitpt+1));
		}
		newright.pointer.add(this.pointer.remove(splitpt+1));		
        if(insertpt < splitpt) {
            left.setParent(this);
            right.setParent(this);
        }
        else if(insertpt == splitpt) {
            left.setParent(this);
            right.setParent(newright);
        }
        else {
            left.setParent(newright);
            right.setParent(newright);
        }
		this.propagate(midnode, newright);
	}

	Tnode insert(DataNode dnode) {
		Tnode next = this.getPointerTo(dnode);
		
		return next.insert(dnode);
	}
}