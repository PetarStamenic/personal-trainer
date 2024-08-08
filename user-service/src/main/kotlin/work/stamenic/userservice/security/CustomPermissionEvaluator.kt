package work.stamenic.userservice.security

import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable


class CustomPermissionEvaluator: PermissionEvaluator {
    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        if (authentication == null || !authentication.isAuthenticated || permission !is String) {
            return false
        }
        var userDetails = authentication.principal as UserDetails
        var authorities = userDetails.authorities

        for (authority in authorities) {
            return authority!!.authority  == permission
        }
        return false
    }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?
    ): Boolean {
        return false
    }
}