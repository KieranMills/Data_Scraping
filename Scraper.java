package org.jsoup.examples;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection.Method;
import org.jsoup.*;
import org.jsoup.select.Elements;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

import static org.jsoup.nodes.Document.*;

public class Scraper {

    public static void main(String[] args) throws Exception {

       Connection.Response res = Jsoup
                .connect("https://myasx.asx.com.au/home/login")
                .data("username", "kieran_mills@hotmail.com")
                .data("password","Drumkit92")
                .method(Method.POST).execute();

        Map<String, String> loginCookies = res.cookies();

        Document document2 = Jsoup.connect("https://myasx.asx.com.au/home/myasx.do")
                .cookies(loginCookies)
                .get();

        Document document3 = Jsoup.connect("https://myasx.asx.com.au/home/premiumService.do?serviceCode=SMG1&display=true")
                .cookies(loginCookies)
                .get();


        System.out.println(document3);

        //prints entire HTML of the website I want.
        // THIS IS THE SHIT!!! here   !!
         //System.out.println(ASXgame);
        //System.out.println(loginCookies);
       // System.out.println(document2.select(table.select));
       // System.out.println("I am a live program");
       // Elements tables = document2.select("table");
        //System.out.println(tables);



        }


    }


            //for (Element table : tables) {
            //if (table.id().equals(""))  {print(" : <%s>  ", table.className());} else
            //System.out.print(" : <%s>  ", table.id());
            //Print
