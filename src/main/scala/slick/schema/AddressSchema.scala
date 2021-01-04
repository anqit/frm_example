package slick.schema

import model.Address
import slick.SlickProviders.SlickProfileProviderComponent

import java.time.Instant

trait AddressSchema { self: SlickProfileProviderComponent with SchemaTraits with UserSchema =>
    import profile.api._

    case class LiftedAddress(number: Rep[Int], street: Rep[String], createdAt: Rep[Instant], updatedAt: Rep[Instant], ownerId: Rep[Int], id: Rep[Option[Int]])

    implicit object AddressShape extends CaseClassShape(LiftedAddress.tupled, Address.tupled)

    class Addresses(tag: Tag) extends Table[Address](tag, "users") with IdColumns[Address] with TimestampColumns [Address] {
        override def id = column[Int]("address_id", O.PrimaryKey, O.AutoInc)

        def number = column[Int]("number")
        def street = column[String]("street")
        def ownerId = column[Int]("owner_id")

        def user = foreignKey("address_user_fk", ownerId, users)(_.id)

        def * = LiftedAddress(number, street, createdAt, updatedAt, ownerId, id.?)
    }

    val addresses = TableQuery[Addresses]
}
