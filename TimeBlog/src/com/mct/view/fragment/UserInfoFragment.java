package com.mct.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mct.blog.R;
import com.mct.model.User;
import com.mct.utils.BitmapUtil;
import com.mct.utils.LoadIconManager;
import com.mct.utils.UserUtil;

/**
 * 用户信息碎片
 * 
 * @author Whunf
 * 
 */
public class UserInfoFragment extends Fragment {
	private View mainLayout;
	private ImageView iHeadIcon;
	private TextView tvName, tvNick, tvSex, tvUserId, tvPhone, tvBirth;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mainLayout = inflater.inflate(R.layout.fragment_me, null);
		return mainLayout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		iHeadIcon = (ImageView) mainLayout.findViewById(R.id.im_icon);
		tvName = (TextView) mainLayout.findViewById(R.id.tv_user_name);
		tvSex = (TextView) mainLayout.findViewById(R.id.tv_sex);
		tvUserId = (TextView) mainLayout.findViewById(R.id.tv_user_id);
		tvNick = (TextView) mainLayout.findViewById(R.id.tv_nick);
		tvPhone = (TextView) mainLayout.findViewById(R.id.tv_phone);
		tvBirth = (TextView) mainLayout.findViewById(R.id.tv_birth);
		showUserInfo(UserUtil.loginUser);
	}

	private void showUserInfo(User user) {
		String name = user.getUserName();
		tvName.setText(name);
		tvUserId.setText(String.valueOf(user.getId()));
		if (user.getNick() != null && !"".equals(user.getNick())) {
			tvNick.setText(user.getNick());
		}
		if (user.getPhone() != null && !"".equals(user.getPhone())) {
			tvPhone.setText(user.getPhone());
		}
		if (user.getBirth() != null && !"".equals(user.getBirth())) {
			tvBirth.setText(user.getBirth());
		}
		if (user.getSex() != null && !"".equals(user.getSex())) {
			tvSex.setText(user.getSex());
		}
		boolean isHasIconInRemote = user.getPhoto() != null
				&& !"".equals(user.getPhoto());
		LoadIconManager.getInstance().showHeadIcon(getActivity(), iHeadIcon,
				name, user.getPhoto(), l);
	}

	private LoadIconManager.OnDownloadListener l = new LoadIconManager.OnDownloadListener() {

		@Override
		public void onDownloadOK(String userName) {
			// TODO Auto-generated method stub
			BitmapUtil.getBitmap(getActivity(), userName, true, iHeadIcon);
		}

		@Override
		public void onDownloadFail(String userName) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "头像加载失败", 3000).show();
			BitmapUtil.getBitmap(getActivity(), userName, false, iHeadIcon);
		}
	};
}
