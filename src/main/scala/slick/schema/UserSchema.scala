package slick.schema

import model.User
import slick.SlickProviders.SlickProfileProviderComponent

trait UserSchema { self: SlickProfileProviderComponent with SchemaTraits =>
    val profile = profileProvider.profile
    import profile.api._

    case class LiftedUser(name: Rep[String], id: Rep[Option[Int]])
    implicit object UserShape extends CaseClassShape(LiftedUser.tupled, User.tupled)

    class Users(tag: Tag) extends Table[User](tag, "users") with IdColumns[User] {
        override def id = column[Int]("user_id", O.PrimaryKey, O.AutoInc)

        def name = column[String]("name")

        def * = LiftedUser(name, id.?)
    }

    val users = TableQuery[Users]

    trait UserQueryProviderComponent extends QueryProviderComponent with SlickProfileProviderComponent {
        def queryProvider = new QueryProvider[Users] {
            override def query = users
        }

        class UserQueryProvider extends QueryProvider[Users] {
            override def query = users
        }
    }
}
