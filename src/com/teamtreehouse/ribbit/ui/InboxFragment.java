package com.teamtreehouse.ribbit.ui;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.R.layout;
import com.teamtreehouse.ribbit.adapters.MessageAdapter;
import com.teamtreehouse.ribbit.utils.ParseConstants;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class InboxFragment extends ListFragment {
	protected List<ParseObject> mMessages;
	protected SwipeRefreshLayout mSwipeRefreshLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_inbox,
				container, false);
		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreseLayout);
		mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);

		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		retrieveMessage();
	}

	private void retrieveMessage() {
		ParseQuery<ParseObject> query = new ParseQuery<>(ParseConstants.CLASS_MESSAGES);
		query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
		query.orderByDescending(ParseConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {
					if (mSwipeRefreshLayout.isRefreshing()) {
						mSwipeRefreshLayout.setRefreshing(false);
					}
					mMessages = messages;
					String[] usernames = new String[mMessages.size()];
					int i = 0;
					for(ParseObject message : mMessages) {
						usernames[i] = message.getString(ParseConstants.KEY_SENDER_NAME);
						i++;
					}
					
					if (getListView().getAdapter() == null) {
						MessageAdapter adapter = new MessageAdapter(getListView().getContext(), mMessages);
						setListAdapter(adapter);
					}
					else {
						((MessageAdapter)getListView().getAdapter()).refill(mMessages);
					}
				}
			}
		});
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		ParseObject message = mMessages.get(position);
		String fileType = message.getString(ParseConstants.KEY_FILE_TYPE);
		ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
		Uri uri = Uri.parse(file.getUrl());
		
		if (fileType.equals(ParseConstants.TYPE_IMAGE)) {
			Intent intent = new Intent(getActivity(), ViewImageActicity.class);
			intent.setData(uri);
			startActivity(intent);
		}
		else {
			//watch video
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.setDataAndType(uri, "video/*");
			startActivity(intent);
		}
		
		List<String> ids = message.getList(ParseConstants.KEY_RECIPIENT_IDS);
		if (ids.size() == 1) {
			//delete object
			message.deleteInBackground();
		}
		else {
			//delete recipient
			ids.remove(ParseUser.getCurrentUser().getObjectId());
			ArrayList<String> idsToRemove = new ArrayList<>();
			idsToRemove.add(ParseUser.getCurrentUser().getObjectId());
			message.removeAll(ParseConstants.KEY_RECIPIENT_IDS, idsToRemove);
			message.saveInBackground();
		}
	}
	
	protected OnRefreshListener mRefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
			Toast.makeText(getActivity(), "You are refreshing", Toast.LENGTH_SHORT).show();
			retrieveMessage();
		}
	};

}
