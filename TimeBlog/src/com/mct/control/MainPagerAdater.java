package com.mct.control;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainPagerAdater extends FragmentStatePagerAdapter {
	private Fragment[] fragments;

	public MainPagerAdater(FragmentManager fm, Fragment[] fragments) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments[arg0];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.length;
	}

}
