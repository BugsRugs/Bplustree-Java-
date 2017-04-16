import java.util.*;
import java.io.*;

class DataNode{
	private String data;
    DataNode(){
        data = null;
    }   
 //    public String toString(){
	// 	return data.toString();
	// }
	public DataNode(String x){
        data = x;
    }
    public String getData(){
        return data;
    }   
    public boolean inOrder(DataNode dnode){
        return (dnode.getData().compareTo(this.data) <= 0);
        // return (dnode.getData()<=this.data);
    }
}