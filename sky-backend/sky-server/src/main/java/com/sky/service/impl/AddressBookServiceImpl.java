package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 设置用户默认地址
     *
     * @param id 地址id
     */
    @Override
    public void setDefault(Long id) {
        Long userId = BaseContext.getCurrentId();

        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(1);

        List<AddressBook> list = addressBookMapper.list(addressBook);

        if (list != null && !list.isEmpty()) {
            AddressBook oldAddressBook = AddressBook.builder()
                    .id(list.get(0).getId())
                    .isDefault(0)
                    .build();
            addressBookMapper.update(oldAddressBook);
        }

        addressBook.setId(id);
        addressBookMapper.update(addressBook);
    }
}
