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

import org.json.JSONException;
import org.json.JSONObject;


public class ShortUrl {

	private String kind;
	private String id;
	private String longUrl;
	
	private Error error;
	
	private boolean isOk;
	
	public ShortUrl(JSONObject json) throws JSONException{
		if(json.has("error")){ //error response
			JSONObject jsonError = json.getJSONObject("error");
			this.error = new Error(jsonError.getString("code"), jsonError.getString("message"));
			isOk = false;
		} else {
			this.kind = json.getString("kind");
			this.id = json.getString("id");
			this.longUrl = json.getString("longUrl");
			isOk = true;
		}
	}

	public String getKind() {
		return kind;
	}

	public String getId() {
		return id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public Error getError() {
		return error;
	}

	public boolean isOk() {
		return isOk;
	}
	
	
}
