package com.why.project.fragmenttabhostbottomdemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.why.project.fragmenttabhostbottomdemo.fragment.ContactFragment;
import com.why.project.fragmenttabhostbottomdemo.fragment.HomeFragment;
import com.why.project.fragmenttabhostbottomdemo.fragment.MessageFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private FragmentTabHost mBottomFTabHostLayout;

	//选项卡子类集合
	private ArrayList<TabItem> tabItemList = new ArrayList<TabItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initTabList();
		initFTabHostLayout();
		setFTabHostData();
		initEvents();

	}

	/**
	 * 初始化选项卡数据集合*/
	private void initTabList() {

		tabItemList.add(new TabItem(this,getResources().getString(R.string.home_function_home),
				R.drawable.home_tab_home_selector,HomeFragment.class));
		tabItemList.add(new TabItem(this,getResources().getString(R.string.home_function_message),
				R.drawable.home_tab_message_selector,MessageFragment.class));
		tabItemList.add(new TabItem(this,getResources().getString(R.string.home_function_contact),
				R.drawable.home_tab_contact_selector,ContactFragment.class));
	}

	/**
	 * 初始化FragmentTabHost*/
	private void initFTabHostLayout() {
		//实例化
		mBottomFTabHostLayout = (FragmentTabHost) findViewById(R.id.tab_bottom_ftabhost_layout);
		mBottomFTabHostLayout.setup(this, getSupportFragmentManager(), R.id.center_layout);//最后一个参数是碎片切换区域的ID值
		// 去掉分割线
		mBottomFTabHostLayout.getTabWidget().setDividerDrawable(null);

		//设置选项卡区域的自定义宽度和高度
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				getResources().getDimensionPixelSize(R.dimen.tab_bottom_background_height));
		mBottomFTabHostLayout.getTabWidget().setLayoutParams(params);
	}

	/**设置选项卡的内容*/
	private void setFTabHostData() {

		//Tab存在于TabWidget内，而TabWidget是存在于TabHost内。与此同时，在TabHost内无需在写一个TabWidget，系统已经内置了一个TabWidget
		for (int i = 0; i < tabItemList.size(); i++) {
			//实例化一个TabSpec,设置tab的名称和视图
			TabHost.TabSpec spec = mBottomFTabHostLayout.newTabSpec(tabItemList.get(i).getTabTitle()).setIndicator(tabItemList.get(i).getTabView());
			// 添加Fragment
			//初始化传参：http://bbs.csdn.net/topics/391059505
			Bundle bundle = new Bundle();
			bundle.putString("param","初始化传参");

			mBottomFTabHostLayout.addTab(spec, tabItemList.get(i).getTabFragment(), bundle);

			// 设置Tab按钮的背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
			mBottomFTabHostLayout.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_bg_selector);
		}

		//默认选中第一项
		mBottomFTabHostLayout.setCurrentTab(0);
		tabItemList.get(0).setChecked(true);
	}

	private void initEvents() {
		//选项卡的切换事件监听
		mBottomFTabHostLayout.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				//重置Tab样式
				for (int i = 0; i< tabItemList.size(); i++) {
					TabItem tabitem = tabItemList.get(i);
					if (tabId.equals(tabitem.getTabTitle())) {
						tabitem.setChecked(true);
					}else {
						tabitem.setChecked(false);
					}
				}

				Toast.makeText(MainActivity.this,tabId,Toast.LENGTH_SHORT).show();

				//切换时执行某个Fragment的公共方法，前提是先打开过一次
				//对于更改参数的情况，还需要实现Fragment保存原有状态，否则Fragment接收到的始终是初始的bundle的值，因为Fragment之间切换时每次都会调用onCreateView方法。
				int currentTabPosition = mBottomFTabHostLayout.getCurrentTab();
				Fragment fragment = getSupportFragmentManager().findFragmentByTag(tabItemList.get(currentTabPosition).getTabTitle());
				if(fragment instanceof ContactFragment){
					Log.e("tag","fragment.isDetached()="+fragment.isDetached());
					if (fragment != null) {
						((ContactFragment)fragment).setBundle_param("切换时更改bundle_param的值");
					}
				}
			}
		});

		//如果想要不切换到相应的fragment，而是打开一个新的界面
		//http://www.jianshu.com/p/3b0ff7a4bde1
		mBottomFTabHostLayout.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this,"打开一个新的界面",Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 选项卡子项类*/
	class TabItem{

		private Context mContext;

		private CheckedTextView bottomtab_checkedTextView;

		//底部选项卡对应的图标
		private int tabImgRedId;
		//底部选项卡对应的文字
		private String tabTitle;
		//底部选项卡对应的Fragment类
		private Class<? extends Fragment> tabFragment;

		public TabItem(Context mContext, String tabTitle, int tabImgRedId, Class tabFragment){
			this.mContext = mContext;

			this.tabTitle = tabTitle;
			this.tabImgRedId = tabImgRedId;
			this.tabFragment = tabFragment;
		}

		public Class<? extends Fragment> getTabFragment() {
			return tabFragment;
		}

		public int getTabImgRedId() {
			return tabImgRedId;
		}

		public String getTabTitle() {
			return tabTitle;
		}

		/**
		 * 获取底部选项卡的布局实例并初始化设置*/
		private View getTabView() {
			//取得布局实例
			View bottomtabitemView = View.inflate(mContext, R.layout.tab_bottom_item, null);

			//===========设置CheckedTextView控件的图片和文字==========
			bottomtab_checkedTextView = (CheckedTextView) bottomtabitemView.findViewById(R.id.bottomtab_checkedTextView);

			//设置CheckedTextView控件的android:drawableTop属性值
			Drawable drawable = ContextCompat.getDrawable(mContext,tabImgRedId);
			//setCompoundDrawables 画的drawable的宽高是按drawable.setBound()设置的宽高
			//而setCompoundDrawablesWithIntrinsicBounds是画的drawable的宽高是按drawable固定的宽高，即通过getIntrinsicWidth()与getIntrinsicHeight()自动获得
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			bottomtab_checkedTextView.setCompoundDrawables(null, drawable, null, null);
			//bottomtab_checkedTextView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);

			//设置CheckedTextView的文字
			bottomtab_checkedTextView.setText(tabTitle.toString());

			return bottomtabitemView;
		}

		/**
		 * 更新文字颜色
		 */
		public void setChecked(boolean isChecked) {
			if(tabTitle != null){
				if(isChecked){
					bottomtab_checkedTextView.setTextColor(mContext.getResources().getColor(R.color.tab_text_selected));
				}else{
					bottomtab_checkedTextView.setTextColor(mContext.getResources().getColor(R.color.tab_text_normal));
				}
			}
		}
	}


}
