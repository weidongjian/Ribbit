package com.teamtreehouse.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class RibbitApplication extends Application {
	
	@Override
	public void onCreate() { 
		super.onCreate();
	    Parse.initialize(this, "NkSX5F1euzX7EiiaJvKgHkbMWD7WCsBo0LrH7HGC", "B0jJ11ALr9ZBJfwJSF56fZ46XNmQ4wL6rkGQgFHJ");
	}
}
