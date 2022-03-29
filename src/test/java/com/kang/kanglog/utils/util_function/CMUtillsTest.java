package com.kang.kanglog.utils.util_function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CMUtillsTest {

    @Test
    @DisplayName("html parser test")
    void jsoupTest() {
        String content = "<img></img>  <h1> hello </h1>";
        Document doc = Jsoup.parseBodyFragment(content);
        Elements imgs = doc.getElementsByTag("img");
        imgs.remove();
        System.out.println(imgs);

        System.out.println(doc);


        for( Element element : doc.select("img") )
        {
            element.remove();
        }


        System.out.println("check: "+doc);
        String outPutContent = doc.body().toString();

        System.out.println("outPutContent: "+outPutContent);

        //String text = Jsoup.parse(content).select("img").remove().html();


    }
}