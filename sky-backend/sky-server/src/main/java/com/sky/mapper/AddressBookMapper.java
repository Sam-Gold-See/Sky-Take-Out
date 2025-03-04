package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 新增地址
     *
     * @param addressBook 地址实体类对象
     */
    @Insert("insert into address_book (user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default) " +
            "values (#{userId}, #{consignee}, #{sex}, #{phone}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void add(AddressBook addressBook);

    /**
     * 动态查询地址
     *
     * @return List<AddressBook>地址数据集合类
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 动态更新地址
     *
     * @param addressBook 地址实体类对象
     */
    void update(AddressBook addressBook);

    /**
     * 根据地址id删除地址
     *
     * @param id 地址id
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);
}
