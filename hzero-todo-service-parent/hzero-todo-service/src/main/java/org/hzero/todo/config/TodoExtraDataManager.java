package org.hzero.todo.config;

import org.springframework.beans.factory.annotation.Autowired;

import io.choerodon.core.swagger.ChoerodonRouteData;
import io.choerodon.swagger.annotation.ChoerodonExtraData;
import io.choerodon.swagger.swagger.extra.ExtraData;
import io.choerodon.swagger.swagger.extra.ExtraDataManager;

/**
 * 服务路由配置
 *
 * @Date 2020/8/14 15:31
 * @Author Summer_OneTree
 */
@ChoerodonExtraData
public class TodoExtraDataManager implements ExtraDataManager {

    @Autowired
    private org.springframework.core.env.Environment environment;

    @Override
    public ExtraData getData() {
        ChoerodonRouteData choerodonRouteData = new ChoerodonRouteData();
        choerodonRouteData.setName(environment.getProperty("hzero.service.current.name", "htdo"));
        choerodonRouteData.setPath(environment.getProperty("hzero.service.current.path", "/todo/**"));
        choerodonRouteData.setServiceId(environment.getProperty("hzero.service.current.service-name", "hzero-todo-service"));
        extraData.put(ExtraData.ZUUL_ROUTE_DATA, choerodonRouteData);
        return extraData;
    }
}
