package fitness_equipment.test.com.fitness_equipment.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HTTPRequestT {
    private HTTPRequestT() {

    }

    public static HttpURLConnection getConn(String url,
                                            HashMap<String, String> header, Map<String, String> param,
                                            String method) throws Exception {
        URL url2 = new URL(url);
        HttpURLConnection openConnection = (HttpURLConnection) url2
                .openConnection();
        openConnection.setRequestMethod(method);
        openConnection.setDoOutput(true);
        openConnection.setDoInput(true);
        openConnection.setUseCaches(false);

        Set<String> keySet = header.keySet();
        for (String string : keySet) {
            openConnection.addRequestProperty(string, header.get(string));
        }
        DataOutputStream outStream = new DataOutputStream(
                openConnection.getOutputStream());
        StringBuffer stringBuffer = new StringBuffer("{");
        Set<String> keySet1 = param.keySet();
        for (String string : keySet1) {
            if (stringBuffer.length() > 1) {
                stringBuffer.append(",");
            }
            stringBuffer.append("\"" + string + "\":\"" + param.get(string)
                    + "\"");
        }
        stringBuffer.append("}");
        outStream.write(stringBuffer.toString().getBytes());
        // outStream.close();
        return openConnection;
    }

    @SuppressWarnings("all")
    public static HttpURLConnection getImgUpdateConn(String url1,
                                                     HashMap<String, String> header, Map<String, Object> param,
                                                     String method) throws Exception {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        DataOutputStream ds = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        List<Map> list = (List<Map>) param.get("file");

        try {
            // 统一资源
            URL url = new URL(url1);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // 设置是否向httpUrlConnection输出
            httpURLConnection.setDoOutput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码连接参数
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置请求内容类型
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            Set<String> keySet1 = header.keySet();

            for (String string : keySet1) {
                httpURLConnection.addRequestProperty(string, header.get(string));
            }
            // 设置DataOutputStream
            ds = new DataOutputStream(httpURLConnection.getOutputStream());
            FileInputStream fStream = null;
//            Log.i("head-------------","null?"+header.toString());

            for (Map map : list) {
                Set<String> keySet = map.keySet();

                for (String key : keySet) {
                    String uploadFile = (String) map.get(key);
                    String filename = uploadFile.substring(uploadFile
                            .lastIndexOf("//") + 1);
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; "
                            + "name=\"" + key + "\";filename=\"" + filename
                            + "\"" + end);
                    ds.writeBytes(end);
                    fStream = new FileInputStream(uploadFile);
                    System.out.println(uploadFile);
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int length = -1;
                    while ((length = fStream.read(buffer)) != -1) {
                        ds.write(buffer, 0, length);
                    }

                }
                ds.writeBytes(end);
				/* close streams */

                System.out.println(twoHyphens + boundary + twoHyphens + end);

            }

            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
            ds.flush();
           if (httpURLConnection.getResponseCode() >= 300) {
              throw new Exception(
                      "HTTP Request is not success, Response code is "
                               + httpURLConnection.getResponseCode());
           }
            return httpURLConnection;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("error",e.getLocalizedMessage());
        }
        return null;
    }

    public static String getResponseString(HttpURLConnection openConnection)
            throws IOException {
        InputStream in = openConnection.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(in, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            buffer.append(line);
        }
        in.close();
        openConnection.disconnect();
        return buffer.toString();
    }
}