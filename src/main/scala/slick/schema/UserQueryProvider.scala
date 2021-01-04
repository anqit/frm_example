package slick.schema

import slick.SlickProviders.SlickProfileProviderComponent

trait UserQueryProviderComponent extends QueryProviderComponent with SlickProfileProviderComponent { self: UserSchema =>
    def queryProvider = new UserQueryProvider

    class UserQueryProvider extends QueryProvider[Users] {
        override def query = users
    }
}