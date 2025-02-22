package kr.ac.chosun.devossian.domain.maintenance.repository;

import kr.ac.chosun.devossian.domain.maintenance.domain.Maintenance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    Page<Maintenance> findByTitleContaining(String title, Pageable pageable);
    Page<Maintenance> findAllByOrderByIdAsc(Pageable pageable);

//    @Query("SELECT m FROM Maintenance m " +
//            "WHERE m.uuid = (SELECT p.uuid FROM PostTag p " +
//                "WHERE p.tag.id = (SELECT t.id FROM Tag t WHERE t.name = :tag))")
    @Query("SELECT m FROM Maintenance m " +
            "JOIN PostTag p ON m.uuid = p.uuid " +
            "JOIN Tag t ON p.tag.id = t.id " +
            "WHERE t.name = :tag")
    Page<Maintenance> findByTag(@Param("tag") String tag, Pageable pageable);
}
