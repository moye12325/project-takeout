package com.moye.mapper;

import com.moye.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    List<AddressBook> list(AddressBook addressBook);

    void insert(AddressBook addressBook);

    void update(AddressBook addressBook);

    AddressBook getById(Long id);
}
