package com.onlineMIS.common;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.filter.SystemParm;

public class HttpUtil {
	 
	public static String callRemoteService(String remoteURL, String param) throws MalformedURLException, IOException{
		StringBuffer temp = new StringBuffer();
		HttpURLConnection uc = (HttpURLConnection)new URL(remoteURL).
		                               openConnection();
		        uc.setConnectTimeout(10000);
		        uc.setDoOutput(true);
		        uc.setRequestMethod("GET");
		        uc.setUseCaches(false);
		        DataOutputStream out = new DataOutputStream(uc.getOutputStream());
		
		        out.writeBytes(param);
		        out.flush();
		        out.close();
		        InputStream in = new BufferedInputStream(uc.getInputStream());
		        Reader rd = new InputStreamReader(in, "UTF-8");
		        int c = 0;
		        while ((c = rd.read()) != -1) {
		            temp.append((char) c);
		        }
		        System.out.println(temp.toString());
		        in.close();
		return temp.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
