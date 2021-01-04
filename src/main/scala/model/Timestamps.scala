package model

import java.time.Instant

trait Timestamps[E <: Timestamps[E]] {
    def createdAt: Instant
    def updatedAt: Instant

    def withCreatedAt(c: Instant): E
    def withUpdatedAt(u: Instant): E
}
