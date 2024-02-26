package cn.itedus.lottery.domain.rule.service.logic.Impl;

import cn.itedus.lottery.domain.rule.req.DecisionMatterReq;
import cn.itedus.lottery.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Component;

/**
 * @description: 年龄规则
 * @author：3500
 */

@Component
public class UserAgeFilter extends BaseLogic {
    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("age").toString();
    }
}
