package phub.Whiteboard;

import java.util.ArrayList;

public class Whiteboard {

	private ArrayList<WhiteboardItem> items;

	public Whiteboard(){
		items = new ArrayList<>();
	}

	private String Name;

	public void paintUsing(Paintable paintable){

	    for(WhiteboardItem x : items){
	        x.paintUsing(paintable);
        }
    }




}