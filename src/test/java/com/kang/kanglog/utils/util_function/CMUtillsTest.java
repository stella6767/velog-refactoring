package com.kang.kanglog.utils.util_function;

import com.github.javafaker.Faker;
import com.github.javafaker.File;
import com.kang.kanglog.web.PostController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;


class CMUtillsTest {


    private static final Logger log = LoggerFactory.getLogger(CMUtillsTest.class);

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



    @Test
    @DisplayName("java faker test")
    public void faker()
    {
        Faker faker = new Faker(new Locale("ko"));

        String name = faker.name().fullName();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        String email = faker.internet().emailAddress();
        String domain = faker.internet().domainName();
        String ipaddr = faker.internet().ipV4Address();

        String cname = faker.company().name();
        File file = faker.file();

        String content = faker.matz().quote();
        String title = faker.hipster().word();
        String image2 = faker.avatar().image();

        String catchPhrase = faker.company().catchPhrase();

        String streetAddress = faker.address().streetAddress();
        String address = faker.address().fullAddress();

        String image = faker.internet().image();

        log.info("name=>" + name);
        log.info("firstName=>" + firstName);
        log.info("lastName=>" + lastName);
        log.info("content=>" + content);
        log.info("title=>" + title);
        log.info("img=>" + image); //http 라서 안 되겠는 걸?
        log.info("img2=>" + image2);

    }


}