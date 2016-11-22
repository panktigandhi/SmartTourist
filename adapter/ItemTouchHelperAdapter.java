package majorproject.amity.smarttourist.adapter;

/**
 * Created by vijim_000 on 6/6/2016.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}