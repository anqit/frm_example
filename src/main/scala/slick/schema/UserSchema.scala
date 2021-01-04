package slick.schema

import model.User
import slick.SlickProfileProvider

trait UserSchema { self: SlickProfileProvider with SchemaTraits =>
    import profile.api._

    case class LiftedUser(name: Rep[String], id: Rep[Option[Int]])
    implicit object UserShape extends CaseClassShape(LiftedUser.tupled, User.tupled)

    class Users(tag: Tag) extends Table[User](tag, "users") with IdColumns[User] {
        override def id = column[Int]("user_id", O.PrimaryKey, O.AutoInc)

        def name = column[String]("name")

        def * = LiftedUser(name, id.?)
    }

    val users = TableQuery[Users]

    val userQueryProvider = new QueryProvider[Users] {
        def query = users
    }
}
