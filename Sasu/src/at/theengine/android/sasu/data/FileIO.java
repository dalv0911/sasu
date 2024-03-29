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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

public class FileIO {

	public static File getTempFile(Context context){
  	  final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName());
  	  if(!path.exists()){
  	    path.mkdir();
  	  }
  	  return new File(path, "qr.png");
  }
	
  public static void saveQr(InputStream stream, String path) throws IOException{
      BufferedInputStream in = new BufferedInputStream(stream);
      FileOutputStream file = new FileOutputStream(path);
      BufferedOutputStream out = new BufferedOutputStream(file);
      int i;
      while ((i = in.read()) != -1) {
          out.write(i);
      }
      out.flush();
  }
	
}
