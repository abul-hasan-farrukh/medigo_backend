package com.medigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medigo.model.Contact;

//One interface can inherit another interface, one class can implement another class.
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}