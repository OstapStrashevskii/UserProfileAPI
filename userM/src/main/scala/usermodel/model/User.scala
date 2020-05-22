package usermodel.model

import usermodel.utils.Validation

import scala.concurrent.Future

/**
 * * - id
 * * - имя (обязательное)
 * * - фамилия (обязательное)
 * * - возраст
 * * - номер телефона (непустой список)
 * * - email (список)
 */

final case class User private(id: Int, name: Name, surName: Surname, age: Age, phoneNum: PhoneNum, email: Email)

object User {
  def create(name: String, surName: String, age: Int, phoneNum: List[String], email: List[String]): Future[User] = {
    if (Validation.validateName(name))
      Future.failed(UserCreateException(" incorrect name"))
    else if (Validation.validateSurName(surName))
      Future.failed(UserCreateException(" incorrect name"))
    else if (Validation.validateAge(age))
      Future.failed(UserCreateException(" incorrect name"))
    else if (Validation.validatePhoneList(phoneNum))
      Future.failed(UserCreateException(" incorrect phone"))
    else if (Validation.validatePhoneList(email))
      Future.failed(UserCreateException(" incorrect email"))
    else
    Future(new User(12323, Name(name), Surname(surName), Age(age), PhoneNum(phoneNum), Email(email)))
  }
}

final case class Name(namestr: String) extends AnyVal

final case class Surname(surnamestr: String) extends AnyVal

final case class Age(years: Int) extends AnyVal

final case class PhoneNum(phones: List[String])

final case class Email(address: List[String])

final case class UserCreateException(message: String) extends Exception(message)
