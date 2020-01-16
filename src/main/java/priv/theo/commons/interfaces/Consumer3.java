package priv.theo.commons.interfaces;

/**
 * 用于支持三参数的 setter 方
 * 用于辅助 {@link priv.theo.commons.util.GenericBuilder}
 *
 * @author theohuang
 * @date 2020/1/16
 */
@FunctionalInterface
public interface Consumer3<T, P1, P2, P3> {

    void accept(T t, P1 p1, P2 p2, P3 p3);
}
