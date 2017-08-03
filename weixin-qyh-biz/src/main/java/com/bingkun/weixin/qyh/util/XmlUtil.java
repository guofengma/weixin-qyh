package com.bingkun.weixin.qyh.util;

import com.bingkun.weixin.qyh.dto.weixin.sysEventDto.SysEventTicket;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

import java.beans.Encoder;
import java.io.Writer;

/**
 * Created by pengjikun on 2016/7/31.
 */
public class XmlUtil {
    /**
     * xml转化为bean
     * @param xmlStr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T xml2Bean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }

    public static SysEventTicket xml2Bean(String xmlStr) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(SysEventTicket.class);
        Object obj = xstream.fromXML(xmlStr);
        if(obj instanceof SysEventTicket){
            return (SysEventTicket)obj;
        }
        return null;
    }

    /**
     * bean转化为xml，并为xml文本添加CDATA标记
     * @param obj
     * @return
     */
    public static String bean2Xml(Object obj) {
        XStream xstream = new XStream(new DomDriver("UTF-8"){
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out, this.getNameCoder()){
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }
                };
            }
        });
        xstream.processAnnotations(obj.getClass()); // 识别obj类中的注解
        return xstream.toXML(obj);
    }

    public static void main(String[] args) {
        String xml = "<xml><SuiteId><![CDATA[tjb3b3b6c9a786c35b]]></SuiteId>\n" +
                "<SuiteTicket><![CDATA[2Z7zqgmNPvKhCmUGTojUMuyDuqV8mnjLK9pR4eD7XM4DVOe_RHaTBhLPksindRzF]]></SuiteTicket>\n" +
                "<InfoType><![CDATA[suite_ticket]]></InfoType>\n" +
                "<TimeStamp><![CDATA[1501586772]]></TimeStamp>\n" +
                "</xml>";
        SysEventTicket sysEventTicket = xml2Bean(xml, SysEventTicket.class);
        System.out.println(sysEventTicket);
        String xml2 = bean2Xml(sysEventTicket);
        System.out.println(xml2);
    }
}
