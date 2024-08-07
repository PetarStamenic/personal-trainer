package work.stamenic.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import work.stamenic.userservice.model.GymManager
@Repository
interface GymManagerRepository : JpaRepository<GymManager,Long>, JpaSpecificationExecutor<GymManager>