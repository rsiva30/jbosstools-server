/**
 * JBoss, a Division of Red Hat
 * Copyright 2006, Red Hat Middleware, LLC, and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
* This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.ide.eclipse.as.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jboss.ide.eclipse.as.core.JBossServerCorePlugin;

public class FileUtil {
	
	// size of the buffer
	private static final int BUFFER = 10240;

	// the buffer
	private static byte[] buf = new byte[BUFFER];



	public static IStatus copyFile(String from, String to) {
		try {
			return copyFile(new FileInputStream(from), to);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static IStatus copyFile(File from, File to) {
		try {
			return copyFile(new FileInputStream(from), to);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	public static IStatus copyFile(FileInputStream in, File to) {
		try {
			return copyFile(in, new FileOutputStream(to));
		} catch( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static IStatus copyFile(FileInputStream in, String to) {
		FileOutputStream out = null;
	
		try {
			out = new FileOutputStream(to);
			return copyFile(in, out);
		} catch( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}

	public static IStatus copyFile(FileInputStream in, FileOutputStream out ) {
		try {
			int avail = in.read(buf);
			while (avail > 0) {
				out.write(buf, 0, avail);
				avail = in.read(buf);
			}
			return Status.OK_STATUS;
		} catch (Exception e) {
			return new Status(IStatus.ERROR, JBossServerCorePlugin.PLUGIN_ID, 0, e.getLocalizedMessage(), e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception ex) {
				// ignore
			}
			try {
				if (out != null)
					out.close();
			} catch (Exception ex) {
				// ignore
			}
		}

	}

	
	// Delete the file. If it's a folder, delete all children.
	// Also, if parent is now empty, delete that as well. 
	public static boolean safeDelete(File file) {
		boolean ret = true;
		if( file.isDirectory() ) {
			File[] children = file.listFiles();
			for( int i = 0; i < children.length; i++ ) {
				ret = ret && safeDelete(children[i]);
			}
		}
		ret = ret && file.delete();
		return ret;
	}
	
	public static boolean completeDelete(File file) {
		boolean ret = safeDelete(file);
		//delete all empty parent folders
		while(file.getParentFile().listFiles().length == 0 ) {
			file = file.getParentFile();
			ret = ret && file.delete();
		}
		return ret;
	}
	
	public static boolean fileSafeCopy(File src, File dest) {
		File parent = dest.getParentFile();
		parent.mkdirs();
		
		if (src.isDirectory())
		{
			File[] subFiles = src.listFiles();
			boolean copied = true;
			
			for (int i = 0; i < subFiles.length; i++)
			{
				File newDest = new File(dest, subFiles[i].getName());
				
				copied = copied && fileSafeCopy(subFiles[i], newDest);
			}
			return copied;
		}
		else {
			try {
			    FileInputStream fis  = new FileInputStream(src);
			    FileOutputStream fos = new FileOutputStream(dest);
			    byte[] buf = new byte[1024];
			    int i = 0;
			    while((i=fis.read(buf))!=-1) {
			      fos.write(buf, 0, i);
			      }
			    fis.close();
			    fos.close();
				return true;
			} catch( Exception e ) {
				return false;
			}
		}
	}

	
}
