package eg.edu.alexu.csd.oop.game;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileTypeFilter extends FileFilter{
	
	private final String extension;
	private final String description;
	
	public FileTypeFilter(String extension, String description) {
		// TODO Auto-generated constructor stub
		this.extension=extension;
		this.description=description;
	}

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if(f.isDirectory()){
			return true;
		}
		return f.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description+String.format(" (*%s)", extension);
	}

}
