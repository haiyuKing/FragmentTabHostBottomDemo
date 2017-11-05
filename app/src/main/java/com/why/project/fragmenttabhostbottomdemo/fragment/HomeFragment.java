package com.why.project.fragmenttabhostbottomdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.why.project.fragmenttabhostbottomdemo.R;


/**
 * @Created HaiyuKing
 * @Used  首页界面——首页碎片界面
 */
public class HomeFragment extends BaseFragment{
	
	private static final String TAG = "HomeFragment";
	/**View实例*/
	private View myView;

	private TextView tv_homef;

	/**传递过来的参数*/
	private String bundle_param;

	//重写
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		myView = inflater.inflate(R.layout.fragment_home_home, container, false);

		//接收传参
		Bundle bundle = this.getArguments();
		bundle_param = bundle.getString("param");

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
		tv_homef.setText(tv_homef.getText() + "--" + bundle_param);
	}

	/**
	 * 初始化点击事件
	 * */
	private void initEvent(){
	}
	
}
