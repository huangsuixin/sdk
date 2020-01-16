package priv.theo.commons.interfaces;

/**
 * @author theohuang
 * @date 2020/1/16
 */
@FunctionalInterface
public interface Consumer2<T, P1, P2> {

    void accept(T t, P1 p1, P2 p2);
}
