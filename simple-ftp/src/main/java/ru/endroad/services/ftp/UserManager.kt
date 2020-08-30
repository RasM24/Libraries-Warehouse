package ru.endroad.services.ftp

import org.apache.ftpserver.ftplet.*
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication
import org.apache.ftpserver.usermanager.impl.BaseUser
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission
import org.apache.ftpserver.usermanager.impl.WritePermission
import org.apache.ftpserver.ftplet.UserManager as IUserManager

class UserManager : IUserManager {
	private var user: BaseUser? = null

	fun setUser(user: BaseUser) {
		this.user = user
		if (user.authorities == null || user.authorities.isEmpty()) {
			val authorities: ArrayList<Authority> = ArrayList()
			authorities.add(WritePermission())
			authorities.add(ConcurrentLoginPermission(10, 10))
			user.authorities = authorities
		}
	}

	@Throws(AuthenticationFailedException::class)
	override fun authenticate(auth: Authentication?): User? {
		if (auth != null && auth is UsernamePasswordAuthentication) {
			val userAuth = auth as UsernamePasswordAuthentication?
			if (user!!.name == userAuth!!.username && user!!.password == userAuth.password) {
				return user
			}
		}

		return null
	}

	override fun delete(login: String) {
	}

	override fun doesExist(login: String): Boolean {
		return user!!.name == login
	}

	override fun getAdminName(): String {
		return user!!.name
	}

	override fun getAllUserNames(): Array<String> {
		return arrayOf(user!!.name)
	}

	override fun getUserByName(login: String): User? = user

	override fun isAdmin(login: String): Boolean {
		return user!!.name == login
	}

	@Throws(FtpException::class)
	override fun save(login: User) {
	}
}