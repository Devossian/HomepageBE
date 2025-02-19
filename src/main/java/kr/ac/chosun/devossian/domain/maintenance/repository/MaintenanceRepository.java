package kr.ac.chosun.devossian.domain.maintenance.repository;

import kr.ac.chosun.devossian.domain.maintenance.domain.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

}
