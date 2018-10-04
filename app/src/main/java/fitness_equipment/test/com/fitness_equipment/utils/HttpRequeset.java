package fitness_equipment.test.com.fitness_equipment.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 陈姣姣 on 2017/12/7.
 */

public class HttpRequeset {
    private HttpRequeset(){

    }
    public static HttpURLConnection getConn(String url, HashMap<String, String> header
            , Map<String,String> param, String method) throws Exception{
        URL url2 = new URL(url);
        HttpURLConnection openConnection =(HttpURLConnection) url2.openConnection();
        openConnection.setRequestMethod(method);
        openConnection.setDoOutput(true);
        openConnection.setDoInput(true);
        openConnection.setUseCaches(false);


        Set<String> keySet = header.keySet();
        for (String string : keySet) {
            openConnection.addRequestProperty(string, header.get(string));
        }
        DataOutputStream outStream = new DataOutputStream(openConnection.getOutputStream());
        StringBuffer stringBuffer = new StringBuffer("{");
        Set<String> keySet1 = param.keySet();
        for (String string : keySet1) {
            if(stringBuffer.length()>1){
                stringBuffer.append(",");
            }
            stringBuffer.append("\""+string+"\":\""+param.get(string)+"\"");
        }
        stringBuffer.append("}");
        outStream.write(stringBuffer.toString().getBytes());
        outStream.close();
        return openConnection;
    }
    public static String getResponseString(HttpURLConnection openConnection) throws IOException {
        InputStream in = openConnection.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = bufferedReader.readLine()) != null){
            buffer.append(line);
        }
        in.close();
        openConnection.disconnect();
        return buffer.toString();
    }

}
