package org.jsoup.examples;

import org.jsoup.Jsoup;
import org.jsoup.examples.ListLinksACCC;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;

import static org.jsoup.examples.ListLinksACCC.URLS2;

/**
 * Example program to list links from a URL.
 */
public class ListDataACCC extends ListLinksACCC {



    public static void main(String[] args) throws IOException {
        String URL = new String("\"http://registers.accc.gov.au/content/index.phtml/itemId/344335\"");
        System.out.println(URL);
//notation to enclose quotation marks \"+name+\"
        Document doc = Jsoup.connect("http://registers.accc.gov.au/content/index.phtml/itemId/344335").userAgent("Chrome").get();  //.userAgent is additionally prefered to simulate chrome or mozilla
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        Elements tables = doc.select("table");
        //Elements rows = doc.select("tr");     must declare these elements later in for loop.
        //Elements columns = rows.select("td");


        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));

        }
        print("\nTables; (%d)", tables.size());



        PrintWriter writer= new PrintWriter("txtdata.tsv", "UTF-8");
        for (Element table : doc.select("table.accctable")) { print("Table name : <%s>  ", table.className());

            for (Element row: table.select("tr"))  {
                print("row"); //row.text());
                List<String> list = new ArrayList<String>();  //Creates a new String Array

                for (Element column:row.select("th")) {print("header data <%s> ", column.text());
                    list.add(column.text());
                }

                for (Element column:row.select("td")) {
                    print("column data <%s> ", column.text());  //td implies a new column of data in HTML
                    list.add(column.text());  //adds column.txt data to the String Array

                };



                String listString = String.join("\t",list);

                // System.out.println("List string");
                // System.out.println(listString);   prints the saved String line,
                writer.println(listString);

            };





            writer.close();
        }
    }
    //for (Element row : doc.select("table.w3-table-all.notranslate tr")) {

    //final String title = row.text();
    //final String rating = row.text();
    //print(title);}



    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }


}
/* websites I tested on,
      https://www.humanservices.gov.au/customer/enablers/income-and-assets-test-youth-allowance
      http://www.imdb.com/chart/top
      https://en.wikipedia.org/wiki/List_of_countries_by_population_(United_Nations)
      https://en.wikipedia.org/wiki/List_of_countries_by_HIV/AIDS_adult_prevalence_rate
      https://www.cia.gov/library/publications/the-world-factbook/rankorder/2155rank.html

*/