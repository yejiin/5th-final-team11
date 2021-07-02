package com.doubleslash.fifth.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.CabinetDTO;
import com.doubleslash.fifth.entity.Alcohol;
import com.doubleslash.fifth.entity.AlcoholLove;
import com.doubleslash.fifth.entity.User;

@Repository
public interface AlcoholLoveRepository extends JpaRepository<AlcoholLove, Integer>{

	public Optional<AlcoholLove> findByUserAndAlcohol(User user, Alcohol alcohol);
	
	// 마시고 싶은 술 조회 (시간순)
	@Query(value = "select new com.doubleslash.fifth.dto.CabinetDTO(al.alcohol.id, a.image) from AlcoholLove as al, Alcohol as a where al.alcohol = a.id and al.alcohol = ?1")
	public Page<CabinetDTO> findLoveAlcoholOrderTime(Long id, Pageable pageable);
	
	// 마시고 싶은 술 조회 (도수순)
	@Query(value = "select new com.doubleslash.fifth.dto.CabinetDTO(al.alcohol.id, a.image) from Alcohol as a, AlcoholLove as al where al.alcohol = a.id and al.alcohol = ?1")
	public Page<CabinetDTO> findLoveAlcoholOrderAbv(Long id, Pageable pageable);
	
	@Query(value = "select count(alcohol) from AlcoholLove where alcohol = ?1")
	public int findAidCount(Long aid);
	
	@Query(value = "select count(alcohol) from AlcoholLove where id = ?1 and alcohol = ?2")
	public int findCount(Long id, Long aid);

}
