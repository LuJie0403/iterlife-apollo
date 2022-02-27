package com.iterlife.xdp.strategy.demo.impl;

import com.iterlife.xdp.strategy.demo.IDeductContextService;
import com.iterlife.xdp.strategy.demo.IDeductPerformer;
import com.iterlife.xdp.strategy.demo.entity.DeductBill;
import com.iterlife.xdp.strategy.demo.entity.DeductResult;

/**
 * @desc:
 * @author: lujie
 * @version: V1.0.0
 * @datetime: 2022/2/27 18:07
 **/
//@Service
public class DeductContextServiceImpl implements IDeductContextService {

    //@Autowired
    private BgwDeductPerformerImpl bgwDeductPerformer;
    //@Autowired
    private CouponDeductPerformerImpl couponDeductPerformer;
    //@Autowired
    private OfflinePayDeductPerformerImpl offlinePayDeductPerformer;
    //@Autowired
    private WeChatPayDeductPerformerImpl weChatPayDeductPerformer;
    //@Autowired
    private PaymentCardDeductPerformerImpl paymentCardDeductPerformer;


    private IDeductPerformer fetchDeductChannel(String payChannel) {
        switch (payChannel) {
            case "DOCKING":
                return this.bgwDeductPerformer;
            case "DEDUCT_PAY":
            case "COUPON_PAY":
                return this.couponDeductPerformer;
            case "OFFLINE_PAY":
                return this.offlinePayDeductPerformer;
            case "WECHAT_PAY":
                return this.weChatPayDeductPerformer;
            default:
                return this.paymentCardDeductPerformer;
        }
    }

    @Override
    public DeductResult doDeducting(DeductBill deductBill) {
        return fetchDeductChannel(deductBill.getPayChannel()).doRepay(deductBill);
    }

    @Override
    public DeductResult queryDeductResult(DeductBill deductBill) {
        return fetchDeductChannel(deductBill.getPayChannel()).doQuery(deductBill);
    }
}
