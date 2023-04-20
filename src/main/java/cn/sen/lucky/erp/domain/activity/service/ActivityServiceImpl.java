package cn.sen.lucky.erp.domain.activity.service;


import cn.sen.lucky.common.Constants;
import cn.sen.lucky.erp.application.IActivityService;
import cn.sen.lucky.erp.domain.activity.model.ActivityListPageReq;
import cn.sen.lucky.erp.infrastructure.common.EasyResult;

import cn.sen.lucky.rpc.activity.deploy.ILuckyActivityDeploy;
import cn.sen.lucky.rpc.activity.deploy.req.ActivityPageReq;
import cn.sen.lucky.rpc.activity.deploy.res.ActivityRes;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @description: 活动服务
 * @author: 小傅哥，微信：fustack
 * @date: 2021/12/11
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@Service
public class ActivityServiceImpl implements IActivityService {

//    @DubboReference(interfaceClass = ILotteryActivityDeploy.class, url = "dubbo://127.0.0.1:20880")
    @DubboReference(interfaceClass = ILuckyActivityDeploy.class)
    private ILuckyActivityDeploy activityDeploy;

    @Override
    public EasyResult queryActivityListPage(ActivityListPageReq req) {
        // 1. 组装参数
        ActivityPageReq activityPageReq = new ActivityPageReq(req.getPage(), req.getRows());
        activityPageReq.setErpId(req.getErpId());
        activityPageReq.setActivityId(req.getActivityId());
        activityPageReq.setActivityName(req.getActivityName());

        // 2. 查询数据
        ActivityRes activityRes = activityDeploy.queryActivityListByPageForErp(activityPageReq);

        // 3. 封装结果
        if (Constants.ResponseCode.SUCCESS.getCode().equals(activityRes.getResult().getCode())) {
            return EasyResult.buildEasyResultSuccess(activityRes.getCount(), activityRes.getActivityDTOList());
        } else {
            return EasyResult.buildEasyResultError(activityRes.getResult().getCode());
        }

    }

}
