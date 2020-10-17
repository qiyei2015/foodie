package com.qiyei.foodie.service;

import com.github.pagehelper.PageInfo;
import com.qiyei.common.PagedGrid;

import java.util.List;

public class BaseService {

    protected PagedGrid setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGrid grid = new PagedGrid();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

}
