package priv.theo.commons.interfaces;

/**
 * @author theohuang
 * @date 2020/1/16
 */
@FunctionalInterface
public interface Consumer1<T, P1> {

    void accept(T t, P1 p1);
}
