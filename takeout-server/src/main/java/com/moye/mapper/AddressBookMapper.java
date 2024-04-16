package com.moye.mapper;

import com.moye.entity.AddressBook;

import java.util.List;

public interface AddressBookMapper {


    List<AddressBook> list(AddressBook addressBook);
}
