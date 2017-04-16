import java.util.*;
import java.io.*;
public class ExternalSort {
	public static long optimalSizeOfBlocks(File file){
		long filesize = file.length();
		final int MAXTEMPFILES = 1024;
		long block_size = filesize/MAXTEMPFILES;
		long free = Runtime.getRuntime().freeMemory();
		if(block_size<free/2)
			block_size = free/2;
		else if(block_size>=free)
			System.err.println("Process will run out of memory\n");
		return block_size;
	}
	
	public static List<File> sortInBatch(File file, Comparator<String> comparator) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<File> files = new ArrayList<File>();
		long block_size = optimalSizeOfBlocks(file);
		try{
			List<String> list = new ArrayList<String>();
			String line = "";
			try{
				while(line!=null){
					long current  = 0;
					while((current<block_size) && ((line=reader.readLine())!=null)){
						list.add(line);
						current+=line.length();
					}
					files.add(sortAndSave(list,comparator));
					list.clear();
				}
			}catch(EOFException eof){
				if(list.size()>0){
					files.add(sortAndSave(list,comparator));
					list.clear();
				}
			}
		}finally{
			reader.close();
		}
		return files;
	}
	 public static File sortAndSave(List<String> list, Comparator<String> comp) throws IOException{
		 Collections.sort(list,comp);
		 File temp = File.createTempFile("sortInBatch","flatfile");
		 temp.deleteOnExit();
		 BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
		 try{
			 for(String r:list){
				 writer.write(r);
				 writer.newLine();
			 }
		 }finally{
			 writer.close();
		 }
		 return temp;
	 }
	 
	 public static int mergeSortedFiles(List<File> files, File output, Comparator<String> comp) throws IOException{
		 PriorityQueue<BinaryFileBuffer> pq = new PriorityQueue<BinaryFileBuffer>(11,
				 new Comparator<BinaryFileBuffer>(){
			 		public int compare(BinaryFileBuffer i, BinaryFileBuffer j){
			 			return comp.compare(i.peek(),j.peek());
			 		}
		 }
		 );
		 for(File f:files){
			 BinaryFileBuffer bfb=new BinaryFileBuffer(f);
			 pq.add(bfb);
		 }
		 BufferedWriter fbw=new BufferedWriter(new FileWriter(output));
		 int count=0;
		 try{
			 while(pq.size()>0){
				 BinaryFileBuffer bfb = pq.poll();
				 String r = bfb.pop();
				 fbw.write(r);
				 fbw.newLine();
				 ++count;
				 if(bfb.empty()){
					 bfb.reader.close();
					 bfb.original.delete();
				 }
				 else{
					 pq.add(bfb);
				 }
			 }
		 } finally{
			 fbw.close();
			 for(BinaryFileBuffer bfb : pq){
				 bfb.close();
			 }
		 }
		 return count;
		 
	 }
	 
	 public void externalsort(String infile, String outfile) throws IOException{
		 /*if(infile == null || outfile==null){
			 System.out.println("Provide input and/or output file");
			 return;
		 }*/
		 //String infile=args[0];
		// String outfile=args[1];
		 Comparator<String> comparator = new Comparator<String>(){
			 public int compare(String r1, String r2){
				 return r1.compareTo(r2);
			 }
		 };
		 List<File> li = sortInBatch(new File(infile),comparator);
		 mergeSortedFiles(li, new File(outfile), comparator);
	 }

}

class BinaryFileBuffer{
	public static int BUFFERSIZE = 2048;
	public BufferedReader reader;
	public File original;
	private String cache;
	private boolean empty;
	
	public BinaryFileBuffer(File file) throws IOException{
		original = file;
		reader = new BufferedReader(new FileReader(file));
		reload();
	}
	
	public boolean empty() {
        return empty;
    }
	
	private void reload() throws IOException {
        try{
          if((this.cache = reader.readLine()) == null){
            empty = true;
            cache = null;
          }
          else{
            empty = false;
          }
      } catch(EOFException oef) {
        empty = true;
        cache = null;
      }
    }
	
	public void close() throws IOException{
		reader.close();
	}
	
	public String peek(){
		if(empty()){
			return null;
		}
		return cache.toString();
	}
	
	public String pop() throws IOException{
		String ans=peek();
		reload();
		return ans;
	}
	
}
