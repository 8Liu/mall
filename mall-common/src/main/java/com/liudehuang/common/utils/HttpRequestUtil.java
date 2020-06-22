package com.liudehuang.common.utils;

import com.liudehuang.common.constant.CharacterConstant;
import com.liudehuang.common.exception.HttpRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpRequestUtil {

    protected HttpRequestUtil() {

    }

    /**
     * 连接时间
     */
    private static int connectTimeout = 30000;

    /**
     * 响应时间
     */
    private static int readTimeout = 60000;


    /**
     * MAP数据转换成HTTP参数格式的字符串
     *
     * @param requestMap
     * @return String
     */
    public static String toHttpParamString(Map<String, Object> requestMap) {
        StringBuilder result = new StringBuilder();
        if (requestMap != null && !requestMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
                if (null != entry.getValue() && !"".equals(entry.getValue())) {
                    result.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
            }
            result = new StringBuilder(result.substring(0, result.length() - 1));
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param)
            throws HttpRequestException {
        return sendGet(url, param, "application/x-www-form-urlencoded");
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendJsonGet(String url, String param)
            throws HttpRequestException {
        return sendGet(url, param, "application/json");
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url         发送请求的URL
     * @param param       请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param contentType 媒体类型
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String contentType)
            throws HttpRequestException {
        return sendGet(url, param, contentType, connectTimeout, readTimeout,null);
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url            发送请求的URL
     * @param param          请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param contentType    媒体类型
     * @param connectTimeout 连接时间
     * @param readTimeout    响应时间
     * @param headerMap    消息头
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url,
                                 String param,
                                 String contentType,
                                 int connectTimeout,
                                 int readTimeout,
                                 Map headerMap)
            throws HttpRequestException {
        // 创建请求头
        if (headerMap==null){
            headerMap=new HashMap();
        }
        headerMap.put("Content-Type", contentType);
        headerMap.put("accept", "*/*");
        headerMap.put("connection", "Keep-Alive");
        headerMap.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        if (StringUtils.isNotEmpty(param)) {
            if (url.indexOf("?") > 0) {
                url = url + "&" + param;
            } else {
                url = url + "?" + param;
            }
        }

        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            setRequestProperty(conn, headerMap, connectTimeout, readTimeout);
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            // Map<String, List<String>> map = conn.getHeaderFields();
            // 读取返回内容
            return getInputStreamContent(conn.getInputStream());
        } catch (Exception e) {
            throw new HttpRequestException(e.getMessage());
        } finally {
            // 使用finally块来关闭输入流
            closeInputStreamReader(inputStreamReader);
            closeBufferedReader(bufferedReader);
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url            发送请求的URL
     * @param param          请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param headerMap    消息头
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url,
                                 String param,
                                 Map headerMap)
            throws HttpRequestException {
        return sendGet(url, param, "application/json", connectTimeout, readTimeout,headerMap);
    }

    /**
     * 向指定微信平台URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendHttpsGet(String url, String param)
            throws HttpRequestException {

        if (StringUtils.isNotEmpty(param)) {
            if (url.indexOf("?") > 0) {
                url = url + "&" + param;
            } else {
                url = url + "?" + param;
            }
        }

        try {
            HttpClientBuilder httpClient = HttpClients.custom();
            httpClient.setSSLHostnameVerifier(new NoopHostnameVerifier());
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient.build());

            RestTemplate restTemplate = new RestTemplate(requestFactory);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new HttpRequestException(e.getMessage());
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param)
            throws HttpRequestException {
        return sendPost(url, param, "application/x-www-form-urlencoded");
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendJsonPost(String url, String param)
            throws HttpRequestException {
        return sendPost(url, param, "application/json");
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url         发送请求的 URL
     * @param param       请求参数
     * @param contentType 媒体类型
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, String contentType)
            throws HttpRequestException {
        return sendPost(url, param, contentType, connectTimeout, readTimeout);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url            发送请求的 URL
     * @param param          请求参数
     * @param contentType    媒体类型
     * @param connectTimeout 连接时间
     * @param readTimeout    响应时间
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url,
                                  String param,
                                  String contentType,
                                  int connectTimeout,
                                  int readTimeout)
            throws HttpRequestException {
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", contentType);
        return sendPost(url, param, connectTimeout, readTimeout, headerMap);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url       发送请求的 URL
     * @param param     请求参数
     * @param headerMap 请求头
     * @return 所代表远程资源的响应结果
     */
    public static String sendJsonPost(String url,
                                      String param,
                                      Map<String, String> headerMap)
            throws HttpRequestException {
        return sendJsonPost(url, param, connectTimeout, readTimeout, headerMap);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url            发送请求的 URL
     * @param param          请求参数
     * @param connectTimeout 连接时间
     * @param readTimeout    响应时间
     * @param headerMap      请求头
     * @return 所代表远程资源的响应结果
     */
    public static String sendJsonPost(String url,
                                      String param,
                                      int connectTimeout,
                                      int readTimeout,
                                      Map<String, String> headerMap)
            throws HttpRequestException {
        if (null == headerMap) {
            headerMap = new HashMap<String, String>();
        }
        headerMap.put("Content-Type", "application/json");
        return sendPost(url, param, connectTimeout, readTimeout, headerMap);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url            发送请求的 URL
     * @param param          请求参数
     * @param headerMap      请求头
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url,
                                  String param,
                                  Map<String, String> headerMap)
            throws HttpRequestException {
        return sendPost(url,param,connectTimeout,readTimeout,headerMap);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url            发送请求的 URL
     * @param param          请求参数
     * @param connectTimeout 连接时间
     * @param readTimeout    响应时间
     * @param headerMap      请求头
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url,
                                  String param,
                                  int connectTimeout,
                                  int readTimeout,
                                  Map<String, String> headerMap)
            throws HttpRequestException {
        // 增加请求头信息
        headerMap.put("accept", "*/*");
        headerMap.put("connection", "Keep-Alive");
        headerMap.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        OutputStreamWriter outputStreamWriter = null;
        PrintWriter printWriter = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            setRequestProperty(conn, headerMap, connectTimeout, readTimeout);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置编码 防止到服务端出现中文乱码
            outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), CharacterConstant.UTF8);
            printWriter = new PrintWriter(outputStreamWriter, true);
            // 发送请求参数
            printWriter.print(param);
            // flush输出流的缓冲
            printWriter.flush();
            // 读取返回内容
            return getInputStreamContent(conn.getInputStream());
        } catch (Exception e) {
            throw new HttpRequestException(e.getMessage());
        } finally {
            //使用finally块来关闭输出流、输入流
            closeOutputStreamWriter(outputStreamWriter);
            closePrintWriter(printWriter);
        }
    }

    /**
     * 发送HTTP POST请求
     *
     * @param url    发送请求的 URL
     * @param params 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, Map<String, Object> params)
            throws HttpRequestException {
        return httpPost(url, params, connectTimeout, readTimeout);
    }

    /**
     * 发送HTTP POST请求
     *
     * @param url            发送请求的 URL
     * @param params         请求参数
     * @param connectTimeout 连接时间
     * @param readTimeout    响应时间
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url,
                                  Map<String, Object> params,
                                  int connectTimeout,
                                  int readTimeout)
            throws HttpRequestException {
        // 创建请求头信息
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");

        // 构建请求参数
        String paramStr = toHttpParamString(params);

        // 尝试发送请求
        HttpURLConnection conn = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            setRequestProperty(conn, headerMap, connectTimeout, readTimeout);
            // POST 只能为大写，严格限制，post会不识别
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), CharacterConstant.UTF8);
            // 发送请求参数
            outputStreamWriter.write(paramStr);
            // flush输出流的缓冲
            outputStreamWriter.flush();
            // 读取返回内容
            return getInputStreamContent(conn.getInputStream());
        } catch (Exception e) {
            throw new HttpRequestException(e.getMessage());
        } finally {
            //使用finally块来关闭连接、输出流、输入流
            closeOutputStreamWriter(outputStreamWriter);
            disconnectConnection(conn);
        }
    }

    /**
     * 设置通用的请求属性
     *
     * @param conn
     * @param headerMap
     * @param connectTimeout 连接时间
     * @param readTimeout    响应时间
     */
    private static void setRequestProperty(URLConnection conn,
                                           Map<String, String> headerMap,
                                           int connectTimeout,
                                           int readTimeout) {
        // 设置日志链路ID值
        //conn.setRequestProperty(LogConstant.LOG_TRACE_ID, MDC.get(LogConstant.LOG_TRACE_ID));
        // 设置通用的请求属性
        if (null != headerMap && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                if (StringUtils.isNotEmpty(entry.getValue())) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
        }
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
    }

    /**
     * 读取返回内容
     *
     * @param inputStream
     * @return
     */
    private static String getInputStreamContent(InputStream inputStream) {
        // 读取返回内容
        StringBuilder buffer = new StringBuilder();
        if (null != inputStream) {

            BufferedReader bufferedReader = null;
            InputStreamReader inputStreamReader = null;
            try {
                // 定义BufferedReader输入流来读取URL的响应
                inputStreamReader = new InputStreamReader(inputStream, CharacterConstant.UTF8);
                bufferedReader = new BufferedReader(inputStreamReader);
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    buffer.append(temp).append("\n");
                }
            } catch (UnsupportedEncodingException e) {
                throw new HttpRequestException(e.getMessage());
            } catch (IOException e) {
                throw new HttpRequestException(e.getMessage());
            } finally {
                closeBufferedReader(bufferedReader);
                closeInputStreamReader(inputStreamReader);
            }
        }
        return buffer.toString();
    }

    /**
     * Disconnect Connection
     *
     * @param connection
     */
    private static void disconnectConnection(HttpURLConnection connection) {
        if (null != connection) {
            try {
                connection.disconnect();
            } catch (Exception e) {
                connection = null;
                log.error("Disconnect HttpURLConnection error: " + e.getMessage());
            }
        }
    }

    /**
     * 关闭 BufferedReader
     *
     * @param reader
     */
    private static void closeBufferedReader(BufferedReader reader) {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                reader = null;
                log.info("Close BufferedReader error: " + e.getMessage());
            }
        }
    }

    /**
     * 关闭 InputStreamReader
     *
     * @param streamReader
     */
    private static void closeInputStreamReader(InputStreamReader streamReader) {
        if (null != streamReader) {
            try {
                streamReader.close();
            } catch (IOException e) {
                streamReader = null;
                log.error("Close InputStreamReader error: " + e.getMessage());
            }
        }
    }

    /**
     * 关闭 OutputStreamWriter
     *
     * @param writer
     */
    private static void closeOutputStreamWriter(OutputStreamWriter writer) {
        if (null != writer) {
            try {
                writer.close();
            } catch (IOException e) {
                writer = null;
                log.error("Close OutputStreamWriter error: " + e.getMessage());
            }
        }
    }

    /**
     * 关闭 PrintWriter
     *
     * @param writer
     */
    private static void closePrintWriter(PrintWriter writer) {
        if (null != writer) {
            try {
                writer.close();
            } catch (Exception e) {
                writer = null;
                log.error("Close PrintWriter error: " + e.getMessage());
            }
        }
    }

}