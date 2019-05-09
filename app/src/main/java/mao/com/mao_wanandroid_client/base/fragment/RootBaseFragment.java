package mao.com.mao_wanandroid_client.base.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mao.com.mao_wanandroid_client.R;
import mao.com.mao_wanandroid_client.base.presenter.AbstractBasePresenter;
import mao.com.mao_wanandroid_client.widget.LoadingView;

/**
 * @author maoqitian
 * @Description 每個Fragment 的基类
 * @Time 2018/12/14 0014 22:48
 */
public abstract class RootBaseFragment <T extends AbstractBasePresenter>extends BaseFragment <T> implements View.OnClickListener {

    //默认为NORMAL状态
    protected int currentState = STATE_NORMAL;

    //TODO 加载中的LoadingView 还未完成
    private LoadingView mLoadingView;

    private ViewGroup mBaseView;
    private ViewGroup normalView;
    private View loadingView;
    private View errorView;
    //重复加载
    private TextView tvReload;
    
    @Override
    protected void initEventAndData() {
        if(getView() == null){
            //如果root view 不存在
            return;
        }
        normalView = getView().findViewById(R.id.view_base_normal);
        if (normalView == null) {
            throw new IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'normalView'.");
        }
        if (!(normalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException(
                    "normalView's ParentView should be a ViewGroup.");
        }
        mBaseView = (ViewGroup) normalView.getParent();
        View.inflate(_mActivity,R.layout.view_loading,mBaseView);
        View.inflate(_mActivity,R.layout.view_error,mBaseView);
        loadingView = mBaseView.findViewById(R.id.loading_view_container);
        errorView = mBaseView.findViewById(R.id.view_error);
        tvReload = errorView.findViewById(R.id.tv_reload);
        tvReload.setOnClickListener(this);
        //TODO 动画View 还未完成加载
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        normalView.setVisibility(View.VISIBLE);
        mLoadingView = loadingView.findViewById(R.id.view_loading);

    }


    @Override
    public void showNormal() {
        if(currentState == STATE_NORMAL) return;
        hideCurrentView();
        currentState = STATE_NORMAL;
        normalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        if(currentState == STATE_LOADING) return;
        hideCurrentView();
        currentState = STATE_LOADING;
        loadingView.setVisibility(View.VISIBLE);
        //TODO 开始动画View 还未完成加载
    }

    @Override
    public void showError() {
        if(currentState == STATE_ERROR) return;
        hideCurrentView();
        currentState = STATE_ERROR;
        errorView.setVisibility(View.VISIBLE);
        //TODO 开始动画View 还未完成加载
    }

    /**
     * 隐藏当前View
     */
    private void hideCurrentView() {
        switch (currentState){
            case STATE_NORMAL:
                if(loadingView != null){
                    normalView.setVisibility(View.GONE);
                }
                break;
            case STATE_LOADING:
                //TODO 停止动画View 还未完成加载
                if(loadingView != null){
                    loadingView.setVisibility(View.GONE);
                }
                break;
            case STATE_ERROR:
                if(errorView != null){
                    errorView.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_reload:
                reload();
                break;
            default:
                break;
        }
    }
}
