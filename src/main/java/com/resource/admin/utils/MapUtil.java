package com.resource.admin.utils;

import com.querydsl.core.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtil {

    public static List<Map<String, Object>> fromTuple(List<Tuple> tupleList, List<String> keyList) {
        return tupleList.stream().map(
                p -> fromTuple(p, keyList)
        ).collect(Collectors.toList());
    }

    public static List<Map<String, Object>> fromTupleParallel(List<Tuple> tupleList, List<String> keyList) {
        return tupleList.stream().parallel().map(
                p -> fromTuple(p, keyList)
        ).collect(Collectors.toList());
    }

    private static Map<String, Object> fromTuple(Tuple tuple, List<String> keyList) {
        Map<String, Object> map = new HashMap();
        int counter = 0;
        for (String key : keyList) {
            map.put(key, tuple.get(counter++, Object.class));
        }
        return map;
    }

}


