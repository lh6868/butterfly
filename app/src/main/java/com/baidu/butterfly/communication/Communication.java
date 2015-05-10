package com.baidu.butterfly.communication;



import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lh6868 on 2015/5/10.
 */
public  class Communication {
    final static String url = "http://lihui6868.xicp.net:16078/butterfly/Butterfly/SendandReceive";


    public static String doPost(String userUame){
        String responseStr = "-1";
        try {
            HttpPost httpRequest = new HttpPost(url);
            HttpParams params = new BasicHttpParams();
            ConnManagerParams.setTimeout(params, 1000); //从连接池中获取连接的超时时间
            HttpConnectionParams.setConnectionTimeout(params, 3000);//通过网络与服务器建立连接的超时时间
            HttpConnectionParams.setSoTimeout(params, 5000);//读响应数据的超时时间
            httpRequest.setParams(params);
            //下面开始跟服务器传递数据，使用BasicNameValuePair
            List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
            // String name = nameEdit.getText().toString().trim();
            String name = userUame;
            //String code = codeEdit.getText().toString().trim();
            // String code = userpw;
            paramsList.add(new BasicNameValuePair("NAME", name));
            //paramsList.add(new BasicNameValuePair("CODE", code));
            UrlEncodedFormEntity mUrlEncodeFormEntity = new UrlEncodedFormEntity(paramsList, HTTP.UTF_8);
            httpRequest.setEntity(mUrlEncodeFormEntity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            final int ret = httpResponse.getStatusLine().getStatusCode();
            if(ret == HttpStatus.SC_OK){
                responseStr = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                System.out.println("IO SUCCESS");
            }else{
                System.out.println("IO ERROR");
                responseStr = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                System.out.println(responseStr);
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return responseStr;
    }
}
