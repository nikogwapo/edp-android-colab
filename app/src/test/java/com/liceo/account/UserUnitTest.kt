package com.liceo.account

import com.liceo.account.data.network.dto.UserDto
import com.liceo.account.data.network.dto.toDomain
import com.liceo.account.ui.ageFrom
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UserUnitTest {

    @Test
    fun testUserDtoToDomainMapper() {
        val dto = UserDto(
            id = "12",
            fullname = " Juan Dela Cruz ",
            email = " juan.test01@liceo.test ",
            password = "secret123",
            birthdate = "2004-05-17"
        )
        val user = dto.toDomain()

        assertEquals("12", user.id)
        assertEquals("Juan Dela Cruz", user.fullName)
        assertEquals("juan.test01@liceo.test", user.email)
        assertEquals("2004-05-17", user.birthdate)
    }

    @Test
    fun testUserDtoToDomainNullHandling() {
        val dto = UserDto()
        val user = dto.toDomain()

        assertEquals("", user.id)
        assertEquals("(no name)", user.fullName)
        assertEquals("", user.email)
        assertEquals("(not set)", user.birthdate)
    }

    @Test
    fun testAgeFromFunction() {
        val age = ageFrom("2004-05-17")
        assertEquals(22, age) // Current year 2026 - 2004 = 22

        val invalidAge = ageFrom("invalid-date")
        assertNull(invalidAge)
    }
}
