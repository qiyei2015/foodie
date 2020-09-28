package com.qiyei.foodie.pojo.vo;

import com.qiyei.foodie.pojo.Items;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/9/26.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ItemsVO {

    private int page;			// 当前页数
    private int totalPage;			// 总页数
    private long total;		// 总记录数
    private List<Items> items;		// 每行显示的内容

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
