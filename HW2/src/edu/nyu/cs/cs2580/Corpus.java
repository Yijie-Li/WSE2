package edu.nyu.cs.cs2580;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.CharacterReference;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.TextExtractor;

public class Corpus implements Iterable<DocumentIndexed>{

	private String path;
	private File[] files;
	private int pos;
	private int did;
	
	

	class CorpusIterator implements Iterator<DocumentIndexed>{

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return pos < files.length;
		}

		@Override
		public DocumentIndexed next(){
			// TODO Auto-generated method stub
			Source source;
			try {
				source = new Source(files[pos]);
				source.fullSequentialParse();
				DocumentIndexed di = new DocumentIndexed(++did);
				di.setUrl(files[pos].getName());
				
				Element titleElement=source.getFirstElement(HTMLElementName.TITLE);
				if (titleElement==null) di.setTitle(null);
				else di.setTitle(CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent()));
				
				ArrayList<String> ls = new ArrayList<String>();
				List<Element> linkElements=source.getAllElements(HTMLElementName.A);
				for (Element linkElement : linkElements) {
					String label=linkElement.getContent().getTextExtractor().toString();
					if(label != null && !(label=label.trim()).isEmpty()) {
						String[] tokens = label.split(" ");
						for(int i=0;i<tokens.length;i++) {
							ls.add(tokens[i]);
						}
					}
				}
				
				TextExtractor textExtractor=new TextExtractor(source) {
					public boolean excludeElement(StartTag startTag) {
						return "control".equalsIgnoreCase(startTag.getAttributeValue("class")) || startTag.getName()==HTMLElementName.LINK
								|| startTag.getName()==HTMLElementName.META|| startTag.getName() == HTMLElementName.A ||startTag.getName() == HTMLElementName.INPUT;
					}
				};
				String[] tokens = textExtractor.setIncludeAttributes(true).toString().split(" ");
				for(int i=0;i<tokens.length;i++) {
					ls.add(tokens[i]);
				}
				di.setBody((String[])ls.toArray());
				pos++;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public Corpus(String p) {
		path = p;
		File f = new File(path);
		files = f.listFiles();
		pos = 0;
		did = 0;
	}
	
	public int numOfFile() {
		return files.length;
	}
	
	@Override
	public Iterator<DocumentIndexed> iterator() {
		return new CorpusIterator();
	}
	
}
