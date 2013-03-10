package edu.nyu.cs.cs2580;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.nyu.cs.cs2580.SearchEngine.Options;

/**
 * @CS2580: Implement this class for HW2.
 */
public class IndexerInvertedDoconly extends Indexer {
private static String PATH="";
private static Pattern PATTERN=Pattern.compile("^[^0-9]*([0-9].*[0-9])[^0-9]*&");
  public IndexerInvertedDoconly(Options options) {
    super(options);
    System.out.println("Using Indexer: " + this.getClass().getSimpleName());
  }

  @Override
  public void constructIndex() throws IOException {
	  int docId=0;
	    Map<String,Set<Integer>>map=new HashMap<String,Set<Integer>>();
	    Corpus c=new Corpus(PATH);
	    DocumentIndexed[] di=new DocumentIndexed[c.numOfFile()];
	    for(DocumentIndexed docuementIndexed:c){
	    	for(String s:docuementIndexed.getBody()){
	    		if(!map.containsKey(s))
	    			map.put(s, new HashSet<Integer>());
	    		map.get(s).add(docId);
	    	}
	    	di[docId]=new DocumentIndexed(docId);
	    	docId+=1;
	    }  
	    System.out.println(
	            "Indexed " + c.numOfFile() + " docs with " +
	           map.keySet().size()+ "distinct terms.");
	        String indexFile = _options._indexPrefix + "/corpus.idx";
	        System.out.println("Store index to: " + indexFile);
	        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
					PATH +indexFile, false)));
	        for(String str: map.keySet()){
	        out.print(str+",");
	        Matcher m=PATTERN.matcher(map.get(str).toString());
	        out.println(m.group(0));
	        }
	        out.println("----");
	        for(DocumentIndexed i: di)
	        	out.println(i._docid+",,"+i.getUrl()+",,"+i.getTitle());
	        out.close();
  }

  @Override
  public void loadIndex() throws IOException, ClassNotFoundException {
	  
  }

  @Override
  public Document getDoc(int docid) {
    return null;
  }

  /**
   * In HW2, you should be using {@link DocumentIndexed}
   */
  @Override
  public Document nextDoc(Query query, int docid) {
    return null;
  }

  @Override
  public int corpusDocFrequencyByTerm(String term) {
    return 0;
  }

  @Override
  public int corpusTermFrequency(String term) {
    return 0;
  }

  @Override
  public int documentTermFrequency(String term, String url) {
    SearchEngine.Check(false, "Not implemented!");
    return 0;
  }
}
