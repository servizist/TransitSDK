package it.sad.sii.transit.sdk.model;

/**
 * Created by ldematte on 10/6/14.
 * <p/>
 * Java has no pair class... How dumb is that?? :D
 */
public class Pair<T, U> implements Comparable<Pair<T, U>> {

    public final T first;
    public final U second;

    private Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public static <T, U> Pair<T, U> create(T first, U second) {
        return new Pair<T, U>(first, second);
    }

    @Override
    public int compareTo(Pair<T, U> o) {
        int cmp = compare(first, o.first);
        return cmp == 0 ? compare(second, o.second) : cmp;
    }

    private static int compare(Object o1, Object o2) {
        return o1 == null ? o2 == null ? 0 : -1 : o2 == null ? +1
                : ((Comparable)o1).compareTo(o2);
    }

    @Override
    public int hashCode() {
        return 31 * hashCodeHelper(first) + hashCodeHelper(second);
    }

    private static int hashCodeHelper(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair))
            return false;
        if (this == obj)
            return true;
        return equal(first, ((Pair)obj).first)
               && equal(second, ((Pair)obj).second);
    }

    // todo move this to a helper class.
    private boolean equal(Object o1, Object o2) {
        return o1 == null ? o2 == null : (o1 == o2 || o1.equals(o2));
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ')';
    }
}
