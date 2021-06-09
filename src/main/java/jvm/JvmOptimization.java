package jvm;

/**
 * jvm调优
 *  1. jvm参数分类:
 *      标准: -开头, 所有hotspot支持
 *      非标准: -X开头, 特定hotspot支持
 *      不稳定: -XX, 下个版本取消
 *         java -XX:+PrintFlagsFinal
 *         java -XX:+PrintFlagsInitial
 *         java -XX:+UseG1GC
 *         java -XX:+PrintCommandLineFlags
 *          -XX:InitialHeapSize=259982592
 *          -XX:MaxHeapSize=4159721472
 *          -XX:+PrintCommandLineFlags
 *          -XX:+UseCompressedClassPointers
 *          -XX:+UseCompressedOops
 *          -XX:+UseParallelGC
 */
public class JvmOptimization {
}
