package com.resource.admin.utils;

import com.querydsl.core.support.FetchableQueryBase;
import com.querydsl.core.support.QueryBase;

import java.util.List;

public class QueryUtil {

    public static <T> List<T> fetchFromQueryBase(QueryBase queryBase) {
        return ((FetchableQueryBase) queryBase).fetch();
    }

    public static long fetchCountFromQueryBase(QueryBase queryBase) {
        return ((FetchableQueryBase) queryBase).fetchCount();
    }
}
