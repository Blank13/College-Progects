package eg.edu.alexu.csd.oop.draw;

import java.io.File;

import javax.swing.filechooser.*;


public class FileTypeFilter extends FileFilter{
	private  String extension ;
	private  String description;
	
	public FileTypeFilter(String e , String d) {
		this.extension = e;
		this.description = d;
	}
	@Override
	public boolean accept(File file) {
		if(file.isDirectory()){
			return true;
		}
		return file.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {
		return description + String.format(" (*%s)", extension);
	}

}
