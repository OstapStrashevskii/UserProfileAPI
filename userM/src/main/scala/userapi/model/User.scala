package userapi.model

import play.api.libs.json.{Json, OFormat}
import userapi.utils.{Utils, Validation}

/**
 * * - id
 * * - имя (обязательное)
 * * - фамилия (обязательное)
 * * - возраст
 * * - номер телефона (непустой список)
 * * - email (список)
 */

final case class User private(id: String, name: Name, surName: Surname, age: Age, phoneNum: PhoneNum, email: Email)

object User {
  def apply(id: Option[String], name: String, surName: String, age: Int, phoneNum: List[String], email: List[String]): Either[String, User] = {
    if (!Validation.validateName(name)) Left(" incorrect name")
    else if (!Validation.validateSurName(surName)) Left(" incorrect surname")
    else if (!Validation.validateAge(age)) Left(" incorrect age")
    else if (!Validation.validatePhoneList(phoneNum)) Left(" incorrect phone")
    else if (!Validation.validateEmailList(email)) Left(" incorrect email")
    else id match {
        case Some(idValue) =>
          Right(new User(idValue, Name(name), Surname(surName), Age(age), PhoneNum(phoneNum), Email(email)))
        case None =>
          Right(new User(Utils.getRandomId, Name(name), Surname(surName), Age(age), PhoneNum(phoneNum), Email(email)))
      }
  }

  implicit lazy val userFormat: OFormat[User] = Json.format[User]
}

final case class Name(namestr: String) extends AnyVal

object Name {
  implicit lazy val nameFormat: OFormat[Name] = Json.format[Name]
}

final case class Surname(surnamestr: String) extends AnyVal

object Surname {
  implicit lazy val surnameFormat: OFormat[Surname] = Json.format[Surname]
}

final case class Age(years: Int) extends AnyVal

object Age {
  implicit lazy val ageFormat: OFormat[Age] = Json.format[Age]
}

final case class PhoneNum(phones: List[String])

object PhoneNum {
  implicit lazy val phoneFormat: OFormat[PhoneNum] = Json.format[PhoneNum]
}

final case class Email(address: List[String])

object Email {
  implicit lazy val emailFormat: OFormat[Email] = Json.format[Email]
}

final case class UserCreateException(message: String) extends Exception(message)
