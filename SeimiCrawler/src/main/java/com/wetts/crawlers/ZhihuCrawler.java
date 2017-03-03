package com.wetts.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

import java.util.List;

@Crawler(name = "zhihu")
public class ZhihuCrawler extends BaseSeimiCrawler {

    @Override
    public String[] startUrls() {
        return new String[]{"https://www.zhihu.com/explore"};
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//a[@class='question_link']/@href");
            logger.info("{}", urls.size());
            for (Object s : urls) {
                push(new Request(response.getUrl().substring(0, response.getUrl().lastIndexOf("/")) + s.toString(), "getTitle"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTitle(Response response) {
        JXDocument doc = response.document();
        try {
            logger.info("url:{} {}", response.getUrl(), doc.sel("//h2[@class='zm-item-title']/a/text()"));
            //do something
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
