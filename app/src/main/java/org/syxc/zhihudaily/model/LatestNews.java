package org.syxc.zhihudaily.model;

import java.util.List;

/**
 * 最新消息
 * Created by syxc on 16/1/7.
 */
public final class LatestNews {

  public String date;
  public List<Stories> stories;
  public List<TopStories> top_stories;

  public LatestNews() {
  }
}
