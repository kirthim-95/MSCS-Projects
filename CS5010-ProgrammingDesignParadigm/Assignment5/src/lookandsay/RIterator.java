package lookandsay;

import java.util.Iterator;

public interface RIterator<T> extends Iterator<T> {

    T prev();

    boolean hasPrevious();
}
