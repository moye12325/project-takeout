package com.moye.mapper;

import com.moye.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    List<AddressBook> list(AddressBook addressBook);

    void insert(AddressBook addressBook);

    void update(AddressBook addressBook);

    AddressBook getById(Long id);

    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);


    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);
}
