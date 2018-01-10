package com.nowcoder.wenda.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${ywj} on 2017/12/21.
 */
public class ViewObject
{
    private Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }

}
