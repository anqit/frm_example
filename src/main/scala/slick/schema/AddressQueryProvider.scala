package slick.schema

import slick.SlickProviders.SlickProfileProviderComponent

trait AddressQueryProvider extends QueryProviderComponent with SlickProfileProviderComponent { self: AddressSchema =>
    def queryProvider = new AddressQueryProvider
    class AddressQueryProvider extends QueryProvider[Addresses] {
        override def query = addresses
    }
}
