package com.school.management.api.repository;

import com.school.management.api.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public interface FoodMenuJpaRepository extends JpaRepository<FoodMenu, Long> {

    /**
     * @param weekOfDay 星期几
     * @return 根据星期几来查询对应的食谱信息
     */
    List<FoodMenu> findByMenuWeekOfDay(int weekOfDay);

    /**
     * @param where 早  午  晚
     * @param week 星期几
     * @return 根据早午晚餐来查询食谱信息
     */
    List<FoodMenu> findByMenuDinnerAndMenuWeekOfDay(int where, int week);

    /**
     * @param menuID 食谱ID
     * @return 根据食谱ID来查询食谱
     */
    FoodMenu findByMenuId(long menuID);

    @Query(nativeQuery = true, value = "select menu_date from food_menu group by menu_date")
    List<Date> getDate();

    @Query(value = "select food_menu.menu_dinner, food_menu.menu_staple_food_name from food_menu where food_menu.menu_date=?1", nativeQuery = true)
    List<Map<String, Object>> getAllByMenuDate(String date);
}
