package com.wetts.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by wetts on 2017/3/3.
 */
public class Demo {

    public static void main(String[] args) {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://epaper.sxrb.com//shtml/sxrb/20160203/335337.shtml").get();

            Elements title = doc.select(".detConTit");
            System.out.format("title: %s \n", title.text());
            Elements body = doc.select(".detailsWrap");
            System.out.format("body: %s ... \n", body.html().substring(0, 100));

            Elements imgs = body.select("img");
            for (Element img : imgs) {
                String src = img.absUrl("src");
                System.out.format("img: %s \n", src);
            }
        } catch (IOException e) {
        }
    }
}
