package com.school.management.api.controller;


import com.school.management.api.entity.FoodMenu;
import com.school.management.api.repository.FoodMenuJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import com.school.management.api.utils.ImgUtils;
import com.school.management.api.vo.FoodVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/school/food")
public class FoodMenuController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FoodMenuJpaRepository foodJpa;

    /**
     * @return 当天菜谱
     */
    @PostMapping("/todayFood")
    public Object menuList() {
        List list = foodJpa.findByMenuWeekOfDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1);
        if (list.size() != 0) {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    /**
     * @param dinner 1、早餐  2、午餐   3、晚餐
     * @return 早、午、晚餐
     */
    @PostMapping("/listBLS")
    public Object listBLS(int dinner) {
        List<FoodMenu> list = foodJpa.findByMenuDinnerAndMenuWeekOfDay(dinner, Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1);
        if (list.size() != 0) {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    /**
     * @param week 星期几
     * @return 星期几的菜谱
     */
    @PostMapping("/weekFood")
    public Object list(int week) {
        List<FoodMenu> list = foodJpa.findByMenuWeekOfDay(week);
        if (list.size() != 0) {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    /**
     * @param foodMenu {@link FoodMenu}
     * @return 更新后的 食谱  对象
     */
    @PostMapping("/update")
    public Object updateFood(FoodMenu foodMenu) {
        try {
            FoodMenu food = foodJpa.findByMenuId(foodMenu.getMenuId());
            if (food != null) {
                return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", foodJpa.saveAndFlush(foodMenu));
            } else {
                return new JsonObjectResult(ResultCode.PARAMS_ERROR, "找不到待修改的数据");
            }
        } catch (Exception e) {
            logger.warn("更新失败", e);
            return new JsonObjectResult(ResultCode.EXCEPTION, "后台发生异常");
        }
    }

    /**
     * @param menuId 食谱ID
     * @return true/false
     */
    @PostMapping("/delete")
    public Object deleteFood(String menuId) {
        System.out.println("menuID:\t" + menuId);
        try {
            foodJpa.delete(foodJpa.findByMenuId(Long.parseLong(menuId)));
            return new JsonObjectResult(ResultCode.SUCCESS, "删除数据成功", true);
        } catch (Exception e) {
            logger.warn("删除失败", e);
            return new JsonObjectResult(ResultCode.EXCEPTION, "删除数据失败");
        }
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String Ids) {
        String[] ids = Ids.split(",");
        for (String id : ids) {
            foodJpa.delete(foodJpa.findByMenuId(Long.parseLong(id)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
    }

    /**
     * @param foodMenu {@link FoodMenu}
     * @return 新增的食谱
     */
    @PostMapping("/add")
    public Object addFood(FoodMenu foodMenu, String fileName) {
        try {
//            foodMenu.setMenuDate(new java.sql.Date(System.currentTimeMillis()));
            foodMenu.setMenuStapleFoodImageUrl(ImgUtils.base64ToImg(foodMenu.getMenuStapleFoodImageUrl(), fileName, "food"));
            return new JsonObjectResult(ResultCode.SUCCESS, "增加数据成功", foodJpa.saveAndFlush(foodMenu));
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("新增失败" + e.getMessage());
            return new JsonObjectResult(ResultCode.EXCEPTION, "增加数据失败");
        }
    }

    @GetMapping("/list")
    public Object listAll(@RequestParam(defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", foodJpa.findAll(PageRequest.of(page - 1, 8)));
    }

    @PostMapping("/query")
    public Object query(String query, @RequestParam(defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", foodJpa.findByMenuWeekOfDay(Integer.parseInt(query), PageRequest.of(page - 1, 8)));
    }

    @PostMapping("/all")
    public Object all() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> datas = new ArrayList<>();
        List<java.sql.Date> dates = foodJpa.getDate();
        for (java.sql.Date date : dates) {
            if (date != null) {
                List<Map<String, Object>> menus = foodJpa.getAllByMenuDate(date.toString());
                Map<String, Object> data = new HashMap<>();
                List<FoodVo> voList = new ArrayList<>();
                for (Map<String, Object> menu : menus) {
                    voList.add(new FoodVo(menu.get("menu_staple_food_name").toString(), Integer.parseInt(menu.get("menu_dinner").toString())));
                }
                data.put("foodDate", sdf.format(date));
                data.put("food", voList);
                datas.add(data);
            }
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "", datas);
    }
}
