package slick.dao

import dao.{AddressDao, UserDao}
import model.{Address, User}
import slick.SlickProviders.{SlickDatabaseProviderComponent, SlickProfileProviderComponent}
import slick.schema.{AddressSchema, SchemaTraits, UserSchema}

import java.time.Instant
import scala.concurrent.Future

trait SlickDaos { self: SchemaTraits with SlickDaoTraits with UserSchema with AddressSchema =>
    trait SlickUserDao extends UserDao with SlickIdDao[User] {
            self: SlickProfileProviderComponent with SlickDatabaseProviderComponent with QueryProviderComponent[Users] =>
        val db = dbProvider.db
        override val query = queryProvider.query
        def create(u: User): Future[Option[User]] = db.run(createAction(u))
        def getById(id: Int): Future[Option[User]] = db.run(getByIdAction(id))
        def updateById(u: User): Future[Option[User]] = db.run(updateByIdAction(u))
        def deleteById(id: Int) : Future[Option[Unit]] = db.run(deleteByIdAction(id))
    }

//    class SlickUserDaoImpl extends SlickUserDao with SlickProfileProviderComponent with SlickDatabaseProviderComponent with QueryProviderComponent[Users] {
//        val queryProvider = UserSc
//    }

    trait SlickAddressDao extends AddressDao with SlickIdDao[Address] with SlickTimestampDao[Address] {
            self: SlickProfileProviderComponent with SlickDatabaseProviderComponent with QueryProviderComponent[Addresses] =>
        val db = dbProvider.db
        override val query = queryProvider.query

        def create(a: Address): Future[Option[Address]] = db.run(createAction(a))
        def getById(id: Int): Future[Option[Address]] = db.run(getByIdAction(id))
        def updateById(a: Address): Future[Option[Address]] = db.run(updateByIdAction(a))
        def deleteById(id: Int) : Future[Option[Unit]] = db.run(deleteByIdAction(id))
        def getUpdatedAfter(updatedAt: Instant): Future[Seq[Address]] = db.run(getUpdatedAfterAction(updatedAt))
    }
}
