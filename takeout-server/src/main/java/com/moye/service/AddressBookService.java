package com.moye.service;

import com.moye.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    List<AddressBook> list(AddressBook addressBook);
}