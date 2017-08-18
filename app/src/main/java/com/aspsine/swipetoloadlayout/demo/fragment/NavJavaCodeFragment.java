package com.aspsine.swipetoloadlayout.demo.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.aspsine.swipetoloadlayout.demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavJavaCodeFragment extends BaseNavigationFragment implements OnRefreshListener, OnLoadMoreListener {

    private SwipeToLoadLayout swipeToLoadLayout;

    private View header;

    private View footer;

    private int mRefreshNum;

    private int mLoadMoreNum;

    private ArrayAdapter<String> mAdapter;

    private LayoutInflater mInflater;

    private int mHeadStyleNum;
    private int mFootStyleNum;

    private int mHeaderNum;

    private int mFooterNum;

    public static BaseNavigationFragment newInstance() {
        BaseNavigationFragment fragment = new NavJavaCodeFragment();
        return fragment;
    }

    public NavJavaCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mInflater = inflater;
        return inflater.inflate(R.layout.fragment_nav_java_code, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("Via java code");
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        ListView listView = (ListView) view.findViewById(R.id.swipe_target);
        listView.setAdapter(mAdapter);

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showConfigDialog();
            }
        }, 500);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_config, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_config) {
            showConfigDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                mAdapter.add("Load More" + mLoadMoreNum);
                mLoadMoreNum++;
            }
        }, 3000);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
                mAdapter.insert("Refresh" + mRefreshNum, 0);
                mRefreshNum++;
            }
        }, 3000);
    }


    Dialog dialog;

    private void showConfigDialog() {
        if (dialog == null) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog_config, null);
            initDialogView(view);
            dialog = new AlertDialog.Builder(getActivity())
                    .setView(view)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            swipeToLoadLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeToLoadLayout.setRefreshing(true);
                                }
                            });
                        }
                    }).create();
        }
        dialog.show();
    }

    private void initDialogView(View view) {
        final RadioGroup rgHeadStyle = (RadioGroup) view.findViewById(R.id.rgHeadStyle);
        final RadioGroup rgFootStyle = (RadioGroup) view.findViewById(R.id.rgFootStyle);
        final RadioGroup rgHeader = (RadioGroup) view.findViewById(R.id.rgHeader);
        final RadioGroup rgFooter = (RadioGroup) view.findViewById(R.id.rgFooter);

        rgHeadStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgHeadStyle.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rgHeadStyle.getChildAt(i);
                    if (rb.isChecked()) {
                        changeHeadStyle(i);
                        break;
                    }
                }
            }
        });

        rgFootStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgFootStyle.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rgFootStyle.getChildAt(i);
                    if (rb.isChecked()) {
                        changeFootStyle(i);
                        break;
                    }
                }
            }
        });

        rgHeader.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgHeader.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rgHeader.getChildAt(i);
                    if (rb.isChecked()) {
                        changeHeader(i);
                        break;
                    }
                }
            }
        });

        rgFooter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgFooter.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rgFooter.getChildAt(i);
                    if (rb.isChecked()) {
                        changeFooter(i);
                        break;
                    }
                }
            }
        });

        ((RadioButton) rgHeadStyle.getChildAt(mHeadStyleNum)).setChecked(true);
        ((RadioButton) rgFootStyle.getChildAt(mFootStyleNum)).setChecked(true);
        ((RadioButton) rgHeader.getChildAt(mHeaderNum)).setChecked(true);
        ((RadioButton) rgFooter.getChildAt(mFooterNum)).setChecked(true);
    }

    private void changeFooter(int i) {
        mFooterNum = i;
        View view = null;
        switch (i) {
            case 0:
                view = mInflater.inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false);
                break;
            case 2:
                view = mInflater.inflate(R.layout.layout_google_footer, swipeToLoadLayout, false);
                break;
            case 1:
                view = mInflater.inflate(R.layout.layout_google_hook_footer, swipeToLoadLayout, false);
                break;
        }
        if (view != null) {
            swipeToLoadLayout.setLoadMoreFooterView(view);
        }
    }

    private void changeHeader(int i) {
        mHeaderNum = i;
        View view = null;
        switch (i) {
            case 0:
                view = mInflater.inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false);
                break;
            case 1:
                view = mInflater.inflate(R.layout.layout_google_hook_header, swipeToLoadLayout, false);
                break;
            case 2:
                view = mInflater.inflate(R.layout.layout_google_header, swipeToLoadLayout, false);
                break;
            case 3:
                view = mInflater.inflate(R.layout.layout_jd_header, swipeToLoadLayout, false);
                break;
        }
        if (view != null) {
            swipeToLoadLayout.setRefreshHeaderView(view);
        }
    }

    private void changeHeadStyle(int i) {
        mHeadStyleNum = i;
        switch (i) {
            case 0:
                swipeToLoadLayout.setHeadSwipeStyle(SwipeToLoadLayout.STYLE.CLASSIC);
                break;
            case 1:
                swipeToLoadLayout.setHeadSwipeStyle(SwipeToLoadLayout.STYLE.ABOVE);
                break;
            case 2:
                swipeToLoadLayout.setHeadSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
                break;
            case 3:
                swipeToLoadLayout.setHeadSwipeStyle(SwipeToLoadLayout.STYLE.SCALE);
                break;
        }
    }

    private void changeFootStyle(int i) {
        mFootStyleNum = i;
        switch (i) {
            case 0:
                swipeToLoadLayout.setFootSwipeStyle(SwipeToLoadLayout.STYLE.CLASSIC);
                break;
            case 1:
                swipeToLoadLayout.setFootSwipeStyle(SwipeToLoadLayout.STYLE.ABOVE);
                break;
            case 2:
                swipeToLoadLayout.setFootSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
                break;
            case 3:
                swipeToLoadLayout.setFootSwipeStyle(SwipeToLoadLayout.STYLE.SCALE);
                break;
        }
    }


}
