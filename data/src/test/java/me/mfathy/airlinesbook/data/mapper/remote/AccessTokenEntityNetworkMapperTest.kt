package me.mfathy.airlinesbook.data.mapper.remote

import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.store.remote.model.AccessToken
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for AccessTokenEntityNetworkMapper
 */
@RunWith(JUnit4::class)
class AccessTokenEntityNetworkMapperTest {
    private val mapper = AccessTokenEntityNetworkMapper()


    @Test
    fun testMapToEntityMapsData() {
        val token = AirportFactory.makeAccessToken()
        val entity = mapper.mapToEntity(token)

        assertEqualData(entity, token)
    }

    private fun assertEqualData(entity: AccessTokenEntity, domain: AccessToken) {
        assertEquals(entity.accessToken, domain.accessToken)
        assertEquals(entity.tokenType, domain.tokenType)
        assertEquals(entity.clintId, domain.clientId)
        assertEquals(entity.expiresIn, domain.expiresIn)
    }
}