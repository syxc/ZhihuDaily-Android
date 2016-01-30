package org.syxc.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.fastjson.JSON;
import javax.inject.Inject;
import org.syxc.zhihudaily.R;
import org.syxc.zhihudaily.api.ApiClient;
import org.syxc.zhihudaily.api.Callback;
import org.syxc.zhihudaily.model.LatestNews;
import org.syxc.zhihudaily.ui.ActivityTitleController;
import timber.log.Timber;

public class LatestNewsFragment extends BaseFragment {
  @Inject ActivityTitleController titleController;

  public LatestNewsFragment() {
    // Required empty public constructor
  }

  public static LatestNewsFragment newInstance() {
    LatestNewsFragment fragment = new LatestNewsFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Timber.tag("LifeCycles");
    Timber.d("Fragment Created");
    loadData();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_latest_news, container, false);
  }

  @Override public void onResume() {
    super.onResume();

    titleController.setTitle("最新消息");
  }

  @Override void loadData() {
    try {
      ApiClient.instance().fetchLatestNews(new Callback<LatestNews>() {
        @Override public void onSuccess(LatestNews latestNews) {
          Timber.i("latestNews: %s", JSON.toJSONString(latestNews));
        }

        @Override public void onCompleted() {

        }

        @Override public void onError(String error) {

        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      ApiClient.instance().fetchTestPost("Jurassic Park", new Callback<String>() {
        @Override public void onSuccess(String s) {
          showLongTost("Search result: " + s);
        }

        @Override public void onCompleted() {
          showShortTost("Search completed");
        }

        @Override public void onError(String error) {
          showShortTost("ERROR: " + error);
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
