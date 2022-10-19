package com.interview.roomoccupancymanager.service.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionHelpers {
    public static <E extends Comparable<E>> List<E> reverse(@NonNull final Collection<? extends E> collection) {
        return collection.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }
}
