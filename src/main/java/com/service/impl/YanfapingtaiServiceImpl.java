package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;

import com.dao.YanfapingtaiDao;
import com.entity.YanfapingtaiEntity;
import com.service.YanfapingtaiService;
import com.entity.view.YanfapingtaiView;

/**
 * 研发平台 服务实现类
 * @since 2021-03-12
 */
@Service("yanfapingtaiService")
@Transactional
public class YanfapingtaiServiceImpl extends ServiceImpl<YanfapingtaiDao, YanfapingtaiEntity> implements YanfapingtaiService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        if(params != null && (params.get("limit") == null || params.get("page") == null)){
            params.put("page","1");
            params.put("limit","10");
        }
        Page<YanfapingtaiView> page =new Query<YanfapingtaiView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }

}
