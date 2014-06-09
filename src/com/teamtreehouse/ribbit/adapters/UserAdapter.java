package com.teamtreehouse.ribbit.adapters;

import java.util.Date;
import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.R.drawable;
import com.teamtreehouse.ribbit.R.id;
import com.teamtreehouse.ribbit.R.layout;
import com.teamtreehouse.ribbit.utils.MD5Util;
import com.teamtreehouse.ribbit.utils.ParseConstants;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<ParseUser> {
	protected Context mContext;
	protected List<ParseUser> mUsers;
	
	public UserAdapter(Context context, List<ParseUser> users) {
		super(context, R.layout.user_item, users);
		mContext = context;
		mUsers = users;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder holder;
		
		if (rowView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			rowView = inflater.inflate(R.layout.user_item, null);
			holder = new ViewHolder();
			holder.userImageView = (ImageView) rowView.findViewById(R.id.uesrImageView);
			//holder.nameLabel = (TextView) rowView.findViewById(R.id.senderLabel);
			holder.friendsLabel = (TextView)rowView.findViewById(R.id.userLabel);
			rowView.setTag(holder);
		}
		else {
			holder = (ViewHolder) rowView.getTag();
		}
		
		ParseUser user = mUsers.get(position);
		String email = user.getEmail().toLowerCase();
		if (email.equals("")) {
			holder.userImageView.setImageResource(R.drawable.avatar_empty);
		}
		else {
			String hash = MD5Util.md5Hex(email);
			String imageUrl = "http://www.gravatar.com/avatar/" + hash + "?s=204&d=404";
			
			Picasso.with(mContext)
			.load(imageUrl)
			.placeholder(R.drawable.avatar_empty)
			.into(holder.userImageView);
		}
		
		//if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
		//	holder.iconImageView.setImageResource(R.drawable.ic_picture);
		//}
		//else
		//	holder.iconImageView.setImageResource(R.drawable.ic_video);
		
		holder.friendsLabel.setText(user.getUsername());
		
		
		return rowView;
	}

	private class ViewHolder {
		ImageView userImageView;
		TextView friendsLabel;
	}
	
	public void refill(List<ParseUser> messages) {
		mUsers.clear();
		mUsers.addAll(messages);
		notifyDataSetChanged();
	}
}












