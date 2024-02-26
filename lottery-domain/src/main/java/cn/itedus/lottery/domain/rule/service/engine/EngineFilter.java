package cn.itedus.lottery.domain.rule.service.engine;

import cn.itedus.lottery.domain.rule.res.EngineResult;
import cn.itedus.lottery.domain.rule.req.DecisionMatterReq;

/**
 * @description: 规则过滤器引擎
 * @author：3500
 */
public interface EngineFilter {

    /**
     * 规则过滤器接口
     *
     * @param matter      规则决策物料
     * @return            规则决策结果
     */
    EngineResult process(final DecisionMatterReq matter);

}
