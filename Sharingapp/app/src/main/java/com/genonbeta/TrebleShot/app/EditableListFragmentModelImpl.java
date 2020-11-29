package com.genonbeta.TrebleShot.app;

import com.genonbeta.TrebleShot.widget.EditableListAdapter;


public interface EditableListFragmentModelImpl<V extends EditableListAdapter.EditableViewHolder>
{
    void setLayoutClickListener(EditableListFragment.LayoutClickListener<V> clickListener);
}
