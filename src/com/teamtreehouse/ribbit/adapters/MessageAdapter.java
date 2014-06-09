package com.teamtreehouse.ribbit.adapters;

import java.util.Date;
import java.util.List;

import com.parse.ParseObject;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.R.drawable;
import com.teamtreehouse.ribbit.R.id;
import com.teamtreehouse.ribbit.R.layout;
import com.teamtreehouse.ribbit.utils.ParseConstants;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<ParseObject> {
	protected Context mContext;
	protected List<ParseObject> mMessages;
	
	public MessageAdapter(Context context, List<ParseObject> messages) {
		super(context, R.layout.message_items, messages);
		mContext = context;
		mMessages = messages;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder holder;
		
		if (rowView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			rowView = inflater.inflate(R.layout.message_items, null);
			holder = new ViewHolder();
			holder.iconImageView = (ImageView) rowView.findViewById(R.id.messageIcon);
			holder.nameLabel = (TextView) rowView.findViewById(R.id.senderLabel);
			holder.timelabel = (TextView)rowView.findViewById(R.id.timeLabel);
			rowView.setTag(holder);
		}
		else {
			holder = (ViewHolder) rowView.getTag();
		}
		
		ParseObject message = mMessages.get(position);
		Date createdAt = message.getCreatedAt();
		long now = new Date().getTime();
		String convertDate = DateUtils.getRelativeTimeSpanString(createdAt.getTime(), now, DateUtils.SECOND_IN_MILLIS).toString();
		holder.timelabel.setText(convertDate);
		
		if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
			holder.iconImageView.setImageResource(R.drawable.ic_picture);
		}
		else
			holder.iconImageView.setImageResource(R.drawable.ic_video);
		
		holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));
		
		
		return rowView;
	}

	private class ViewHolder {
		ImageView iconImageView;
		TextView nameLabel;
		TextView timelabel;
	}
	
	public void refill(List<ParseObject> messages) {
		mMessages.clear();
		mMessages.addAll(messages);
		notifyDataSetChanged();
	}
}












