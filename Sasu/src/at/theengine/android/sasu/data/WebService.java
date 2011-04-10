//SaSu (Share and Shorten Url) for Android by Bruno Hautzenberger 2011
//
//This file is part of SaSu.
//
//SaSu is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//SaSu is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with SaSu.  If not, see <http://www.gnu.org/licenses/>.

package at.theengine.android.sasu.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class WebService {
	
	private static String URL = "https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyCb74-cvjKlKPWo_4Azj36Y2tL3gmfEAQ0";


	public static ShortUrl shortenUrl(String longUrl) throws IOException, JSONException{
		String resp = Post(buildJSON(longUrl));
		
		JSONObject jsonResp = new JSONObject(resp);
		
		return new ShortUrl(jsonResp);
	}
	

	private static String Post(JSONObject data) throws IOException{
            URL url1;
            URLConnection urlConnection;
            DataOutputStream outStream;
            DataInputStream inStream;
     
            String body = data.toString();
            
            // Create connection
            url1 = new URL(URL);
            urlConnection = url1.openConnection();
            ((HttpURLConnection)urlConnection).setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            //urlConnection.setRequestProperty("Content-Length", ""+ body.length());
     
            // Create I/O streams
            outStream = new DataOutputStream(urlConnection.getOutputStream());
     
            // Send request
            outStream.writeBytes(body);
            outStream.flush();
            outStream.close();
     
            inStream = new DataInputStream(urlConnection.getInputStream()); 
            
            // Get Response  
            String buffer;  
            StringBuilder response = new StringBuilder();
            while((buffer = inStream.readLine()) != null) {  
                response.append(buffer);
            }  
            
            // Close I/O streams
            inStream.close();
            
            return response.toString();    	
    }
	
	public static String getQr(String shortUrl,Context context) throws IOException{
        URL url1;
        URLConnection urlConnection;
        DataInputStream inStream;
        
        // Create connection
        url1 = new URL(shortUrl + ".qr");
        urlConnection = url1.openConnection();
        ((HttpURLConnection)urlConnection).setRequestMethod("GET");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(false);
        urlConnection.setUseCaches(false);
 
        inStream = new DataInputStream(urlConnection.getInputStream()); 
        
        File file = FileIO.getTempFile(context);
        
        FileIO.saveQr(inStream, file.getPath());
        
        return file.getPath();   	
}

	private static JSONObject buildJSON(String longUrl) throws JSONException{    	
    	JSONObject json = new JSONObject();
    	
    	json.put("longUrl", longUrl);

    	return json;
    }

}
