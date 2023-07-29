package com.fu.redisdemo.service;

/**
 * 限时活动（秒杀）
 */
public interface FlashSaleService {

    /**
     * 卖出商品
     */
    String sale();

    String saleByRedisson();

}
