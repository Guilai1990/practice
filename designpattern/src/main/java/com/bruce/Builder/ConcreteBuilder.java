package com.bruce.Builder;

/**
 * @Author: Bruce
 * @Date: 2019/5/15 22:17
 * @Version 1.0
 */

/**
 * 具体的生成器实现对象
 */
public class ConcreteBuilder implements Builder {
    /**
     * 生成器最终构建
     */
    private Product resultProduct;

    public Product getResultProduct() {
        return resultProduct;
    }

    @Override
    public void buildPart() {
        //构建某个部件的功能处理
    }
}
