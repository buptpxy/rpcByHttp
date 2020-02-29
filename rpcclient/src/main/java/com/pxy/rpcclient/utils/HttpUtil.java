package com.pxy.rpcclient.utils;

import com.alibaba.fastjson.JSON;
import com.pxy.rpcclient.rpc.Result;
import com.pxy.rpcclient.rpc.RpcParam;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {
    public static final int SERVER_PORT = 8002;
    public static final String SERVER_IP = "127.0.0.1";
    public static final String PATH = "/";
    public static final String HTTP_URL = "http://"+ SERVER_IP + ":" + SERVER_PORT + PATH;
    public static final int OK_STATUS = 200;

    public static synchronized Result callRemoteService(RpcParam rpcParam) {
        List<NameValuePair> paramsList = getNameValuePairs(rpcParam);
        Result rpcResult = getRpcResult(paramsList);
        return rpcResult;
    }

    private static Result getRpcResult(List<NameValuePair> paramsList) {
        try {
            String result = sendPost(HTTP_URL, paramsList);
            return JSON.parseObject(result, Result.class);
        } catch (Exception ex) {
            return Result.getFailResult("触发远程调用失败");
        }
    }

    private static List<NameValuePair> getNameValuePairs(RpcParam rpcParam) {
        List<NameValuePair> paramsList = new ArrayList<>();
        paramsList.add(new BasicNameValuePair("className", rpcParam.getClassName()));
        paramsList.add(new BasicNameValuePair("methodName", rpcParam.getMethodName()));
        paramsList.add(new BasicNameValuePair("argTypes", rpcParam.getArgTypes()));
        paramsList.add(new BasicNameValuePair("argValues", rpcParam.getArgValues()));
        return paramsList;
    }

    // 根据远程服务的url和nameValuePairList来发送POST请求
    private static synchronized String sendPost(String url, List<NameValuePair> nameValuePairList)throws IOException {
        CloseableHttpResponse response = getPostResponse(url,nameValuePairList);
        String result = "";
        if (isSuccess(response)) {
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        if (response != null) {
            response.close();
        }
        return result;
    }

    private static boolean isSuccess(CloseableHttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        return OK_STATUS == statusCode;
    }

    private static CloseableHttpResponse getPostResponse(String url, List<NameValuePair> nameValuePairList) throws IOException {
        HttpPost post = getHttpPost(url, nameValuePairList);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);
        return response;
    }

    private static HttpPost getHttpPost(String url, List<NameValuePair> nameValuePairList) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");;
        post.setEntity(entity);
        return post;
    }

}
