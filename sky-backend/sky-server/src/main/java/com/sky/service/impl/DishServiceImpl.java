package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    /**
     * 新增菜品和相对应的口味
     *
     * @param dishDTO 菜品DTO对象
     */
    @Transactional
    @Override
    public void addDishWithFlavors(DishDTO dishDTO) {
        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO, dish);
        //向菜品表插入1条数据
        dishMapper.insertDish(dish);

        //获取Insert语句生成的主键值
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> flavor.setDishId(dishId));
            //向口味表插入n条数据
            dishFlavorMapper.insertFlavorsBatch(flavors);
        }
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 菜品分页查询
     * @return PageResult类分页查询响应对象
     */
    @Override
    public PageResult getDishListPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.getDishListPage(dishPageQueryDTO);

        long total = page.getTotal();
        List<DishVO> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 菜品的批量删除
     *
     * @param ids 菜品id集合
     */
    @Transactional
    @Override
    public void deleteDishes(List<Long> ids) {
        //判断当前菜品能否能够删除--是否存在起售中的菜品
        for (Long id : ids) {
            Dish dish = dishMapper.getDishById(id);
            if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE))
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }

        //判断当前菜品是否能够删除--是否被套餐关联了
        List<Long> setmealIds = setmealDishMapper.getSetmealDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            //当前菜品被套餐关联了，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        /*
        //删除菜品表中的菜品数据
        //删除菜品关联的口味数据
        for (Long id : ids) {
            dishMapper.deleteDishById(id);
            dishFlavorMapper.deleteFlavorByDishId(id);
        }
        */

        //根据菜品id集合批量删除菜品数据
        dishMapper.deleteDishByIds(ids);
        //根据菜品id集合批量删除关联的口味数据
        dishFlavorMapper.deleteFlavorByDishIds(ids);
    }

    /**
     * 根据id查询菜品和口味数据
     *
     * @param id 菜品id
     * @return DishVO菜品VO对象类
     */
    @Override
    public DishVO getDishVOById(Long id) {
        DishVO dishVO = new DishVO();

        Dish dish = dishMapper.getDishById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getFlavorsByDishId(id);

        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    /**
     * 启用禁用菜品
     *
     * @param status 目标启用/禁用状态信息
     * @param id     菜品id
     */
    @Override
    public void startOrStopDish(Integer status, Long id) {
        Dish dish = new Dish();

        dish.setId(id);
        dish.setStatus(status);

        dishMapper.updateDish(dish);
    }

    /**
     * 根据id修改菜品和对应口味
     *
     * @param dishDTO 菜品DTO对象类
     */
    @Override
    public void updateDishWithFlavorById(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.updateDish(dish);

        Long dishId = dish.getId();
        dishFlavorMapper.deleteFlavorByDishId(dishId);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> flavor.setDishId(dishId));
            //向口味表插入n条数据
            dishFlavorMapper.insertFlavorsBatch(flavors);
        }
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return List<Dish>菜品集合类
     */
    @Override
    public List<Dish> getDishListByCategoryId(Long categoryId) {
        return dishMapper.getDishListByCategoryId(categoryId);
    }
}
