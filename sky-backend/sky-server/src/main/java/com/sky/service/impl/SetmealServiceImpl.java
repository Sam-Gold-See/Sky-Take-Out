package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;

    /**
     * 根据id查询套餐
     *
     * @param id 套餐id
     * @return SetmealVO套餐VO类对象
     */
    @Transactional
    @Override
    public SetmealVO getSetmealVOById(Long id) {
        SetmealVO setmealVO = setmealMapper.getSetmealVOById(id);

        String categoryName = categoryMapper.getCategoryNameById(id);
        setmealVO.setCategoryName(categoryName);

        List<SetmealDish> list = setmealDishMapper.getSetmealDishListBySetmealId(id);
        setmealVO.setSetmealDishes(list);

        return setmealVO;
    }

    /**
     * 新增套餐
     *
     * @param setmealDTO 套餐DTO类对象
     */
    @Transactional
    @Override
    public void insertSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setmealMapper.insertSetmeal(setmeal);

        Long setmealId = setmeal.getId();

        /*
        for (SetmealDish setmealDish : setmealDTO.getSetmealDishes()) {
            setmealDish.setSetmealId(setmealId);
            setmealDishMapper.insertSetmealDish(setmealDish);
        }
        */

        List<SetmealDish> list = setmealDTO.getSetmealDishes();

        if (list != null && !list.isEmpty()) {
            list.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
            setmealDishMapper.insertSetmealDishes(list);
        }
    }

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO 套餐分页查询DTO类对象
     * @return PageResult类分页查询对象
     */
    @Override
    public PageResult getSetmealListPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> page = setmealMapper.getSetmealListPage(setmealPageQueryDTO);

        long total = page.getTotal();
        List<SetmealVO> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 起售/停售套餐
     *
     * @param status 目标套餐起售停售状态
     * @param id     目标套餐id
     */
    @Override
    public void startOrStopSetmeal(Integer status, Long id) {
        if (Objects.equals(status, StatusConstant.ENABLE)) {
            List<Dish> dishList = dishMapper.getDishListBySetmealId(id);
            if (dishList != null && !dishList.isEmpty()) {
                dishList.forEach(dish -> {
                    if (StatusConstant.DISABLE.equals(dish.getStatus()))
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                });
            }
        }

        Setmeal setmeal = new Setmeal();

        setmeal.setId(id);
        setmeal.setStatus(status);

        setmealMapper.updateSetmeal(setmeal);
    }

    /**
     * 根据id批量删除套餐
     *
     * @param ids 套餐id集合类
     */
    @Transactional
    @Override
    public void deleteSetmealByIds(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getSetmealById(id);
            if (StatusConstant.ENABLE.equals(setmeal.getStatus()))
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        });

        ids.forEach(setmealId -> {
            setmealMapper.deleteSetmealById(setmealId);
            setmealDishMapper.deleteSetmealDishBySetmealId(setmealId);
        });
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO 套餐DTO类对象
     */
    @Transactional
    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setmealMapper.updateSetmeal(setmeal);

        Long setmealId = setmeal.getId();

        setmealDishMapper.deleteSetmealDishBySetmealId(setmealId);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));

        setmealDishMapper.insertSetmealDishes(setmealDishes);
    }

    /**
     * 根据分类id查询套餐
     *
     * @param setmeal 套餐实体类对象（仅包含分类id和在售状态）
     * @return List<Setmeal>套餐集合类
     */
    @Override
    public List<Setmeal> getSetmealByCategoryId(Setmeal setmeal) {
        return setmealMapper.getSetmealByCategoryId(setmeal);
    }

    /**
     * 根据套餐id查询包含的菜品列表
     *
     * @param setmealId 套餐id
     * @return List<DishItemVO>
     */
    @Override
    public List<DishItemVO> getDishItemBySetmealId(Long setmealId) {
        return setmealMapper.getDishItemBySetmealId(setmealId);
    }
}
