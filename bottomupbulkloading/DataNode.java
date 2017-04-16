import java.io.*;
import java.util.*;
public class DataNode {
	//private current_size;
	private ArrayList<String> data;
	private ArrayList<DataNode> children;
	private DataNode next;
	boolean leaf;
	public DataNode getNext(){
		return this.next;
	}
	public void setNext(DataNode next){
		this.next=next;
	}
	public DataNode(boolean leaf){
		this.leaf=leaf;
		this.data = new ArrayList<String>();
		this.children=new ArrayList<DataNode>();
	}
	public ArrayList<String> getData(){
		return this.data;
	}
	public void addData(String value){
		this.data.add(value);
	}
	public int setDataIndex(int i, String value){
		if(this.data.size()==i){
			this.data.add(value);
			return 0;
		}
		else if(this.data.size()>i){
			this.data.set(i,value);
			return 0;
		}
		else{
			return -1;
		}
	}
	public void printDataNode(BufferedWriter bw){
		try{
			int i=0;
			bw.write("[");
			for(i=0;i<this.getData().size();i++){
				bw.write(this.getData().get(i)+" ");
			}
			bw.write("]");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public ArrayList<DataNode> getChildren(){
		return this.children;
	}
	public int setChildIndex(int i, DataNode value){
		if(this.children.size()==i){
			this.children.add(value);
			return 0;
		}
		else if(this.children.size()>i){
			this.children.set(i,value);
			return 0;
		}
		else{
			return -1;
		}
	}
	public void addChild(DataNode value){
		this.children.add(value);
	}
	public void setChildren(ArrayList<DataNode> children){
		this.children=children;
	}
}
