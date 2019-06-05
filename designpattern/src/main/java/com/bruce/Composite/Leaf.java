package com.bruce.Composite;

/**
 * @Author: Bruce
 * @Date: 2019/5/16 15:19
 * @Version 1.0
 */

/**
 * 叶子对象，叶子对象不再包含其他子对象
 */
public class Leaf extends Component {
    /**
     * 示意方法，叶子对象可能有自己的功能方法
     */
    @Override
    public void someOperation() {
        // do something
    }
}
