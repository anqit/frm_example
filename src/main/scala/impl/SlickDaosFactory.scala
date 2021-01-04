package impl

import slick.SlickProviders.{SlickDatabaseProvider, SlickProfileProvider}
import slick.dao.{SlickDaoTraits, SlickDaos}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.JdbcProfile
import slick.schema.{AddressSchema, SchemaTraits, UserSchema}

class SlickDaosFactory(val profile: JdbcProfile, val db: Database) extends SlickDaos
        with SlickProfileProvider
        with SlickDatabaseProvider
        with SchemaTraits
        with SlickDaoTraits
        with UserSchema
        with AddressSchema {
    def userDao = new SlickUserDaoImpl(profile, db)
    def addressDao = new SlickAddressDaoImpl(profile, db)


    class SlickUserDaoImpl(val profile: JdbcProfile, val db: Database) extends SlickUserDao
    class SlickAddressDaoImpl(val profile: JdbcProfile, val db: Database) extends SlickAddressDao
}
