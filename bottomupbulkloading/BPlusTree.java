import java.io.*;
import java.util.*;
public class BPlusTree {
	private ArrayList<DataNode> leaves;
	private ArrayList<Level> levels;
	private int height;
	
	public BPlusTree(){
		this.height=0;
		this.leaves = new ArrayList<DataNode>();
		this.levels = new ArrayList<Level>();
	}
	public ArrayList<DataNode> getLeaves(){
		return this.leaves;
	}
	public void addLevel(Level level){
		this.levels.add(level);
	}
	public void setHeight(int i){
		this.height=height;
	}
	public int getHeight(){
		return this.height;
	}
	public void addLeaf(DataNode leaf){
		this.leaves.add(leaf);
	}
	public ArrayList<Level> getLevels(){
		return this.levels;
	}
	
	
	
}

