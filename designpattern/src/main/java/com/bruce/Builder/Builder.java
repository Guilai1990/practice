package com.bruce.Builder;

/**
 * @Author: Bruce
 * @Date: 2019/5/15 22:15
 * @Version 1.0
 */

/**
 * 生成器接口，定义创建一个产品对象所需的各个部件的操作
 */
public interface Builder {
    /**
     * 示意方法，构建某个部件
     */
    public void buildPart();
}
