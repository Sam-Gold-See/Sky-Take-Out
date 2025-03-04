package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@Api("地址簿相关接口")
@RequestMapping("/user/addressBook")
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

    /**
     * 新增地址
     *
     * @param addressBook 地址实体类对象
     * @return Result类响应地址
     */
    @PostMapping
    @ApiOperation("新增地址")
    public Result add(@RequestBody AddressBook addressBook) {
        addressBookService.add(addressBook);
        return Result.success();
    }

    /**
     * 查询当前用户的所有地址
     *
     * @return Result类响应对象
     */
    @GetMapping("/list")
    @ApiOperation("查询当前用户的所有地址")
    public Result<List<AddressBook>> list() {
        List<AddressBook> list = addressBookService.list();
        return Result.success(list);
    }

    /**
     * 根据地址id查询地址
     *
     * @param id 地址id
     * @return Result类响应对象
     */
    @GetMapping("/{id}")
    @ApiOperation("根据地址id查询地址")
    public Result<AddressBook> getById(@PathVariable("id") Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    /**
     * 设置用户默认地址
     *
     * @param id 地址id
     * @return Result类响应对象
     */
    @PutMapping("/default")
    @ApiOperation("设置用户默认地址")
    public Result setDefault(Long id) {
        addressBookService.setDefault(id);
        return Result.success();
    }

    /**
     * 查询默认地址
     *
     * @return Result类响应对象
     */
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault() {
        AddressBook addressBook = addressBookService.getDefault();
        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook 地址实体类对象
     * @return Result类响应对象
     */
    @PutMapping
    @ApiOperation("根据id修改地址")
    public Result updateById(@RequestBody AddressBook addressBook) {
        addressBookService.updateById(addressBook);
        return Result.success();
    }
}
