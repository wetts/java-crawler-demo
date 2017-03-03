package com.wetts.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wetts on 2017/3/2.
 */
@Crawler(name = "weixin", useCookie = true)
public class WeixinCrawler extends BaseSeimiCrawler {
    @Override
    public String[] startUrls() {
        return null;
    }

    @Override
    public List<Request> startRequests() {
        List<Request> requests = new LinkedList<>();
        Request start = Request.build("https://mp.weixin.qq.com/s?__biz=MzAxMzMxNDIyOA==&mid=2655545144&idx=1&sn=ea3d20b306dda7ffca670c333729e2cc&chksm=8018b6e3b76f3ff533bdfd923ac10390ee316860d51c29b54ad71cb14fcc1ba5d5bc653ca437&scene=0&key=29152ff03f3f736021e009587885608df13f13ad319a287e26282385d2c64319e6bbdcc231e1567da0ed5bf6fc7fd5a1a59cd851d32b77b2668302518bb899c836257fcb4b2a9bc4c0fb0e7f5b4148c3&ascene=0&uin=NDMwMjI2NDU1&devicetype=iMac+MacBookPro12%2C1+OSX+OSX+10.12.3+build(16D32)&version=12020010&nettype=WIFI&fontScale=100&pass_ticket=5CrP0aDCAfQ14nZSzo6H99L70TrpD80wqOhkBVVHQvz7w5BPZ4ye4jyx9FOtfWLl", "start");
        requests.add(start);
        return requests;
    }

    @Override
    public void start(Response response) {
//        logger.info(response.getContent());
        push(Request.build("http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzAxMzMxNDIyOA==&f=json", "minePage"));
    }

    public void minePage(Response response) {
        JSONObject ob = JSON.parseObject(response.getContent());

        if (Integer.valueOf(ob.get("ret").toString()) == 0) {
            JSONObject generalMsgList = JSON.parseObject((String) ob.get("general_msg_list"));

            List<Map> list = (List) generalMsgList.get("list");

            int lastId = 0;

            for (int i = 0; i < list.size(); i++) {
                Map commMsgInfo = (Map) list.get(i).get("comm_msg_info");
                lastId = (int) commMsgInfo.get("id");
                if (list.get(i).containsKey("app_msg_ext_info")) {
                    Map appMsgExtInfo = (Map) list.get(i).get("app_msg_ext_info");
                    logger.info("id:{}", commMsgInfo.get("id"));
                    logger.info("author:{}", appMsgExtInfo.get("author"));
                }
            }

            if (1 == (Integer) ob.get("is_continue")) {
                push(Request.build("http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzAxMzMxNDIyOA==&f=json&frommsgid=" + lastId, "minePage"));
            }
        }
    }

}
