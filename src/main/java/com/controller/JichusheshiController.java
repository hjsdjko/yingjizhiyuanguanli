package com.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.JichusheshiEntity;

import com.service.JichusheshiService;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 基础设施
 * 后端接口
 * @author
 * @email
 * @date 2021-03-12
*/
@RestController
@Controller
@RequestMapping("/jichusheshi")
public class JichusheshiController {
    private static final Logger logger = LoggerFactory.getLogger(JichusheshiController.class);

    @Autowired
    private JichusheshiService jichusheshiService;

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params){
        logger.debug("Controller:"+this.getClass().getName()+",page方法");
        PageUtils page = jichusheshiService.queryPage(params);
        return R.ok().put("data", page);
    }
    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("Controller:"+this.getClass().getName()+",info方法");
        JichusheshiEntity jichusheshi = jichusheshiService.selectById(id);
        if(jichusheshi!=null){
            return R.ok().put("data", jichusheshi);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody JichusheshiEntity jichusheshi, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",save");
        Wrapper<JichusheshiEntity> queryWrapper = new EntityWrapper<JichusheshiEntity>()
            .eq("name", jichusheshi.getName())
            .eq("jianjie_content", jichusheshi.getJianjieContent())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        JichusheshiEntity jichusheshiEntity = jichusheshiService.selectOne(queryWrapper);
        if("".equals(jichusheshi.getImgPhoto()) || "null".equals(jichusheshi.getImgPhoto())){
            jichusheshi.setImgPhoto(null);
        }
        if(jichusheshiEntity==null){
            jichusheshiService.insert(jichusheshi);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody JichusheshiEntity jichusheshi, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",update");
        //根据字段查询是否有相同数据
        Wrapper<JichusheshiEntity> queryWrapper = new EntityWrapper<JichusheshiEntity>()
            .notIn("id",jichusheshi.getId())
            .eq("name", jichusheshi.getName())
            .eq("jianjie_content", jichusheshi.getJianjieContent())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        JichusheshiEntity jichusheshiEntity = jichusheshiService.selectOne(queryWrapper);
        if("".equals(jichusheshi.getImgPhoto()) || "null".equals(jichusheshi.getImgPhoto())){
                jichusheshi.setImgPhoto(null);
        }
        if(jichusheshiEntity==null){
            jichusheshiService.updateById(jichusheshi);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        logger.debug("Controller:"+this.getClass().getName()+",delete");
        jichusheshiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}

