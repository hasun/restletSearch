package com.mycompany.restlet.search.sample;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Attribute;
import org.apache.lucene.util.AttributeFactory;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.Version;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer.KoreanToken;

import scala.collection.Seq;

import com.twitter.penguin.korean.TwitterKoreanProcessorJava;

public class kmaAnalyzer extends Analyzer {
	 
	private Version matchVersion;
	   
	public kmaAnalyzer(Version matchVersion) {
		this.matchVersion = matchVersion;
	}
	 
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new WhitespaceTokenizer());
	}
	   
	public static void main(String[] args) throws IOException {
	     // text to tokenize
	     String text = "힘들겟씀다 그래욬ㅋㅋㅋ";
	     
	     Version matchVersion = Version.LUCENE_5_5_0; // Substitute desired Lucene version for XY
	     kmaAnalyzer analyzer = new kmaAnalyzer(matchVersion);
	     CharSequence result =TwitterKoreanProcessorJava.normalize(text);
	     Seq<KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(result);
	     Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
	     TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(stemmed).toString();
	     List<String> resultList = TwitterKoreanProcessorJava.tokensToJavaStringList(stemmed);
	     StringBuilder builder = new StringBuilder ();
	     for ( String one : resultList) {
	    	 builder.append(one).append(" ");
	     }
	     //tokensToJavaStringList
	     //text =  ""; 
	     TokenStream stream = analyzer.tokenStream("field", new StringReader(builder.toString()));
	     
	     // get the CharTermAttribute from the TokenStream
	     CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);
	 
	     try {
	    	 stream.reset();
	     
//	    	 print all tokens until stream is exhausted
	    	 while (stream.incrementToken()) { 
	    		 System.out.println(termAtt.toString());
	    	 }
	     
	    	 stream.end();
	     } finally {
	    	 stream.close();
	     }
	}
}
