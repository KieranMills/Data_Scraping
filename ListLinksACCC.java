package org.jsoup.examples;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class ListLinksACCC {
    static String[] Urlnames = {"General Undertakings","s.93AA ASIC act 1989 & ASIC Act 2001","Water Act 2007","Trade Practises Act 1974"};
    static String[] URLarray = {"http://registers.accc.gov.au/content/index.phtml/itemId/12283", "http://registers.accc.gov.au/content/index.phtml/itemId/6045", "http://registers.accc.gov.au/content/index.phtml/itemId/949450", "http://registers.accc.gov.au/content/index.phtml/itemId/815599"};
    static int count = 0;
    static int count2= 0;
    static List<String> list = new ArrayList<String>();
  public static ArrayList URLS = new ArrayList();
    // first URLS uses all links found in this class, being initial links not usefull for tables.
  public static ArrayList URLS2 = new ArrayList();


    // second urls contains all urls required to fetch data from tabls.
    public static void main(String[] arg) throws IOException
    {

        // goes through initial Browse undertakings form URLS.
        for (int i=0; i<4; i++) {
            String URL = URLarray[i];
            // print System.out.println(URLarray[i]);
            // print System.out.println(Urlnames[i]);
//printed in format of URL name, then name of general undertaking class.
            Document document = Jsoup.connect(URL).get();
            //System.out.println(document.text()); used as a test
            for (Element a : document.select("table > tbody > tr > td > table > tbody > tr > td.middlePane > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > a")) {

                //1, 4, 8,9 useless links.
                final String title = a.text();
                final String rating = a.text();

                {count++;}  // counts the number of times and uses to put into an array.
                //System.out.println("data in table..." + title);

                System.out.println(a.text());
                System.out.println(a.attr("abs:href")); //must specify between abs:href and href due to relative URLs.

                URLS.add(a.attr("abs:href"));
                if (URLS.size() <8) {URLS2.add(a.attr("abs:href"));}
                System.out.println(count);
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
              Jsoup.connect((String) URLS.get(j)).userAgent("Chrome").get();
                for (Element c : Jsoup.connect((String) URLS.get(j)).userAgent("Firefox").get().select("table > tbody > tr > td > table > tbody > tr > td.middlePane > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > a")) {
                    System.out.println(c.text() + "     j>8 links");
                    System.out.println(c.attr("abs:href") + "     j>8 links");
                    URLS2.add(c.attr("abs:href"));
                    {count2++;};
                    System.out.println(count2);
                    System.out.println(URLS2.size());
                   //  else { }
                }
            }
        }
    }
    public static ArrayList getURLS2()
    {return URLS2;

    }
}