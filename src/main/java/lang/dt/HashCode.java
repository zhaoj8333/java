package lang.dt;

/**
 * {@link java.util.List}, {@link java.util.Set}，使用equals对比费时
 * 哈希算法用来提高集合查找效率，将集合分成若干存储区域，每个对象拥有 {@link Object#hashCode()}，hash码，将hash分组，对应某个存储区域，
 * 通过hash码快速定位某个元素，大大减少 equals方法的调用次数。
 */
public class HashCode {
}
