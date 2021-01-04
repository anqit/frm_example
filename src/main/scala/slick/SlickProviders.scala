package slick

import slick.jdbc.JdbcProfile

object SlickProviders {
    trait SlickProfileProviderComponent {
        val profileProvider: SlickProfileProvider

        trait SlickProfileProvider {
            def profile: JdbcProfile
        }
    }

    trait SlickDatabaseProviderComponent {
        self: SlickProfileProviderComponent =>
        private val profile = profileProvider.profile

        import profile.api._

        val dbProvider: SlickDatabaseProvider

        trait SlickDatabaseProvider {
            def db: Database
        }
    }
}
