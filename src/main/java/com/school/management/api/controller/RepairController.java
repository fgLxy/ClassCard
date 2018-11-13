package com.school.management.api.controller;

import com.school.management.api.entity.Repair;
import com.school.management.api.repository.RepairJpaRepository;
import com.school.management.api.results.JsonObjectResult;
import com.school.management.api.results.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/school/repair")
public class RepairController {

    @Autowired
    private RepairJpaRepository jpa;

    @GetMapping("/list")
    public Object list(@RequestParam(name = "page", defaultValue = "1") int page, String classCode) {
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", jpa.findAllByClassCode(Integer.parseInt(classCode), PageRequest.of(page - 1, 8)));
    }

    /**
     * @return 报修物品清单
     */
    @PostMapping("/showThing")
    public Object showThing() {
        List<String> list = new ArrayList<>();
        list.add("窗户（玻璃）");
        list.add("门");
        list.add("灯");
        list.add("黑板");
        list.add("讲台");
        list.add("桌椅");
        list.add("墙");
        list.add("地面");
        list.add("其他");
        return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", list);
    }

    /**
     * @param classCode  班级编号
     * @param repairDate 保修日期
     * @return 报修实体类 {@link Repair}
     */
    @PostMapping("/showRepair")
    public Object showRepair(int classCode, String repairDate) {
        Repair repair = jpa.findByClassCodeAndRepairDate(classCode, repairDate);
        if (repair != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "获取数据成功", repair);
        } else {
            return new JsonObjectResult(ResultCode.PARAMS_ERROR, "获取数据失败");
        }
    }

    /**
     * @param classCode  班级编号
     * @param repairDate 报修日期
     * @param thing      报修物品
     * @return 添加成功后的报修信息
     */
    @PostMapping("/addRepair")
    public Object addRepair(int classCode, String repairDate, String thing) {
        Repair repair = new Repair();
        repair.setRepairDate(repairDate);
        repair.setRepairThings(thing);
        repair.setClassCode(classCode);
        return new JsonObjectResult(ResultCode.SUCCESS, "添加数据成功", jpa.save(repair));
    }

    @PostMapping("/add")
    public Object add(Repair repair) {
        return new JsonObjectResult(ResultCode.SUCCESS, "添加成功", jpa.saveAndFlush(repair));
    }

    @PostMapping("/delete")
    public Object delete(String id) {
        Repair repair = jpa.findByRepairId(Long.parseLong(id));
        if (repair != null) {
            jpa.delete(repair);
            return new JsonObjectResult(ResultCode.SUCCESS, "删除成功");
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "删除失败");
    }

    @PostMapping("/deleteSome")
    public Object deleteSome(String Ids) {
        String[] repairIds = Ids.split(",");
        for (String repairId : repairIds) {
            jpa.delete(jpa.findByRepairId(Long.parseLong(repairId)));
        }
        return new JsonObjectResult(ResultCode.SUCCESS, "批量删除成功");
    }

    @PostMapping("/update")
    public Object update(Repair repair) {
        Repair old = jpa.findByRepairId(repair.getRepairId());
        if (old != null) {
            return new JsonObjectResult(ResultCode.SUCCESS, "修改成功", jpa.saveAndFlush(repair));
        }
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "修改失败");
    }

    @PostMapping("/query")
    public Object query(String classCode, String query) {
        System.out.println(classCode + "\t:\t" + query);
        return new JsonObjectResult(ResultCode.SUCCESS, "", jpa.findByClassCodeAndRepairDateLike(Integer.parseInt(classCode), query + "%"));
    }

    @PostMapping("/all")
    public Object all(String classCode) {
        if (classCode != null)
            return new JsonObjectResult(ResultCode.SUCCESS, "", jpa.findByClassCodeAndRepairStatus(Integer.parseInt(classCode), 0));
        return new JsonObjectResult(ResultCode.PARAMS_ERROR, "班级编号不能为空");
    }

    @PostMapping("/allRepair")
    public Object allRepair() {
        return new JsonObjectResult(ResultCode.SUCCESS, "", jpa.findByRepairStatus(0));
    }

    @GetMapping("/listComplete")
    public Object listComplete(String classCode, @RequestParam(defaultValue = "1") int page) {
        return new JsonObjectResult(ResultCode.SUCCESS, "", jpa.findByRepairStatusAndClassCode(1, Integer.parseInt(classCode), PageRequest.of(page - 1, 8)));
    }
}
