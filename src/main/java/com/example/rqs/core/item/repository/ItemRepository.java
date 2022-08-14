package com.example.rqs.core.item.repository;

import com.example.rqs.core.item.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, CustomItemRepository {
}
