package impl

import slick.{SlickDatabaseProvider, SlickProfileProvider}
import slick.dao.{SlickDaoTraits, SlickDaos}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.JdbcProfile
import slick.schema.{AddressSchema, SchemaTraits, UserSchema}

class SlickDaosImpl(val profile: JdbcProfile, val db: Database) extends SlickDaos
        with SlickProfileProvider
        with SchemaTraits
        with SlickDaoTraits
        with UserSchema
        with AddressSchema {
    outer =>

    object SlickUserDaoImpl extends SlickUserDao
            with SlickProfileProvider with SlickDatabaseProvider with QueryProvider[Users] {
        val profile = outer.profile
        val db = outer.db
    }

    object SlickAddressDaoImpl extends SlickAddressDao
            with SlickProfileProvider with SlickDatabaseProvider with QueryProvider[Addresses] {
        val profile = outer.profile
        val db = outer.db
    }
}
