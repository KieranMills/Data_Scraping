import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.awt.*;
import java.net.URL;

import jdk.*;

public class Scraper2 {

    public static void main(String[] args) throws Exception {
        String Data[]= new String[14];
        int count = 0;
String[] URLarray = new String[4];
String URL = "http://registers.accc.gov.au/content/index.phtml/itemId/12283";
        Document document = Jsoup.connect(URL).get();
//connects to the webpage and obtains HTML


        for (Element a : document.select("table > tbody > tr > td > table > tbody > tr > td.middlePane > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > a")) {

            final String title = a.text();
            final String rating = a.text();
            Data[count] = (title);
            {count++;}  // counts the number of times and uses to put into an array.
            //System.out.println("data in table..." + title);
            System.out.println(a.text());
            System.out.println(a.attr("abs:href")); //must specify between abs:href and href due to relative URLs.
            System.out.println(count);
        }



    }
}