import java.io.*;
import java.util.*;
public class Level {
	private ArrayList<DataNode> list;
	public Level(){
		this.list = new ArrayList<DataNode>();
	}
	public void addDataNode(DataNode DataNode){
		this.list.add(DataNode);
	}
	public void setList(ArrayList<DataNode> list){
		this.list=list;
	}
	public ArrayList<DataNode> getList(){
		return this.list;
	}
}
