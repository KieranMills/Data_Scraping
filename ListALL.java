import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiz on 14/04/2017.
 */
public class ListALL {
    static String[] Urlnames = {"General Undertakings", "s.93AA ASIC act 1989 & ASIC Act 2001", "Water Act 2007", "Trade Practises Act 1974"};
    static String[] URLarray = {"http://registers.accc.gov.au/content/index.phtml/itemId/12283", "http://registers.accc.gov.au/content/index.phtml/itemId/6045", "http://registers.accc.gov.au/content/index.phtml/itemId/949450", "http://registers.accc.gov.au/content/index.phtml/itemId/815599"};
    static int count = 0;
    static int count2 = 0;
    static List<String> list = new ArrayList<String>();
    public static ArrayList URLS = new ArrayList();
    // first URLS uses all links found in this class, being initial links not usefull for tables.
    public static ArrayList URLS2 = new ArrayList();
    public static ArrayList URLSTITLE = new ArrayList();


    public static void main(String[] args) throws IOException,FileNotFoundException,SocketTimeoutException {
        final ArrayList arrayList = ListLinks();
        ListDATA();


        // print System.out.println(ListLinks());
       // System.out.println(URLS2.size());

    }

    private static String ListDATA() throws IOException,FileNotFoundException,SocketTimeoutException {
        String URL = new String("\"http://registers.accc.gov.au/content/index.phtml/itemId/344335\"");
        System.out.println(URL);
//notation to enclose quotation marks \"+name+\"
        //Document doc = Jsoup.connect("http://registers.accc.gov.au/content/index.phtml/itemId/344335").userAgent("Chrome").get();  //.userAgent is additionally prefered to simulate chrome or mozilla
        String listString;
        listString = null;
        //f hits 140 and server crashes
        for (int f = 143; f < URLS2.size(); f++) {
            if (f==141) {f=142;}
            if (f==140) {f=142;}
            try {
                Thread.sleep(0);
                //server connection issues
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Document doc = Jsoup.connect((String) URLS2.get(f)).userAgent("Firefox").timeout(10*90000).get();
            Elements links = doc.select("a[href]");
            Elements media = doc.select("[src]");
            Elements imports = doc.select("link[href]");
            Elements tables = doc.select("table");
            //Elements rows = doc.select("tr");     must declare these elements later in for loop.
            //Elements columns = rows.select("td");

            listString = null;
            // proper loop for (int f = 1; f < URLS2.size(); f++){

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
                print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
            }

            print("\nLinks: (%d)", links.size());
            for (Element link : links) {
                print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));

            }
            print("\nTables; (%d)", tables.size());
//urls title array requires replacing when saving the title txtfile. so replace all.
            PrintWriter writer = new PrintWriter("/Users/Kiz/Desktop/DataMergers/"+ f+".tsv", "UTF-8");
            listString = null;
            //to replace + area+
             //URLSTITLE.get(f) ;
            for (Element table : doc.select("table.accctable")) {
                print("Table name : <%s>  ", table.className());

                for (Element row : table.select("tr")) {
                    print("row"); //row.text());
                    List<String> list = new ArrayList<String>();  //Creates a new String Array

                    for (Element column : row.select("th")) {
                        print("header data <%s> ", column.text());
                        list.add(column.text());
                    }

                    for (Element column : row.select("td")) {
                        print("column data <%s> ", column.text());  //td implies a new column of data in HTML
                        list.add(column.text());  //adds column.txt data to the String Array

                    }
                    ;


                    listString = String.join("\t", list);

                    // System.out.println("List string");
                    // System.out.println(listString);   prints the saved String line,
                    writer.println(listString);
                    System.out.println(f +"this is f")     ;

                }
                ;


                writer.close();

            }

        }
        return listString;
    }

    private static ArrayList ListLinks() throws IOException, FileNotFoundException {
        // second urls contains all urls required to fetch data from tabls


        // goes through initial Browse undertakings form URLS.
        for (int i = 0; i < 4; i++) {
            String URL = URLarray[i];
            // print System.out.println(URLarray[i]);
            // print System.out.println(Urlnames[i]);
//printed in format of URL name, then name of general undertaking class.
            Document document = Jsoup.connect(URL).timeout(10*1000).get();
            //System.out.println(document.text()); used as a test
            for (Element a : document.select("table > tbody > tr > td > table > tbody > tr > td.middlePane > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > a")) {

                //1, 4, 8,9 useless links.
                final String title = a.text();
                final String rating = a.text();

                {
                    count++;
                }  // counts the number of times and uses to put into an array.
                //System.out.println("data in table..." + title);


                // print System.out.println(a.attr("abs:href")); //must specify between abs:href and href due to relative URLs.

                URLS.add(a.attr("abs:href"));
                if (URLS.size() < 8) {
                    URLS2.add(a.attr("abs:href"));
                    a.text().replaceAll("/","");
                    URLSTITLE.add(a.text());
                }
                //print System.out.println(count);
                // print System.out.println(URLS.get(0));
                //System.out.println(URLS.get(0));
                //establishing condition when there is a date,
                // very good way to test the final
            }
        }
        for (int j = 1; j < URLS.size(); j++) {
            //casting casts the url to string.
            // print System.out.println(URLS.get(j));
            if (j >= 8) {
                Jsoup.connect((String) URLS.get(j)).userAgent("Chrome").timeout(10*1000).get();
                for (Element c : Jsoup.connect((String) URLS.get(j)).userAgent("Chrome").timeout(10*1000).get().select("table > tbody > tr > td > table > tbody > tr > td.middlePane > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > a")) {
                    //print System.out.println(c.text() + "     j>8 links");
                    //print System.out.println(c.attr("abs:href") + "     j>8 links");
                    URLS2.add(c.attr("abs:href"));
                    c.text().replaceAll("/","");
                    URLSTITLE.add(c.text());
                    {
                        count2++;
                    }
                    ;
                   //print  System.out.println(count2);
                   //print  System.out.println(URLS2.size());
                    //  else { }
                }
            }
        }
    return    URLS2;

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
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
}


