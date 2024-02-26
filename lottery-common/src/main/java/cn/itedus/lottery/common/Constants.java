package cn.itedus.lottery.common;

import com.sun.org.apache.xerces.internal.util.FeatureState;

/**
 * 枚举信息自定义
 */
public class Constants {

    /**
     * 决策树节点
     */
    public static final class NodeType{
        /** 树茎 */
        public static final Integer STEM = 1;
        /** 果实 */
        public static final Integer FRUIT = 2;
    }

    /**
     * 全局属性
     */
    public static final class Global {
        /** 空节点值 */
        public static final Long TREE_NULL_NODE = 0L;
    }

    /**
     * 规则限定类型
     */
    public static final class RuleLimitType {
        /** 等于 */
        public static final int EQUAL = 1;
        /** 大于 */
        public static final int GT = 2;
        /** 小于 */
        public static final int LT = 3;
        /** 大于&等于 */
        public static final int GE = 4;
        /** 小于&等于 */
        public static final int LE = 5;
        /** 枚举 */
        public static final int ENUM = 6;
    }




}
