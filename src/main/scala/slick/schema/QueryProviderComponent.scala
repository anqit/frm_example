package slick.schema

import slick.SlickProviders.SlickProfileProviderComponent

trait QueryProviderComponent { self: SlickProfileProviderComponent =>
    private val profile = profileProvider.profile
    import profile.api._

    def queryProvider[T <: Table [_]]: QueryProvider[T]

    trait QueryProvider[T <: Table [_]] {
        def query: TableQuery[_ <: T]
    }
}
