package com.base.baselibrary.utils.video.videoCreator;

/**
 * 提供数据方
 *
 * @param <T>
 */
public interface IProvider<T> {
    boolean hasNext();

    int size();

    T next();
}
