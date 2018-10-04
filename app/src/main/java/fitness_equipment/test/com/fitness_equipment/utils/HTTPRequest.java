package fitness_equipment.test.com.fitness_equipment.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HTTPRequest {
    static String str="";
    private HTTPRequest(){

    }
    public static synchronized String Request(final String url,final  HashMap < String, String > header
            ,final  Map < String, String > param,final  String method ,final HTTPUtil http){
        str=null;
        Thread thread = new Thread(new Runnable() {
            public String getConn() throws Exception {
                URL url2 = new URL(url);
                HttpURLConnection openConnection = (HttpURLConnection) url2.openConnection();
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
                    if (stringBuffer.length() > 1) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append("\"" + string + "\":\"" + param.get(string) + "\"");
                }
                stringBuffer.append("}");
                outStream.write(stringBuffer.toString().getBytes());
                outStream.close();
                InputStream in = openConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }
                in.close();
                openConnection.disconnect();
                System.gc();
                return buffer.toString();
            }


            public void run() {
                try {

                    str = getConn();
                    http.doSome(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while (true){
            if(str!=null){
                return str;
            }
        }

    }
}