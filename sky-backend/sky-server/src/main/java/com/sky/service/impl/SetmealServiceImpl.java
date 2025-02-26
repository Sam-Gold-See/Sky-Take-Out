package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

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
}
