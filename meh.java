/**
 * Created by Kiz on 24/02/2017.
 */
package org.jsoup.examples;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import static org.jsoup.Connection.*;

public class meh {

    private void main(String[] args) {

        Connection.Response res = null;
        Connection homeConnection = null;
        Document homePage = null;
        Map<String, String> loginCookies = null;
        try {
            res = Jsoup.connect("https://myasx.asx.com.au/home/login")
                    .data("cookieexists", "false")
                    .data("name", "user")
                    .data("password", "pswd").method(Method.POST)
                    .execute();

        } catch (IOException e) {

            e.printStackTrace();
        }

        if (res != null) {

            loginCookies = res.cookies();

            try {
                homePage = Jsoup.connect("https://myasx.asx.com.au/home/myasx.do")
                        .cookies(loginCookies).get();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

}