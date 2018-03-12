package com.base.baselibrary.utils.permission.lib.inter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public interface Action {
    /**
     * An action.
     *
     * @param permissions the current action under permissions.
     */
    void onAction(List<String> permissions);
}
