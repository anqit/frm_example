package slick.dao

import dao.{AddressDao, UserDao}
import model.{Address, User}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.JdbcProfile
import slick.{SlickDatabaseProvider, SlickProfileProvider}
import slick.schema.{AddressSchema, SchemaTraits, UserSchema}

import java.time.Instant
import scala.concurrent.Future

trait SlickDaos { self: SchemaTraits with SlickDaoTraits with UserSchema with AddressSchema =>
    trait SlickUserDao extends UserDao with SlickIdDao[User] {
            self: SlickProfileProvider with SlickDatabaseProvider with QueryProvider[Users] =>
        override def query = userQueryProvider.query
        def create(u: User): Future[Option[User]] = db.run(createAction(u))
        def getById(id: Int): Future[Option[User]] = db.run(getByIdAction(id))
        def updateById(u: User): Future[Option[User]] = db.run(updateByIdAction(u))
        def deleteById(id: Int) : Future[Option[Unit]] = db.run(deleteByIdAction(id))
    }

    class SlickUserDaoImpl(val profile: JdbcProfile, val db: Database) extends SlickUserDao
            with SlickProfileProvider with SlickDatabaseProvider with QueryProvider[Users] {
        override def query = userQueryProvider.query
    }

    trait SlickAddressDao extends AddressDao with SlickIdDao[Address] with SlickTimestampDao[Address] {
            self: SlickProfileProvider with SlickDatabaseProvider with QueryProvider[Addresses] =>
        override def query = addressQueryProvider.query
        def create(a: Address): Future[Option[Address]] = db.run(createAction(a))
        def getById(id: Int): Future[Option[Address]] = db.run(getByIdAction(id))
        def updateById(a: Address): Future[Option[Address]] = db.run(updateByIdAction(a))
        def deleteById(id: Int) : Future[Option[Unit]] = db.run(deleteByIdAction(id))
        def getUpdatedAfter(updatedAt: Instant): Future[Seq[Address]] = db.run(getUpdatedAfterAction(updatedAt))
    }
}
