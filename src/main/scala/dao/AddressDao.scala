package dao

import model.Address

import scala.concurrent.Future

trait AddressDao extends IdDao[Address] with TimestampDao[Address] {
    def create(u: Address): Future[Option[Address]]
}
