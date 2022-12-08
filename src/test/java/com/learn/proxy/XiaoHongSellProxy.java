package com.learn.proxy;

public class XiaoHongSellProxy implements SellPerfume {

    private SellPerfume sellPerfumeFactory;

    public XiaoHongSellProxy(SellPerfume sellPerfumeFactory) {
        this.sellPerfumeFactory = sellPerfumeFactory;
    }

    @Override
    public void sellPerfume(double price) {
        doSomethingBeforeSell(); // 前置增强
        sellPerfumeFactory.sellPerfume(price);
        doSomethingAfterSell(); // 后置增强
    }

    private void doSomethingBeforeSell() {
        System.out.println("小红代理购买香水前的额外操作...");
    }

    private void doSomethingAfterSell() {
        System.out.println("小红代理购买香水后的额外操作...");
    }
}
