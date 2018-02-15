package com.kaizensoftware.students.util;

import com.kaizensoftware.students.dao.common.ResponseDescription;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VakSF
 */
public class ResponseValue<T> {

    private ResponseDescription responseDescription;

    private int responseId;
    private List<T> responseList;
    private T responseItem;

    private List<String> responseListDescription;

    public ResponseValue() {
        this.responseList = new ArrayList<T>();
        this.responseListDescription = new ArrayList<String>();
    }

    public ResponseValue(T item) {
        this.responseList = new ArrayList<T>();
        this.responseListDescription = new ArrayList<String>();
        this.responseItem = item;
    }

    public ResponseDescription getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(ResponseDescription responseDescription) {
        this.responseDescription = responseDescription;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public List<T> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<T> responseList) {
        this.responseList = responseList;
    }

    public T getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(T responseItem) {
        this.responseItem = responseItem;
    }

    public List<String> getResponseListDescription() {
        return responseListDescription;
    }

    public void setResponseListDescription(List<String> responseListDescription) {
        this.responseListDescription = responseListDescription;
    }

    @Override
    public String toString() {
        return  "ResponseValue{"
                + "responseDescription=" + responseDescription + ", "
                + "responseId=" + responseId + ", "
                + "responseItem=" + responseItem + ", "
                + "responseListDescription=" + responseListDescription + ", "
                + "responseList=" + responseList +
                "}";
    }

}
