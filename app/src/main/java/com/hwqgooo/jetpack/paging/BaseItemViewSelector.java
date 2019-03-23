package com.hwqgooo.jetpack.paging;

import com.hwqgooo.jetpack.utils.recyclerview.ItemViewSelector;
import com.hwqgooo.jetpack.utils.recyclerview.ItemView;

public abstract class BaseItemViewSelector<T> implements ItemViewSelector<T> {
    private static final ItemViewSelector EMPTY = new BaseItemViewSelector() {
        @Override
        public void select(ItemView itemView, int position, Object item) {

        }
    };

    /**
     * Returns an empty {@link ItemViewSelector}, i.e. one that does nothing on {@link
     * #select(ItemView, int, Object)}.
     */
    @SuppressWarnings("unchecked")
    public static <T> ItemViewSelector<T> empty() {
        return EMPTY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int viewTypeCount() {
        return 1;
    }
}