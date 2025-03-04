package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    /**
     * 新增地址
     *
     * @param addressBook 地址实体类对象
     */
    void add(AddressBook addressBook);

    /**
     * 查询当前用户的所有地址
     *
     * @return List<AddressBook>地址数据集合类
     */
    List<AddressBook> list();

    /**
     * 根据地址id查询地址
     *
     * @param id 地址id
     * @return AddressBook类实体对象
     */
    AddressBook getById(Long id);

    /**
     * 设置用户默认地址
     *
     * @param id 地址id
     */
    void setDefault(Long id);
}
