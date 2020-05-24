package userapi.utils

object Validation {
  def paramsValidate(name: String, surName: String, age: Int, phoneNum: List[String], email: List[String]): Boolean = {
    validateAge(age) &&
    validateName(name) &&
    validateSurName(surName)
    validateEmailList(email) &&
    validatePhoneList(phoneNum)
  }

  def validateAge(age: Int): Boolean = {
    age < 120 && age > 5
  }

  def validatePhone(phone: String): Boolean = {//0771234567  +0771234567
    phone.matches("^[0-9\\-\\+]{9,15}$")
  }

  def validatePhoneList(phoneList: List[String]): Boolean = {
    phoneList.forall(s => validatePhone(s))
  }

  def validateEmail(email: String): Boolean = {
    email.matches("^[^@]+@[^\\.]+\\..+$")
  }


  def validateEmailList(email: List[String]): Boolean = {
    email.nonEmpty &&
    email.forall(s => validateEmail(s))
  }

  def validateName(name: String): Boolean = {
    name.matches("^[a-zA-Z]+$")
  }

  def validateSurName(surName: String): Boolean = {
    surName.matches("^[a-zA-Z]+$")
  }
}
