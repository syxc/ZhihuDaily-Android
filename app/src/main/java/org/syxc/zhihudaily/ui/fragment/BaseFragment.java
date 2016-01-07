package org.syxc.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import org.syxc.zhihudaily.ui.activity.BaseActivity;

/**
 * Created by syxc on 16/1/7.
 */
public class BaseFragment extends Fragment {
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((BaseActivity) getActivity()).inject(this);
  }
}
