package cn.itedus.lottery.domain.rule.service.engine;

import cn.itedus.lottery.domain.rule.service.logic.Impl.UserGenderFilter;
import cn.itedus.lottery.domain.rule.service.logic.LogicFilter;
import cn.itedus.lottery.domain.rule.service.logic.Impl.UserAgeFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 引擎信息规则配置
 * @author：3500
 */
public class EngineConfig {

    //保证线程安全
    protected static Map<String, LogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    @Resource
    private UserAgeFilter userAgeFilter;

    @Resource
    private UserGenderFilter userGenderFilter;

    //完成构造之后，进行初始化
    @PostConstruct
    public void init( ) {
        logicFilterMap.put("userAge", userAgeFilter);
        logicFilterMap.put("userGender", userGenderFilter);
    }
}
