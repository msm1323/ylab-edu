package ru.msm.edu.lesson_3.DatedMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap {

    private Map<String, String> map = new HashMap<>();
    private Map<String, Date> mapDates = new HashMap<>();

    @Override
    public void put(String key, String value) {
        map.put(key, value);
        mapDates.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
        mapDates.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return mapDates.get(key);
    }
}
