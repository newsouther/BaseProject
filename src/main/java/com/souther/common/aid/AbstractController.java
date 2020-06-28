/**
 * @filename:UserController 2020年5月19日
 * @project generalproject  V1.0 Copyright(c) 2020 souther Co. Ltd. All right reserved.
 */
package com.souther.common.aid;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.souther.vo.bo.CommonResult;
import com.souther.vo.po.PageParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 用户API接口层</P>
 *
 * @version: V1.0
 * @author: souther
 * @time 2020年5月19日
 */
public class AbstractController<S extends IService<T>, T> {

  @Autowired
  protected S baseService;

  /**
   * @param id:
   * @Description: 查询对象
   * @Author: souther
   * @Date: 2020/6/22 14:10
   * @return: com.souther.vo.bo.CommonResult<T>
   **/
  @GetMapping("/getById/{id}")
  @ApiOperation(value = "获取对象", notes = "作者：souther")
  @ApiImplicitParam(paramType = "path", name = "id", value = "对象id", required = true, dataType = "Long")
  public CommonResult<T> getById(@PathVariable("id") Long id) {
    CommonResult<T> result;
    T obj = baseService.getById(id);
    if (null != obj) {
      result = new CommonResult(obj);
    } else {
      result = CommonResult.error("查询对象不存在！");
    }
    return result;
  }

  /**
   * @param id:
   * @Description: 删除对象
   * @Author: souther
   * @Date: 2020/6/22 14:11
   * @return: com.souther.vo.bo.CommonResult<T>
   **/
  @GetMapping("/deleteById/{id}")
  @ApiOperation(value = "删除", notes = "作者：souther")
  @ApiImplicitParam(paramType = "path", name = "id", value = "对象id", required = true, dataType = "Long")
  public CommonResult<T> deleteById(@PathVariable("id") Long id) {
    CommonResult<T> result;
    T obj = baseService.getById(id);
    if (null != obj) {
      boolean rsg = baseService.removeById(id);
      if (rsg) {
        result = new CommonResult<>();
      } else {
        result = CommonResult.error("删除失败");
      }
    } else {
      result = CommonResult.error("删除的对象不存在！");
    }
    return result;
  }

  /**
   * @param entity:
   * @Description: 添加
   * @Author: souther
   * @Date: 2020/6/22 14:11
   * @return: com.souther.vo.bo.CommonResult<T>
   **/
  @PostMapping("/insert")
  @ApiOperation(value = "添加", notes = "作者：souther")
  public CommonResult<T> insert(@Validated @RequestBody T entity) {
    CommonResult<T> result;
    if (null != entity) {
      boolean rsg = baseService.save(entity);
      if (rsg) {
        result = new CommonResult<>();
      } else {
        result = CommonResult.error("添加失败");
      }
    } else {
      result = CommonResult.error("请传入正确参数！");
    }
    return result;
  }

  /**
   * @param entity:
   * @Description: 修改
   * @Author: souther
   * @Date: 2020/6/22 14:11
   * @return: com.souther.vo.bo.CommonResult<T>
   **/
  @PostMapping("/update")
  @ApiOperation(value = "修改", notes = "作者：souther")
  public CommonResult<T> update(@Validated @RequestBody T entity) {
    CommonResult<T> result;
    if (null != entity) {
      boolean rsg = baseService.updateById(entity);
      if (rsg) {
        result = new CommonResult<>();
      } else {
        result = CommonResult.error("修改失败");
      }
    } else {
      result = CommonResult.error("请传入正确参数！");
    }
    return result;
  }

  /**
   * @param param:
   * @Description: 分页条件查询用户
   * @Author: souther
   * @Date: 2020/6/22 14:11
   * @return: com.souther.vo.bo.CommonResult<com.baomidou.mybatisplus.core.metadata.IPage < T>>
   **/
  @PostMapping("/getPages")
  @ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<T>],作者：souther")
  public CommonResult<IPage<T>> getPages(@Validated @RequestBody PageParam<T> param) {
    CommonResult<IPage<T>> returnPage;
    Page<T> page = new Page<T>(param.getPageNum(), param.getPageSize());
    QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
    //模糊
    if (param.getLike() != null) {
      param.getLike().forEach((k, v) -> {
        queryWrapper.like(k, v);
      });
    }
    //排序
    if (param.getOrder() != null) {
      param.getOrder().forEach((k, v) -> {
        queryWrapper.orderBy(true, v, k);
      });
    }
    //eq
    queryWrapper.setEntity(param.getParam());
    //分页数据
    IPage<T> pageData = baseService.page(page, queryWrapper);
    returnPage = new CommonResult<>(pageData);

    return returnPage;
  }
}
