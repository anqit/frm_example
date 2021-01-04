package slick

import slick.jdbc.JdbcProfile

object SlickProviders {
    trait SlickProfileProvider {
        val profile: JdbcProfile
    }

    trait SlickDatabaseProvider {
        self: SlickProfileProvider =>
        import profile.api._

        def db: Database
    }
}
