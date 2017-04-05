package phub.Whiteboard;

import java.io.File;

public class Picture extends WhiteboardItem {

	public java.io.File getFile() {
		return File;
	}

	public void setFile(java.io.File file) {
		File = file;
	}

	private File File;

	@Override
	void paintUsing(Paintable paintable) {

	}
}