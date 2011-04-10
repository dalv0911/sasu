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

package at.theengine.android.sasu;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import at.theengine.android.sasu.data.*;

public class SasuApp extends Activity {
	
	
	public static String TAG = "SaSu";

	private EditText etUrl;
	private TextView tvPowered;
	private Button btnShareUrl;
	private Button btnShareQr;
	private Button btnReset;
	private Button btnShorten;
	private ImageView ivQr;
	
	private Context ctx;

	private ProgressDialog pdlg;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ctx = this;
        
        etUrl = (EditText) findViewById(R.id.etUrl);
        
        ivQr = (ImageView) findViewById(R.id.ivQr);

        btnShareUrl = (Button) findViewById(R.id.btnShareUrl);
    	btnShareQr = (Button) findViewById(R.id.btnShareQr);
    	btnReset = (Button) findViewById(R.id.btnReset);
    	btnShorten = (Button) findViewById(R.id.btnShorten);
    	
    	tvPowered = (TextView) findViewById(R.id.tvPowered);
    	Linkify.addLinks(tvPowered, Linkify.ALL);
        
    	btnShorten.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				shortenUrl();
			} 
        });
    	
    	btnReset.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				reset();
			} 
        });
    	
    	btnShareUrl.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
	    		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, etUrl.getText().toString());
	    		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, etUrl.getText().toString());
	    		startActivity(Intent.createChooser(shareIntent, "Share"));
			} 
        });
    	
    	btnShareQr.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
				shareIntent.setType("image/jpeg");
	            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(FileIO.getTempFile(ctx)));
	    		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, etUrl.getText().toString());
	    		startActivity(Intent.createChooser(shareIntent, "Share"));
			} 
        });
    	
    	reset();
    	
    	try {
    		etUrl.setText(getIntent().getExtras().get(Intent.EXTRA_TEXT).toString());
    		shortenUrl();
    	} catch (Exception e){
    	}
    }
    
    private void reset(){
    	etUrl.setText("");
    	btnShareUrl.setVisibility(View.GONE);
    	btnShareQr.setVisibility(View.GONE);
    	btnReset.setVisibility(View.GONE);
    	
    	ivQr.setVisibility(View.GONE);
    	
    	btnShorten.setVisibility(View.VISIBLE);
    }
    
    private void shortenUrl(){
    	pdlg = ProgressDialog.show(this,"", "getting short URL...", true); 
    	pdlg.show();
    	
    	class ObjectsLoader extends AsyncTask<Void, String, Void> {
    		
    		String msg = "";
    		ShortUrl su;
	    	String qr;
    		
    		@Override
    		protected Void doInBackground(Void... unused) {
    			try {
    				if(etUrl.getText().toString().length() > 10){
    					su = WebService.shortenUrl(etUrl.getText().toString());
    					if(su.isOk()){
    						qr = WebService.getQr(su.getId(), ctx);
    					}
    				} else {
    					msg = "Invalid URL!";
    				}
    			}catch (IOException e) {
    				msg = e.toString() + ": " + e.getMessage();
    			} catch (JSONException e) {
    				msg = e.toString() + ": " + e.getMessage();
    			} catch(Exception e){
    				msg = e.toString() + ": " + e.getMessage();
    			} 
    			
    			return(null);
    		}

    		@Override
    		protected void onPostExecute(Void unused) {
    			pdlg.dismiss();
    			
    			if(!msg.equals("")){
    				Toast.makeText(ctx, msg , Toast.LENGTH_LONG).show();
    			} else {
    				if(su.isOk()){
    					etUrl.setText(su.getId());
						
						Drawable d = Drawable.createFromPath(qr);
		                ivQr.setImageDrawable(d);
		                
		                ivQr.setVisibility(View.VISIBLE);
						btnShareUrl.setVisibility(View.VISIBLE);
				    	btnShareQr.setVisibility(View.VISIBLE);
				    	btnReset.setVisibility(View.VISIBLE);
				    	
				    	btnShorten.setVisibility(View.GONE);
					} else {
						Toast.makeText(ctx, su.getError().getCode() + " - " + su.getError().getMessage() , Toast.LENGTH_LONG).show();
					}
    			}
    		}
    	}
    	
    	new ObjectsLoader().execute();
		
    }
}