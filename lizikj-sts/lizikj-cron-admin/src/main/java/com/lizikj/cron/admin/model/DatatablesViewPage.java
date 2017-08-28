package com.lizikj.cron.admin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class DatatablesViewPage<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int sEcho = 3;
    private List<List<String>> aaData; // aaData 与datatales 加载的dataSrc对应
    private int iTotalDisplayRecords;
    private int iTotalRecords;

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public List<List<String>> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> resultList, AaDataInterface<T> aaDataConcru) {
        List<List<String>> aaData = new ArrayList<List<String>>();
        if (resultList != null) {
            for (T result : resultList) {
                if (result != null) {
                    aaData.add(aaDataConcru.wrap(result));
                }
            }
        }
        this.aaData = aaData;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public interface AaDataInterface<K> {
        List<String> wrap(K rm);
    }

}
