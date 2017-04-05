package phub.Whiteboard;

import java.io.File;

public class Video extends WhiteboardItem {

	private File File;

	@Override
	void paintUsing(Paintable paintable) {
		paintable.paint(this);
	}
}