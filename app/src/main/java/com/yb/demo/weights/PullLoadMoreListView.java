package com.yb.demo.weights;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yb.demo.R;

/**
 *<p>下拉加载更多,仿QQ聊天界面</p>
 * 改自下拉刷新上拉加载更多ListView<br>
 * <b>正常ListView的方法</b><br>
 * 调用setAdapter(BaseAdapter adapter)<br>
 * <strong>开启下拉加载更多的方法</strong><br>
 * 调用setAdapter(BaseAdapter adapter, Context context)<br>
 */
public class PullLoadMoreListView extends ListView  implements OnScrollListener{
	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	// 正在加载
	private final static int REFRESHING = 2;
	// 加载完成
	private final static int DONE = 3;
	private final static int LOADING = 4;
	//处理反复触发
	private Boolean mRepeate = true;
	/*
	 * 比率, 比例 这个值是个很有用的值，要设定一个合适的值 现在设置为3,是为了： 1.当手指在屏幕上拉动的距离是headView的3倍的时候才会刷新
	 * 2.每当手指滑动三个单位的距离，headView刷新一次 如果是1.那么整个listview会被拉到屏幕的最底部，效果不好
	 */
	private final static int RATIO = 3;

	private LayoutInflater mInflater;
	private LinearLayout mHeadViewLinearLayout;// headerView
	private TextView tipsTextview;// 提示文字
	private ImageView mArrowImageView;// 箭头
	private ProgressBar mProgressBar;// 进度条
	private String mTipsString = "正在加载...";
	private RotateAnimation mAnimation;// 逆时针
	private RotateAnimation mReverseAnimation;// 顺时针
	private boolean mIsRecored;
	private int mHeadContentHeight;// head高，这个属性比较重要
	private int mStartY;
	private int mFirstItemIndex;
	private int mState;
	private boolean mIsBack;
	private boolean mIsRefreshable;// 是否可下拉刷新，只有设置了下拉刷新listener之后才可以下来
	private boolean mIsRefreshableNohead = true;//如果删除headview就将此标志更改为false，取消onTouch事件，否则轻轻滑动会使列表回滚到第一行
	private BaseAdapter mAdapter;
	private Context mContext;
	private static onEventUpListener mUplistener;

	public PullLoadMoreListView(Context context) {
		super(context);
		init(context);
	}

	public PullLoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		setCacheColorHint(context.getResources().getColor(R.color.color_transparent));
		mInflater = LayoutInflater.from(context);
		mHeadViewLinearLayout = (LinearLayout) mInflater.inflate(R.layout.view_pull_load_more_list_view_head, null);
		mArrowImageView = (ImageView) mHeadViewLinearLayout.findViewById(R.id.head_arrowImageView);
		mArrowImageView.setMinimumWidth(70);
		mArrowImageView.setMinimumHeight(50);
		mProgressBar = (ProgressBar) mHeadViewLinearLayout.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) mHeadViewLinearLayout.findViewById(R.id.head_tipsTextView);
		measureView(mHeadViewLinearLayout);// 确定headView的宽高,如果不调用这个方法，之后得到的宽高值都为0
		mHeadContentHeight = mHeadViewLinearLayout.getMeasuredHeight();
		mHeadViewLinearLayout.setPadding(0, -1 * mHeadContentHeight, 0, 0);// 设置paddingTop为负数(-head_height)，这个是把head放到看不见的地方的代码
		mHeadViewLinearLayout.invalidate();
		addHeaderView(mHeadViewLinearLayout);// 和上边方法区别的，这个添加分割线
		// 逆时针动画
		mAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mAnimation.setInterpolator(new LinearInterpolator());
		mAnimation.setDuration(200);
		mAnimation.setFillAfter(true);// 设置在动画结束之后图片就呈现的是动画结束之后的样子
		mReverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseAnimation.setInterpolator(new LinearInterpolator());
		mReverseAnimation.setDuration(200);
		mReverseAnimation.setFillAfter(true);
		mState = LOADING;// 初始化state
		mIsRefreshable = false;// 默认不可下拉加载更多
	}

	public GetMoreDataListener mGetMoreDataListener;

	public void setmGetMoreDataListener(GetMoreDataListener mGetMoreDataListener) {
		this.mGetMoreDataListener = mGetMoreDataListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		mFirstItemIndex = firstVisibleItem;
	}

	/**
	 *
	 * 加载更多回调接口
	 */
	public interface GetMoreDataListener {
		void getMore();
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (mIsRefreshable && mIsRefreshableNohead) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 按下
					if (mFirstItemIndex == 0 && !mIsRecored) {
						mIsRecored = true;
						mStartY = (int) event.getY();
					}
					break;
				case MotionEvent.ACTION_UP:
					if (mState != REFRESHING && mState != LOADING) {
						if (mState == DONE) {
						}
						if (mState == PULL_To_REFRESH) {// 拉的距离不够
							mState = DONE;
							changeHeaderViewByState();
						}
						if (mState == RELEASE_To_REFRESH) {
							mState = REFRESHING;
							changeHeaderViewByState();
							onRefresh();
						}
					}
					mIsRecored = false;
					mIsBack = false;
					if(mUplistener != null){
						mUplistener.onUp();
					}
					break;
				case MotionEvent.ACTION_MOVE:
					int tempY = (int) event.getY();
					if (!mIsRecored && mFirstItemIndex == 0) {
						mIsRecored = true;
						mStartY = tempY;
					}
					if (mState != REFRESHING && mIsRecored && mState != LOADING) {

						if (mState == RELEASE_To_REFRESH) {
							if (((tempY - mStartY) / RATIO < mHeadContentHeight) && (tempY - mStartY) > 0) {
								mState = PULL_To_REFRESH;
								changeHeaderViewByState();
							} else if (tempY - mStartY <= 0) {
								mState = DONE;
								changeHeaderViewByState();
							}
						}
						if (mState == PULL_To_REFRESH) {
							if ((tempY - mStartY) / RATIO >= mHeadContentHeight) {// 滑动的距离到达临近点
								mState = RELEASE_To_REFRESH;
								mIsBack = true;
								changeHeaderViewByState();
							} else if (tempY - mStartY <= 0) {
								mState = DONE;
								changeHeaderViewByState();

							}
						}
						if (mState == DONE) {
							if (tempY - mStartY > 0) {
								mState = PULL_To_REFRESH;
								changeHeaderViewByState();

							}
						}
						// 更新headView的size
						if (mState == PULL_To_REFRESH) {
							mHeadViewLinearLayout.setPadding(0, -1 * mHeadContentHeight + (tempY - mStartY) / RATIO, 0, 0);
						}
						// 更新headView的paddingTop
						if (mState == RELEASE_To_REFRESH) {
							mHeadViewLinearLayout.setPadding(0, (tempY - mStartY) / RATIO - mHeadContentHeight, 0, 0);
						}
					}
					break;
			}
		}
		try {
			return super.onTouchEvent(event);
		} catch (IllegalStateException e) {
			return true;
		} catch (IndexOutOfBoundsException e) {
			return true;
		}
	}

	/**
	 *<p>当状态改变时候，调用该方法，以更新界面</p><br/>
	 */
	private void changeHeaderViewByState() {
		switch (mState) {
			case RELEASE_To_REFRESH:
				releaeeToRefresh();
				break;
			case PULL_To_REFRESH:
				pullToRefresh();
				break;
			case REFRESHING:
				refreshing();
				break;
			case DONE:
				done();
				break;
		}
	}

	public static void setOnEventUpListener(onEventUpListener uplistener){
		mUplistener = uplistener;
	}

	/**
	 *<p>加载完成</p><br/>
	 */
	public void onRefreshComplete() {
		mState = DONE;
		mRepeate = true;
		changeHeaderViewByState();
	}

	/**
	 *<p>正在刷新方法</p><br/>
	 */
	private void onRefresh() {
		if (mGetMoreDataListener != null) {
			setFastScrollEnabled(false);
			mGetMoreDataListener.getMore();
		}
	}

	/**
	 *<p>只有调用了这个方法以后，header的宽高才能被计算出来</p><br/>
	 */
	private void measureView(View header) {
		ViewGroup.LayoutParams p = header.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int childHeightSpec;
		int lpHeight = p.height;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		header.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 *<p>列表初始化</p><br/>
	 */
	public void setAdapter(BaseAdapter adapter, Context context) {
		mAdapter = adapter;
		mContext = context;
		refreshing();
		onRefreshComplete();
		super.setAdapter(adapter);
	}


	/**
	 *<p>松开即可加载更多动作</p><br/>
	 */
	public void releaeeToRefresh() {
		mArrowImageView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		tipsTextview.setVisibility(View.VISIBLE);
		mArrowImageView.clearAnimation();
		mArrowImageView.startAnimation(mAnimation);
		tipsTextview.setText(mContext.getString(R.string.string_pull_load_more_list_view_go_load_more));
	}

	/**
	 *<p>下拉加载更多动作</p><br/>
	 */
	public void pullToRefresh() {
		mProgressBar.setVisibility(View.GONE);
		tipsTextview.setVisibility(View.VISIBLE);
		mArrowImageView.clearAnimation();
		mArrowImageView.setVisibility(View.VISIBLE);
		if (mIsBack) {
			mIsBack = false;
			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(mReverseAnimation);
		}
		tipsTextview.setText(mContext.getString(R.string.string_pull_load_more_list_view_pull));
	}

	/**
	 *<p>加载中</p><br/>
	 * @since 4.2
	 * @author sunxh
	 */
	public void refreshing() {
		mIsRefreshable = false;
		mHeadViewLinearLayout.setPadding(0, 0, 0, 0);
		mProgressBar.setVisibility(View.VISIBLE);
		mArrowImageView.clearAnimation();
		mArrowImageView.setVisibility(View.GONE);
		tipsTextview.setText(mTipsString);
	}

	/**
	 *<p>加载结束</p><br/>
	 */
	public void done() {
		mIsRefreshable = true;
		mHeadViewLinearLayout.setPadding(0, -1 * mHeadContentHeight, 0, 0);
		mProgressBar.setVisibility(View.GONE);
		mArrowImageView.clearAnimation();
		mArrowImageView.setImageResource(R.drawable.result_list_view_arrow);
		tipsTextview.setText(mContext.getString(R.string.string_result_list_view_already_finish));
	}

	/**
	 *<p>修改头文字内容</p><br/>
	 * @since 4.2
	 * @author sunxh
	 * @param string
	 */
	public void setTipsString(String string) {
		mTipsString = string;
	}


	/**
	 *
	 *<p>处理事件传递</p><br/>
	 */
	public interface onEventUpListener {
		/**
		 *<p>手指抬起</p><br/>
		 */
		void onUp();
	}
}
