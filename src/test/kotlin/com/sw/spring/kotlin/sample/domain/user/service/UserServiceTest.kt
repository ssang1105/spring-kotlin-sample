package com.sw.spring.kotlin.sample.domain.user.service

import com.sw.spring.kotlin.sample.domain.user.dto.SignUpRequest
import com.sw.spring.kotlin.sample.domain.user.model.UserStatus
import org.jeasy.random.EasyRandom
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
internal class UserServiceTest @Autowired constructor(val sut: UserService) {

    lateinit var random: EasyRandom;

    @BeforeEach
    fun init() {
        random = EasyRandom()
    }

    @Test
    fun doSignUp() {
        // given
        val request = random.nextObject(SignUpRequest("test").javaClass)

        // when
        val result = sut.doSignUp(request)

        // then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(result.id.toString().length, UUID.randomUUID().toString().length)
        Assertions.assertEquals(result.name, request.name)
        Assertions.assertEquals(result.status, UserStatus.ACTIVE)
        Assertions.assertTrue(result.createdAt.isBefore(LocalDateTime.now()))
        Assertions.assertTrue(result.modifiedAt.isBefore(LocalDateTime.now()))
    }

    @Test
    fun findUser() {
        // given
        val signUpResult = sut.doSignUp(random.nextObject(SignUpRequest("test").javaClass));

        // when
        val result = sut.findUser(signUpResult.id!!)

        // then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(result.id.toString().length, UUID.randomUUID().toString().length)
        Assertions.assertEquals(result.name, signUpResult.name)
        Assertions.assertEquals(result.status, signUpResult.status)
        Assertions.assertEquals(result.createdAt, signUpResult.createdAt)
        Assertions.assertEquals(result.modifiedAt, signUpResult.modifiedAt)
    }

    @Test
    fun findUserByName() {
        // given
        val signUpResult = sut.doSignUp(random.nextObject(SignUpRequest("test").javaClass));

        // when
        val result = sut.findUserByName(signUpResult.name).first { it.id!! == signUpResult.id }

        // then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(result.id.toString().length, UUID.randomUUID().toString().length)
        Assertions.assertEquals(result.name, signUpResult.name)
        Assertions.assertEquals(result.status, signUpResult.status)
        Assertions.assertEquals(result.createdAt, signUpResult.createdAt)
        Assertions.assertEquals(result.modifiedAt, signUpResult.modifiedAt)
    }
}