package slick.schema

import model.Address
import slick.SlickProfileProvider

import java.time.Instant

trait AddressSchema { self: SlickProfileProvider with SchemaTraits with UserSchema =>
    import profile.api._

    case class LiftedAddress(number: Rep[Int], street: Rep[String], createdAt: Rep[Instant], updatedAt: Rep[Instant], userId: Rep[Int], id: Rep[Option[Int]])

    implicit object AddressShape extends CaseClassShape(LiftedAddress.tupled, Address.tupled)

    class Addresses(tag: Tag) extends Table[Address](tag, "users") with IdColumns[Address] with TimestampColumns [Address] {
        override def id = column[Int]("address_id", O.PrimaryKey, O.AutoInc)

        def number = column[Int]("number")
        def street = column[String]("street")
        def userId = column[Int]("user_id")

        def user = foreignKey("address_user_fk", userId, users)(_.id)

        def * = LiftedAddress(number, street, createdAt, updatedAt, userId, id.?)
    }

    val addresses = TableQuery[Addresses]

    val addressQueryProvider = new QueryProvider[Addresses] {
        def query = addresses
    }
}
