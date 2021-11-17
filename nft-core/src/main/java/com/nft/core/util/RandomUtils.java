package com.nft.core.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class RandomUtils extends org.apache.commons.lang3.RandomUtils {

    public static <K, V> Optional<Map.Entry<K, V>> nextMap(Map<K, V> map) {
        if (CollectionUtils.isEmpty(map)) {
            return Optional.empty();
        }

        List<K> kList = new ArrayList<>(map.keySet());
        K k = kList.get(RandomUtils.nextInt(0, kList.size() - 1));
        for (Map.Entry<K, V> kvEntry : map.entrySet()) {
            if (!kvEntry.getKey().equals(k)) {
                continue;
            }
            return Optional.of(kvEntry);
        }
        return Optional.empty();
    }

    public <E> Optional<E> nextList(List<E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Optional.empty();
        }
        return Optional.of(list.get(RandomUtils.nextInt(0, list.size() - 1)));
    }
}
