package com.wetts.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Created by wetts on 2017/3/3.
 */
public class XSSCleaner {

    public static void main(String[] args) {
        String unsafe = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
        String safe = Jsoup.clean(unsafe, Whitelist.basic());
        System.out.println(safe);
    }
}
