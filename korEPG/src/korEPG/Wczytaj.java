package korEPG;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Wczytaj {
	static void wczytaj(String nazwaPliku) {
		try {
	         File inputFile = new File(nazwaPliku);
	         SAXBuilder saxBuilder = new SAXBuilder();
	         Document document = saxBuilder.build(inputFile);
	         Element classElement = document.getRootElement();
	         List<Element> supercarList = classElement.getChildren("channel");
	         for (Element e: supercarList) {
	        	 String id = e.getAttributeValue("id");
	        	 String ikona = "";
	        	 new Kanal(id, ikona);
	         }
	         List<Element> programy = classElement.getChildren("programme");
	         for (Element e: programy) {
	        	 String start = e.getAttributeValue("start").split(" ")[0];
	        	 String stop= e.getAttributeValue("stop").split(" ")[0];
	        	 String kanal = e.getAttributeValue("channel");
	        	 String tytul = e.getChildText("title");
	        	 String opis  = e.getChildText("desc");
	        	 
	        	 String wiek="";
	        	 Element e2 = e.getChild("rating");
	        	 if (e2!=null) 
	        		 wiek = e.getChildText("value");

	        	 String kategoria = e.getChildText("category");
	        	 
	        	 String ikona="";
	        	 Element e3 = e.getChild("icon");
	        	 if (e3!=null)
	        		 ikona = e3.getAttributeValue("src");
	        	 
	        	 new Program(start, stop, kanal, tytul, opis,wiek,kategoria, ikona);
	         }
	      } catch(JDOMException e) {
	         e.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      }
	}
	

}
