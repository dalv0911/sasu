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

public class Error {
	
	private String code;
	private String message;
	
	public Error(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
