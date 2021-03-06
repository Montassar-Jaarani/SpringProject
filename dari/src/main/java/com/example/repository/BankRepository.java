package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Bank;
import com.example.entity.Offre;

import java.lang.String;

@Repository
public interface BankRepository extends CrudRepository<Bank, Integer> {
	
/*public List<Bank> getAllOffresByBankRepo(@Param("offre") Offre offre);
*/


	@Query("select DISTINCT o from Offre o join o.bank b  where b.id=:bankId")
	public List<Offre> getAllOffresByBankRepo(@Param("bankId")int bankId); 
	
	List<Bank> findByName(String name);
	
//	@Query("select DISTINCT")
//	public Bank findByBankName();
	
	@Query(value = "select name from bank b", nativeQuery = true)
	public List<String> findBankNames();
	
//	@Query("select DISTRINCT b from Bank join b.offre where b.id=:offre.id")
	@Query(value = "select  b.name from bank b "
			+ " inner join offre o on o.bank_id = b.id  "
			+ " inner join simulation on o.id = simulation.id_offre "
			+ " inner join user u on u.id = simulation.id_user "
			+ " inner join expert e on e.bank_name = b.name ", nativeQuery = true)
	public List<String> findByExpertBank(String bank_name);

}
