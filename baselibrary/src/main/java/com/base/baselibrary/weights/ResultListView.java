package com.base.baselibrary.weights;

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

import com.base.baselibrary.R;
import com.base.baselibrary.utils.PreferencesUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * ListView<br>
 * 实现下拉刷新上拉更多<br>
 * <b>正常ListView的方法</b><br>
 * 调用setAdapter(BaseAdapter adapter)<br>
 * <strong>开启下拉刷新,上拉加载的方法</strong><br>
 * 调用setAdapter(BaseAdapter adapter, Context context,Class clas)<br>
 */
public class ResultListView extends ListView implements OnScrollListener {
    private final static int RELEASE_To_REFRESH = 0;
    private final static int PULL_To_REFRESH = 1;
    // 正在刷新
    private final static int REFRESHING = 2;
    // 刷新完成
    private final static int DONE = 3;
    private final static int LOADING = 4;
    //处理反复触发
    private Boolean mRepeate = true;
    /*
     * 比率, 比例 这个值是个很有用的值，要设定一个合适的值 现在设置为3,是为了： 1.当手指在屏幕上拉动的距离是headView的3倍的时候才会刷新
     * 2.每当手指滑动三个单位的距离，headView刷新一次 如果是1.那么整个listview会被拉到屏幕的最底部，效果不好
     */
    private final static int RATIO = 3;
    /***
     * 显示底部加载
     */
    public static final int FOOTER_SHOW = 0;
    /***
     * 显示底部无更多文案
     */
    public static final int FOOTER_NOT_SHOW = 1;
    /***
     * 不显示底部加载栏
     */
    public static final int FOOTER_NOT_DATA = 2;
    /***
     * 添加按钮
     */
    public static final int FOOTER_BUTTON = 3;
    private LayoutInflater mInflater;
    private LinearLayout mHeadViewLinearLayout;// headerView
    private TextView tipsTextview;// 提示文字
    private ImageView mArrowImageView;// 箭头
    private ProgressBar mProgressBar;// 进度条
    private String mTipsString = "正在刷新...";
    private RotateAnimation mAnimation;// 逆时针
    private RotateAnimation mReverseAnimation;// 顺时针
    private boolean mIsRecored;
    //	private int mHeadContentWidth;// head宽
    private int mHeadContentHeight;// head高，这个属性比较重要
    private int mStartY;
    private int mFirstItemIndex;
    private int mState;
    private boolean mIsBack;
    private OnRefreshListener mRefreshListener;
    private boolean mIsRefreshable;// 是否可下拉刷新，只有设置了下拉刷新listener之后才可以下来
    private boolean mIsRefreshableNohead = true;//如果删除headview就将此标志更改为false，取消onTouch事件，否则轻轻滑动会使列表回滚到第一行
    private int mScrollState;
    private BaseAdapter mAdapter;
    private Context mContext;
    private int pageLastIndex;
    private boolean isUp;
    private View mView;
    private View mLayout;
    private TextView mText;
    private TextView mClickMore;
    private OnScrollListener onScrollListener;
    private static onEventUpListener mUplistener;

    public ResultListView(Context context) {
        super(context);
        init(context);
    }

    public ResultListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        super.setOnScrollListener(this);
        this.onScrollListener = l;
    }

    private void init(Context context) {
        this.mContext = context;
        setCacheColorHint(context.getResources().getColor(R.color.color_transparent));
        mInflater = LayoutInflater.from(context);
        mHeadViewLinearLayout = (LinearLayout) mInflater.inflate(R.layout.view_result_list_view_head, null);
        mArrowImageView = (ImageView) mHeadViewLinearLayout.findViewById(R.id.head_arrowImageView);
        mArrowImageView.setMinimumWidth(70);
        mArrowImageView.setMinimumHeight(50);
        mProgressBar = (ProgressBar) mHeadViewLinearLayout.findViewById(R.id.head_progressBar);
        tipsTextview = (TextView) mHeadViewLinearLayout.findViewById(R.id.head_tipsTextView);
        timeTextview = (TextView) mHeadViewLinearLayout.findViewById(R.id.head_time);
        measureView(mHeadViewLinearLayout);// 测量headView的宽高,如果不调用这个方法，之后得到的宽高值都为0
        mHeadContentHeight = mHeadViewLinearLayout.getMeasuredHeight();
        mHeadViewLinearLayout.setPadding(0, -1 * mHeadContentHeight, 0, 0);// 设置paddingTop为负数(-head_height)，这个是把head放到看不见的地方的代码
        mHeadViewLinearLayout.invalidate();
        addHeaderView(mHeadViewLinearLayout);// 和上边方法区别的，这个添加分割线
        super.setOnScrollListener(this);// 设置滚动监听
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
        mIsRefreshable = false;// 默认不可下拉刷新
    }

    private TextView timeTextview;
    private Class mClazz;


    public void daoClear() {
        pageLastIndex = 0;
        isUp = true;
    }

    /**
     * 记录滑动状态
     */
    @Override
    public void onScroll(AbsListView view, int firstVisiableItem, int visibleItemCount, int totalItemCount) {
        if (onScrollListener != null) {
            onScrollListener.onScroll(view, firstVisiableItem, visibleItemCount, totalItemCount);
        }
        mFirstItemIndex = firstVisiableItem;
        if (mAdapter != null) {
            //判断滑动动作
            if (pageLastIndex < firstVisiableItem + visibleItemCount) {
                pageLastIndex = firstVisiableItem + visibleItemCount;//页面上显示的最后一条数据的索引
                isUp = true;
            } else {
                isUp = false;
            }
            //当剩余未显示的条数小于6时，激发加载更多回调
            if ((totalItemCount - (firstVisiableItem + visibleItemCount)) < 6) {
                if (isUp) {
                    if (mRefreshListener != null) {
                        mRefreshListener.onLoadMore();
                    }
                }
            }
        }
    }

    /**
     * <p>底部加载栏控制</p>
     */
    public void setFooterView(int option) {
        switch (option) {
            //显示底部加载
            case FOOTER_SHOW:
                mLayout.setVisibility(View.VISIBLE);
                mText.setVisibility(View.GONE);
                hideBtn();
                break;
            //显示底部无更多文案
            case FOOTER_NOT_SHOW:
                mLayout.setVisibility(View.GONE);
                mText.setVisibility(View.VISIBLE);
                hideBtn();
                break;
            //不显示底部加载栏
            case FOOTER_NOT_DATA:
                mLayout.setVisibility(View.GONE);
                mText.setVisibility(View.GONE);
                hideBtn();
                break;
            default:
                break;
        }
    }

    private void hideBtn() {
        if (mClickMore != null) {
            mClickMore.setVisibility(View.GONE);
        }
    }

    /**
     * <p>底部加载栏控制</p><br/>
     * <p>footer添加按钮</p>
     */
    public void setFooterView(int option, OnClickListener click, String text) {
        switch (option) {
            case FOOTER_BUTTON:
                mLayout.setVisibility(View.GONE);
                mText.setVisibility(View.GONE);
                mClickMore.setOnClickListener(click);
                mClickMore.setVisibility(View.VISIBLE);
                mClickMore.setText(text);
                break;
            default:
                break;
        }
    }

    /**
     * 滑动动作
     */
    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(arg0, arg1);
        }

        int first = arg0.getFirstVisiblePosition();//判断当前listview第一条item显示第几条数据
        this.mScrollState = arg1;
        if (arg1 == 2 && first == 0) {
            //解决部分手机下拉刷新是listview状态错误
            this.mScrollState = SCROLL_STATE_IDLE;
        }
    }

    public int getScrollState() {
        return mScrollState;
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
                    if (mUplistener != null) {
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
//						setSelection(0);
                            if (((tempY - mStartY) / RATIO < mHeadContentHeight) && (tempY - mStartY) > 0) {
                                mState = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            } else if (tempY - mStartY <= 0) {
                                mState = DONE;
                                changeHeaderViewByState();
                            }
                        }
                        if (mState == PULL_To_REFRESH) {
//						setSelection(0);
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
     * <p>当状态改变时候，调用该方法，以更新界面</p><br/>
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

    /**
     * <p>添加刷新数据监听</p><br/>
     */
    public void setonRefreshListener(OnRefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
    }

    /**
     * <p>添加手指离开屏幕监听</p><br/>
     */
    public static void setOnEventUpListener(onEventUpListener uplistener) {
        mUplistener = uplistener;
    }

    /**
     * <p>加载完成</p><br/>
     */
    public void onRefreshComplete() {
        mState = DONE;
        mRepeate = true;
        changeHeaderViewByState();
        putTime();
        daoClear();
    }

    /**
     * 初始化ListView上次更新时间
     * 必须初始化，否则上次更新时间会异常
     *
     * @param clazz 根据类名存储在sharepreference中
     */
    public void initHeaderTime(Class clazz) {
        mClazz = clazz;
        putTime();
    }

    private void putTime() {
        if (mClazz != null) {
            DateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");//24小时制
            String date = sdf.format(new Date(System.currentTimeMillis()));
            PreferencesUtils.savePrefString(mContext, mClazz.getName() + "list_view_header_time", date);
            timeTextview.setText(mContext.getString(R.string.string_result_list_view_last_refresh_time, date));
        }
    }

    /**
     * <p>进行刷新</p><br/>
     */
    private void onRefresh() {
        if (mRefreshListener != null) {
            setFastScrollEnabled(false);
            mRefreshListener.onRefresh();
        }
    }

    /**
     * <p>只有调用了这个方法以后，header的宽高才能被计算出来</p>
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
     * <p>列表初始化</p><br/>
     */
    public void setAdapter(BaseAdapter adapter, Context context, Class clas) {
        mAdapter = adapter;
        mContext = context;
        mView = View.inflate(mContext, R.layout.view_result_list_view_footer, null);
        mLayout = mView.findViewById(R.id.itemloading);
        mClickMore = (TextView) mView.findViewById(R.id.id_result_list_view_footer_click_more);
        mText = (TextView) mView.findViewById(R.id.footer);
        addFooterView(mView);
        refreshing();
        onRefreshComplete();
        initHeaderTime(clas);
        super.setAdapter(adapter);
    }

    /**
     * 移除底部view
     */
    public void removeFooter() {
        removeFooterView(mView);
    }

    /**
     * 添加底部view
     */
    public void addFooter() {
        addFooterView(mView);
    }

    /**
     * <p>松开即可刷新动作</p><br/>
     */
    public void releaeeToRefresh() {
        mArrowImageView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        tipsTextview.setVisibility(View.VISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.startAnimation(mAnimation);
        tipsTextview.setText(mContext.getString(R.string.string_result_list_view_go_refresh));
    }

    /**
     * <p>下拉刷新动作</p>
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
            tipsTextview.setText(mContext.getString(R.string.string_result_list_view_go_refresh));
        } else {
            tipsTextview.setText(mContext.getString(R.string.string_result_list_view_go_refresh));
        }
    }

    /**
     * <p>加载中</p>
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
     * <p>手动设置加载中状态，并把ListView移动到首部</p>
     */
    public void refreshingTop() {
        setSelection(0);
        refreshing();
    }

    /**
     * <p>加载结束</p>
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
     * <p>移除上拉刷新</p><br/>
     */
    public void removeHead() {
        mIsRefreshableNohead = false;
        removeHeaderView(mHeadViewLinearLayout);
    }

    /**
     * <p>修改头文字内容</p><br/>
     */
    public void setTipsString(String string) {
        mTipsString = string;
    }

    /**
     * <p>处理事件传递</p>
     */
    public interface onEventUpListener {
        /**
         * <p>手指抬起</p><br/>
         */
        void onUp();
    }

    /**
     * <p>下拉、上拉回调接口</p><br/>
     */
    public interface OnRefreshListener {
        public void onRefresh();

        public void onLoadMore();
    }
}
