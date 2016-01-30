package org.syxc.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import org.syxc.zhihudaily.ui.activity.BaseActivity;

/**
 * Created by syxc on 16/1/7.
 */
public abstract class BaseFragment extends Fragment {
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((BaseActivity) getActivity()).inject(this);
  }

  /** -------------------- abstract methods -------------------- */

  abstract void loadData();

  void showShortTost(CharSequence text) {
    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
  }

  void showShortTost(int resId) {
    Toast.makeText(getActivity(), getText(resId), Toast.LENGTH_SHORT).show();
  }

  void showLongTost(CharSequence text) {
    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
  }

  void showLongTost(int resId) {
    Toast.makeText(getActivity(), getText(resId), Toast.LENGTH_LONG).show();
  }
}
