package usermodel.service

import usermodel.dao.UserDao
import usermodel.dto.{UserCreateRequest, UserUpdateRequest}

class UserService(userDao: UserDao) extends ProfileService {

  override def createUser(userCreateRequest: UserCreateRequest): CreateResult = userDao.createUser(userCreateRequest)

  override def updateUser(userUpdateRequest: UserUpdateRequest): UpdateResult = userDao.updateUser(userUpdateRequest)
}
