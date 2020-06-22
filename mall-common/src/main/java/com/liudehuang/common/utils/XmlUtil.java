package com.liudehuang.common.utils;

import com.liudehuang.common.exception.ConvertException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:39
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:39
 * @UpdateRemark:
 * @Version:
 */
public class XmlUtil {
    protected XmlUtil() {
    }

    public static <T> String serialize(T data) throws ConvertException {
        return serialize(data, "UTF-8", false);
    }

    public static <T> String serialize(T data, Boolean fragmentFlag) throws ConvertException {
        return serialize(data, "UTF-8", fragmentFlag);
    }

    public static <T> String serialize(T data, String encoding, Boolean fragmentFlag) throws ConvertException {
        try {
            JAXBContext context = JAXBContext.newInstance(data.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", encoding);
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.setProperty("jaxb.fragment", fragmentFlag);
            StringWriter writer = new StringWriter();
            marshaller.marshal(data, writer);
            return writer.toString();
        } catch (JAXBException var6) {
            throw new ConvertException(var6);
        } catch (Exception var7) {
            throw new ConvertException(var7);
        }
    }

    public static <T> T deserialize(String xml, Class<T> cls) throws ConvertException {
        try {
            JAXBContext context = JAXBContext.newInstance(cls);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException var4) {
            throw new ConvertException(var4);
        } catch (Exception var5) {
            throw new ConvertException(var5);
        }
    }

    public static String converterXmlStr(Map<? extends Object, ? extends Object> dataMap, String rootElementName) {
        return converterXmlStr(dataMap, rootElementName, "UTF-8");
    }

    public static String converterXmlStr(Map<? extends Object, ? extends Object> dataMap, String rootElementName, Boolean fragmentFlag) {
        return converterXmlStr(dataMap, rootElementName, "UTF-8", fragmentFlag);
    }

    public static String converterXmlStr(Map<? extends Object, ? extends Object> dataMap, String rootElementName, String encoding) {
        return converterXmlStr(dataMap, rootElementName, encoding, false);
    }

    public static String converterXmlStr(Map<? extends Object, ? extends Object> dataMap, String rootElementName, String encoding, Boolean fragmentFlag) {
        if (null == dataMap) {
            return null;
        } else {
            if (StringUtils.isEmpty(rootElementName)) {
                rootElementName = "xml";
            }

            StringBuilder strBuilder = new StringBuilder();
            if (!fragmentFlag) {
                strBuilder.append("<?xml version=\"1.0\" encoding=\"").append(encoding).append("\" standalone=\"yes\"?>\n");
            }

            strBuilder.append("<").append(rootElementName).append(">\n");
            Set<?> objSet = dataMap.keySet();
            Iterator var6 = objSet.iterator();

            while(var6.hasNext()) {
                Object key = var6.next();
                if (null != key) {
                    strBuilder.append("<").append(key.toString()).append(">");
                    strBuilder.append(dataMap.get(key));
                    strBuilder.append("</").append(key.toString()).append(">\n");
                }
            }

            strBuilder.append("</").append(rootElementName).append(">");
            return strBuilder.toString();
        }
    }

    public static Map<String, Object> getMapFromXML(String xml) throws ConvertException {
        HashMap map = new HashMap();

        try {
            Document document = DocumentHelper.parseText(xml);
            Element rootElement = document.getRootElement();
            Iterator elements = rootElement.elementIterator();

            while(elements.hasNext()) {
                Element element = (Element)elements.next();
                map.put(element.getName(), element.getText());
            }

            return map;
        } catch (DocumentException var6) {
            throw new ConvertException(var6);
        }
    }
}
