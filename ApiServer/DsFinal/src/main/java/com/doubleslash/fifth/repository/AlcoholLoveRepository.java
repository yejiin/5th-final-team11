package com.doubleslash.fifth.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.CabinetDTO;
import com.doubleslash.fifth.vo.ReviewLoveVO;

@Repository
public interface AlcoholLoveRepository extends JpaRepository<ReviewLoveVO, Integer>{

	@Modifying
	@Transactional
	@Query(value = "insert ignore into AlcoholLove(id, aid) values(?1, ?2)", nativeQuery = true)
	public int insert(int id, int aid);
	
	@Modifying
	@Transactional
	@Query(value = "delete from AlcoholLove where id=?1 and aid=?2", nativeQuery = true)
	public int delete(int id, int aid);
	
	// 마시고 싶은 술 조회 (시간순)
	@Query(value = "select new com.doubleslash.fifth.dto.CabinetDTO(al.aid, a.image) from AlcoholLoveVO as al, AlcoholVO as a where al.aid = a.aid and al.id = ?1")
	public Page<CabinetDTO> findLoveAlcoholOrderTime(int id, Pageable pageable);
	
	// 마시고 싶은 술 조회 (도수순)
	@Query(value = "select new com.doubleslash.fifth.dto.CabinetDTO(al.aid, a.image) from AlcoholVO as a, AlcoholLoveVO as al where al.aid = a.aid and al.id = ?1")
	public Page<CabinetDTO> findLoveAlcoholOrderAbv(int id, Pageable pageable);
}
