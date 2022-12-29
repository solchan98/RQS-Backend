package com.example.rqs.core.spacemember.repository;

import com.example.rqs.core.spacemember.SpaceMember;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpaceMemberRepository extends JpaRepository<SpaceMember, Long>, CustomSpaceMemberRepository {
}
