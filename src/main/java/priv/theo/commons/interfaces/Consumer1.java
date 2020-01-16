package priv.theo.commons.interfaces;

/**
 * 用于支持单一参数的 set 方法
 * 用于辅助 {@link priv.theo.commons.util.GenericBuilder}
 *
 * @author theohuang
 * @date 2020/1/16
 */
@FunctionalInterface
public interface Consumer1<T, P1> {

    void accept(T t, P1 p1);
}
