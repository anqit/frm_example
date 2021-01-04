package slick

import slick.jdbc.JdbcProfile

trait SlickProfileProvider {
    val profile: JdbcProfile
}

trait SlickDatabaseProvider { self: SlickProfileProvider =>
    import profile.api._

    val db: Database
}