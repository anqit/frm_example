package model

case class User(name: String, id: Option[Int] = None) extends Id[User] {
    override def withId(id: Int): User = this.copy(id = Some(id))
}
