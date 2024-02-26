package cn.itedus.lottery.infrastructure.repository;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.infrastructure.dao.RuleTreeDao;
import cn.itedus.lottery.infrastructure.dao.RuleTreeNodeDao;
import cn.itedus.lottery.infrastructure.dao.RuleTreeNodeLineDao;
import cn.itedus.lottery.infrastructure.po.RuleTree;
import cn.itedus.lottery.infrastructure.po.RuleTreeNode;
import cn.itedus.lottery.infrastructure.po.RuleTreeNodeLine;
import cn.itedus.lottery.domain.rule.model.aggregates.TreeRuleRich;
import cn.itedus.lottery.domain.rule.model.vo.TreeNodeLineVO;
import cn.itedus.lottery.domain.rule.model.vo.TreeNodeVO;
import cn.itedus.lottery.domain.rule.model.vo.TreeRootVO;
import cn.itedus.lottery.domain.rule.repository.IRuleRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 规则信息仓储服务
 * @author：3500
 */

@Repository
public class RuleRepository implements IRuleRepository {

    @Resource
    private RuleTreeDao ruleTreeDao;

    @Resource
    private RuleTreeNodeDao ruleTreeNodeDao;

    @Resource
    private RuleTreeNodeLineDao ruleTreeNodeLineDao;

    @Override
    public TreeRuleRich queryTreeRuleRich(Long treeId) {

        // 1，获得规则树树根
        RuleTree ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        TreeRootVO treeRootVO = new TreeRootVO();
        treeRootVO.setTreeId(ruleTree.getId());
        treeRootVO.setTreeRootNodeId(ruleTree.getTreeRootNodeId());
        treeRootVO.setTreeName(ruleTree.getTreeName());

        // 2.树节点->树连接线
        // 先获得所有的节点，后续进行连接操作
        Map<Long, TreeNodeVO> treeNodeMap = new HashMap<>();
        List<RuleTreeNode> ruleTreeNodeList =  ruleTreeNodeDao.queryRuleTreeNodeList(treeId);
        //进行遍历节点，判断类型，获取连接线，收集信息内容，进行关系映射
        for(RuleTreeNode treeNode : ruleTreeNodeList) {
            //用于收集叶子的连接合集
            List<TreeNodeLineVO> treeNodeLineInfoList = new ArrayList<>();
            if(Constants.NodeType.STEM.equals(treeNode.getNodeType())) {

                //获得与叶子节点的连线
                RuleTreeNodeLine ruleTreeNodeLineReq = new RuleTreeNodeLine();
                ruleTreeNodeLineReq.setTreeId(treeId);
                ruleTreeNodeLineReq.setNodeIdFrom(treeNode.getId());
                List<RuleTreeNodeLine> ruleTreeNodeLineList = ruleTreeNodeLineDao.queryRuleTreeNodeLineList(ruleTreeNodeLineReq);

                //对叶子节点连线进行收集
                for(RuleTreeNodeLine nodeLine : ruleTreeNodeLineList) {
                    TreeNodeLineVO treeNodeLineInfo = new TreeNodeLineVO();
                    treeNodeLineInfo.setNodeIdFrom(nodeLine.getNodeIdFrom());
                    treeNodeLineInfo.setNodeIdTo(nodeLine.getNodeIdTo());
                    treeNodeLineInfo.setRuleLimitType(nodeLine.getRuleLimitType());
                    treeNodeLineInfo.setRuleLimitValue(nodeLine.getRuleLimitValue());
                    treeNodeLineInfoList.add(treeNodeLineInfo);
                }
            }

            //把与根节点有关的连线内容进行收集
            TreeNodeVO treeNodeInfo = new TreeNodeVO();
            treeNodeInfo.setTreeId(treeNode.getTreeId());
            treeNodeInfo.setTreeNodeId(treeNode.getId());
            treeNodeInfo.setNodeType(treeNode.getNodeType());
            treeNodeInfo.setNodeValue(treeNode.getNodeValue());
            treeNodeInfo.setRuleKey(treeNode.getRuleKey());
            treeNodeInfo.setRuleDesc(treeNode.getRuleDesc());
            treeNodeInfo.setTreeNodeLineInfoList(treeNodeLineInfoList);

            //进行关系映射
            treeNodeMap.put(treeNode.getId(), treeNodeInfo);
        }

        //封装业务信息：根节点，每个节点的连线信息
        TreeRuleRich treeRuleRich = new TreeRuleRich();
        treeRuleRich.setTreeRoot(treeRootVO);
        treeRuleRich.setTreeNodeMap(treeNodeMap);

        return treeRuleRich;
    }
}
