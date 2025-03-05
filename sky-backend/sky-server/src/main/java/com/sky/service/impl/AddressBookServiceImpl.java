package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.exception.AddressBookBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    AddressBookMapper addressBookMapper;

    /**
     * 新增地址
     *
     * @param addressBook 地址实体类对象
     */
    @Override
    public void add(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(0);

        addressBookMapper.add(addressBook);
    }

    /**
     * 查询当前用户的所有地址
     *
     * @return List<AddressBook>地址数据集合类
     */
    @Override
    public List<AddressBook> list() {
        Long userId = BaseContext.getCurrentId();
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(userId);

        return addressBookMapper.list(addressBook);
    }

    /**
     * 根据地址id查询地址
     *
     * @param id 地址id
     * @return AddressBook类实体对象
     */
    @Override
    public AddressBook getById(Long id) {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(id);
        return addressBookMapper.list(addressBook).get(0);
    }

    /**
     * 查询默认地址
     *
     * @return AddressBook类实体对象
     */
    @Override
    public AddressBook getDefault() {
        Long userId = BaseContext.getCurrentId();
        AddressBook addressBook = AddressBook.builder()
                .userId(userId)
                .isDefault(1)
                .build();
        List<AddressBook> list = addressBookMapper.list(addressBook);
        if (list != null && list.size() == 1)
            return list.get(0);
        else
            throw new AddressBookBusinessException(MessageConstant.DEFAULT_ADDRESS_BOOK_EXCEPTION);
    }

    /**
     * 设置用户默认地址
     *
     * @param addressBook 地址类实体对象
     */
    @Transactional
    @Override
    public void setDefault(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();

        AddressBook oldAddressBook = AddressBook.builder()
                .userId(userId)
                .isDefault(1)
                .build();

        List<AddressBook> list = addressBookMapper.list(oldAddressBook);

        if (list != null && !list.isEmpty())
            for (AddressBook temp : list) {
                temp.setIsDefault(0);
                addressBookMapper.update(temp);
            }

        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook 地址类实体对象
     */
    @Override
    public void updateById(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据地址id删除地址
     *
     * @param id 地址id
     */
    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }
}
