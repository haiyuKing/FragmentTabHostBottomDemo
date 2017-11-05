package com.why.project.fragmenttabhostbottomdemo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.why.project.fragmenttabhostbottomdemo.R;


/**
 * Created by HaiyuKing
 * Used 首页界面——我的碎片界面
 */

public class ContactFragment extends BaseFragment{

	private static final String TAG = "ContactFragment";
	/**View实例*/
	private View myView;

	private TextView tv_homef;

	/**传递过来的参数*/
	private String bundle_param;

	//重写
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//使用FragmentTabHost时，Fragment之间切换时每次都会调用onCreateView方法，导致每次Fragment的布局都重绘，无法保持Fragment原有状态。
		//http://www.cnblogs.com/changkai244/p/4110173.html
		if(myView==null){
			myView = inflater.inflate(R.layout.fragment_home_contact, container, false);
			//接收传参
			Bundle bundle = this.getArguments();
			bundle_param = bundle.getString("param");
		}
		//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) myView.getParent();
		if (parent != null) {
			parent.removeView(myView);
		}
		//普通写法，如果换成这个方式，那么bundle_param的值不会发生任何变化
//		myView = inflater.inflate(R.layout.fragment_home_contact, container, false);
//		//接收传参
//		Bundle bundle = this.getArguments();
//		bundle_param = bundle.getString("param");

		return myView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		//初始化控件以及设置
		initView();
		//初始化数据
		initData();
		//初始化控件的点击事件
		initEvent();
	}
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tv_homef = (TextView) myView.findViewById(R.id.tv_homef);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		Log.w("tag","{iniData}bundle_param" + bundle_param);
		tv_homef.setText(tv_homef.getText() + "--" + bundle_param);
	}

	/**
	 * 初始化点击事件
	 * */
	private void initEvent(){
	}

	public void setBundle_param(String bundle_param) {
		this.bundle_param = bundle_param;
	}
}
