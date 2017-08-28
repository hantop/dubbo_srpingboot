package com.lizikj.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Michael.Huang on 2017/4/1.
 * 内存分页工具
 */
public class Pager<T> {

    /**
     * 每页显示条数
     */
    private int pageSize;
    /**
     * 原集合
     */
    private List<T> data;

    private int totalPage;

    private int total;

    private Pager(List<T> data, int pageSize) {
        this.data = data;
        this.pageSize = pageSize;
        if (data == null || data.isEmpty()) {
            this.total = 0;
            this.totalPage = 0;
            return;
        }
        this.total = data.size();
        this.totalPage = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));

    }

    /**
     * 创建分页器
     *
     * @param data     需要分页的数据
     * @param pageSize 每页显示条数
     * @param <T>      业务对象
     * @return 分页器
     */
    public static <T> Pager<T> create(List<T> data, int pageSize) {
        return new Pager<>(data, pageSize);
    }

    /**
     * 得到分页后的数据
     *
     * @param pageNum 页码
     * @return 分页后结果
     */
    public List<T> getPagedList(int pageNum) {
        if (total == 0) {
            return new ArrayList<>();
        }
        int fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList();
        }

        int toIndex = pageNum * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotal() {
        return total;
    }

    public List<T> getData() {
        return data;
    }

    /*public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        List<Integer> list = Arrays.asList(array);

        Pager<Integer> pager = Pager.create(list, 10);

        List<Integer> page1 = pager.getPagedList(1);
        System.out.println(page1);

        List<Integer> page2 = pager.getPagedList(2);
        System.out.println(page2);

        List<Integer> page3 = pager.getPagedList(3);
        System.out.println(page3);
    }*/
}