package slick.schema

import model.Address
import slick.SlickProviders.SlickProfileProvider

import java.time.Instant

trait AddressSchema { self: SlickProfileProvider with SchemaTraits with UserSchema =>
    import profile.api._

    case class LiftedAddress(number: Rep[Int], street: Rep[String], createdAt: Rep[Instant], updatedAt: Rep[Instant], ownerId: Rep[Int], id: Rep[Option[Int]])

    implicit object AddressShape extends CaseClassShape(LiftedAddress.tupled, Address.tupled)

    class Addresses(tag: Tag) extends Table[Address](tag, "addresses") with IdColumns[Address] with TimestampColumns[Address] {
        override def id = column[Int]("address_id", O.PrimaryKey, O.AutoInc)

        def number = column[Int]("number")
        def street = column[String]("street")
        def ownerId = column[Int]("owner_id")

        def * = LiftedAddress(number, street, createdAt, updatedAt, ownerId, id.?)

        def userFk = foreignKey("address_owner_fk", ownerId, users)(_.id)
    }

    val addresses = TableQuery[Addresses]

    trait AddressQueryProvider extends QueryProvider[Addresses] {
        override def query = addresses
    }
}
