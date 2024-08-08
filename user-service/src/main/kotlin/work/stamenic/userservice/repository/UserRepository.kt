package work.stamenic.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import work.stamenic.userservice.model.User
import java.util.*

@Repository
interface UserRepository : JpaRepository<User,Long>, JpaSpecificationExecutor<User>{
    fun findByUserAndStatusTrue(username: String): User?
}